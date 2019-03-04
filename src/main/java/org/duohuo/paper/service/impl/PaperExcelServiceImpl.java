package org.duohuo.paper.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.duohuo.paper.Constants;
import org.duohuo.paper.excel.listener.PaperExcelListener;
import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.ZipFileException;
import org.duohuo.paper.model.*;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.repository.*;
import org.duohuo.paper.service.PaperExcelService;
import org.duohuo.paper.utils.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service("paperExcelServiceImpl")
public class PaperExcelServiceImpl implements PaperExcelService {


    /*
    Paper插入思路：
    1. 首先获取压缩包文件，将其保存在jar包同级目录下。
    2. 解压缩，同时获取解压之后文件
    3. 如果是esi论文，过滤文件名和学科相验证，存在不符合规范文件，直接抛出异常结束。
    4. 如果是校级论文，首先分辨出图片和excel，一个excel和对应的多少张图片，过滤图片名称和excel中文件相验证，如果不存在的articleName，直接抛出异常结束。
    5. 检验excel中学科是否存在，不存在直接结束
    6. 如果是校级论文，将图片转成base64编码，存入数据库中
    7. 判断新的时间是否存在，如果不存在，直接插入时间。
    8. 如果存在，首先删除单类别下的该时间点所有数据，接着查出除此单类别之外的该时间点下所有数据，查到相同accessionNumber，存在该类别，不变，不存在该类别添加上该类别，同时都要修改除了accessionNumber之外的所有数据
    9. 插入新的数据
    10. 结束
    */

    @Resource(name = "paperRepository")
    private PaperRepository paperRepository;

    @Resource(name = "redisRepository")
    private RedisRepository redisRepository;

    @Resource(name = "timeRepository")
    private TimeRepository timeRepository;

    @Resource(name = "categoryRepository")
    private CategoryRepository categoryRepository;

    @Resource(name = "schoolPaperImageRepository")
    private SchoolPaperImageRepository schoolPaperImageRepository;

    @Override
    public JsonResult insertPaperExcel(List<String> filePaths, Integer year, Integer month, Integer type, String fileName) {
        String typeFilePath = getTypeFilePath(type);
        try {
            //这是解压之后文件所在目录
            //mac多余文件的删除和过滤
            filePaths = filePaths.stream()
                    .peek(elem -> {
                        if (elem.contains("_MACOSX") || elem.contains(".DS_Store")) {
                            new File(typeFilePath + elem)
                                    .deleteOnExit();
                        }
                    })
                    .filter(elem -> !elem.contains("_MACOSX")
                            && !elem.contains(".DS_Store"))
                    .collect(Collectors.toList());
            //如果可用文件为空，直接抛出异常，此时已经没有需要删除的文件
            if (!ObjectUtil.ifNotNullList(filePaths)) {
                throw new ZipFileException("zip文件中可用文件为空!" + fileName);
            }
            boolean timeExists = true;
            Time time = timeRepository.findByYearAndMonth(year, month).orElse(null);
            if (time == null) {
                time = new Time();
                time.setYear(year);
                time.setMonth(month);
                time.setTimeId(TimeUtil.createTimeIdByYearAndMonth(year, month));
                timeExists = false;
                timeRepository.save(time);
            }
            if (type == 1 || type == 2) {
                List<Paper> papers = getEsiPaperInfos(type, time, filePaths, typeFilePath, timeExists);
                paperRepository.saveAll(papers);
            } else {
                getSchoolPaperInfos(type, time, filePaths, typeFilePath);
            }
            redisRepository.delByPattern("paper_*");
        } finally {
            FileUtil.fileDelete(filePaths, typeFilePath);
        }

        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    private String getTypeFilePath(Integer type) {
        switch (type) {
            case 1:
                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_ESI_HIGHLY_PAPER + File.separator;
            case 2:
                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_ESI_HOT_PAPER + File.separator;
            case 3:
                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_SCHOOL_HIGHLY_PAPER + File.separator;
            case 4:
                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_SCHOOL_HOT_PAPER + File.separator;
            default:
                throw new RuntimeException();
        }
    }

    //这个文件夹中全部都是excel，并且根据文件名称筛选subject
    private List<Paper> getEsiPaperInfos(Integer type, Time time, List<String> filePaths, String typeFilePath, Boolean timeExists) {
        List<Paper> paperModels = new ArrayList<>();
        for (String path : filePaths) {
            File file = new File(typeFilePath + path);
            if (file.isDirectory()) continue;
            String fileName = file.getName();
            if (!RegexUtil.excelFileValidation(fileName)) {
                throw new ZipFileException("未知格式文件:" + fileName);
            }
            String categoryName = fileName.split("-")[0];
            if (categoryName.contains("_")) {
                categoryName = categoryName.replace("_", "/");
            }
            Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
            if (category == null) {
                throw new ExcelException("类别不存在" + categoryName);
            }
            List<PaperExcelModel> paperExcelModels = getPaperExcelModelByFilePath(typeFilePath + path);
            if (!ObjectUtil.ifNotNullList(paperExcelModels)) {
                throw new ExcelException("excel文件为空!" + fileName);
            }
            if (timeExists) {
                //如果时间存在，判断这个论文是否已经存在，通过accessionNumber来获取
                for (PaperExcelModel model : paperExcelModels) {
                    paperModels.add(getPaperListByExcelModelTimeNotExist(type, time, model, category));
                }
            } else {
                //如果时间不存在，说明这个时间点还没有不同种类的论文上传，直接将其转换即可
                PaperType paperType = getPaperType(type);
                paperModels = paperExcelModels.stream()
                        .map(model -> model.convertToPaper(paperType, category, time))
                        .collect(Collectors.toList());
            }
        }
        return paperModels;
    }

    private void getSchoolPaperInfos(Integer type, Time time, List<String> filePaths, String typeFilePath) {
        //excel文件的路径
        String excelFilePath = null;
        //找到excel文件的路径
        for (String path : filePaths) {
            File file = new File(typeFilePath + path);
            //如果文件是一个目录，跳过
            if (file.isDirectory()) continue;
            //获得文件名称
            String fileName = file.getName();
            //验证文件是不是excel文件
            if (RegexUtil.excelFileValidation(fileName)) {
                //验证文件是不是临时文件
                if (!fileName.contains("~$")) {
                    excelFilePath = path;
                    break;
                }
            }
        }
        System.out.println("1");
        //如果为null说明没有找到
        if (excelFilePath == null) {
            throw new ExcelException("没有找到excel文件");
        }
        System.out.println("2");
        //创建类别名称为key的hash表
        Map<String, Category> categoryMap = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getCategoryName, category -> category));
        //将图片的开头标号建立hash表
        Map<Integer, String> imageFilePathMap = new HashMap<>();
        for (String imageFilePath : filePaths) {
            //image文件
            File imageFile = new File(typeFilePath + imageFilePath);
            String fileName = imageFile.getName();
            if (RegexUtil.imageRegex(fileName)) {
                //获取开头标号
                String number = fileName.split("-")[0];
                //放进hash
                imageFilePathMap.put(Integer.parseInt(number), imageFilePath);
            }
        }
        System.out.println("3");
        //读取excel中文件信息，因为是list所以不用担心顺序问题
        List<PaperExcelModel> paperExcelModels = getPaperExcelModelByFilePath(typeFilePath + excelFilePath);
        System.out.println("4");
        int size = paperExcelModels.size();
        //循环，excel为顺序读，并存到list中，说明此时的顺序和excel中顺序相同，可以直接利用这个特性，找到文件夹中文件
        for (int i = 0; i < size; i++) {
            String imageFilePath;
            //如果hash表中存在该序号表示图片存在，否则抛出异常，显示图片不存在
            if (imageFilePathMap.containsKey(i + 1)) {
                imageFilePath = imageFilePathMap.get(i + 1);
            } else {
                throw new ZipFileException("缺少所需图片文件");
            }
            //获取该顺序下excelModel
            PaperExcelModel tempExcelModel = paperExcelModels.get(i);
            Category category = null;
            if (categoryMap.containsKey(tempExcelModel.getResearchField())) {
                category = categoryMap.get(tempExcelModel.getResearchField());
            }
            //存储
            Paper paper = paperRepository.save(getPaperListByExcelModelTimeNotExist(type, time, tempExcelModel, category));
            File imageFile = new File(typeFilePath + imageFilePath);
            String imageFileName = imageFile.getName();
            byte[] data;
            try (InputStream inputStream = new FileInputStream(imageFile)) {
                data = new byte[inputStream.available()];
                //noinspection ResultOfMethodCallIgnored
                inputStream.read(data);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            SchoolPaperImage image = new SchoolPaperImage();
            image.setImageData(data);
            image.setImageName(imageFileName);
            image.setPaperId(paper.getPaperId());
            schoolPaperImageRepository.save(image);
        }
    }

    private Paper getPaperListByExcelModelTimeNotExist(Integer type, Time time, PaperExcelModel model, Category category) {
        Paper paper = new Paper();
        Paper ttPaper = paperRepository.findByAccessionNumberAndTime(model.getAccessionNumber(), time).orElse(null);
        if (ttPaper == null) {
            //说明数据库中没有
            paper = model.convertToPaper(getPaperType(type), category, time);
        } else {
            //数据库中存在，更新数据：使用新的值，旧的id，新的type
            paper.setPaperId(ttPaper.getPaperId());
            paper.setPmid(model.getPmid());
            paper.setInstitutions(model.getInstitutions());
            paper.setSource(model.getSource());
            paper.setDoi(model.getDoi());
            paper.setResearchField(model.getResearchField());
            paper.setAccessionNumber(model.getAccessionNumber());
            paper.setTime(time);
            paper.setAuthors(model.getAuthors());
            paper.setPublicationDate(model.getPublicationDate());
            paper.setTimesCited(model.getTimesCited());
            paper.setCategory(category);
            paper.setCountries(model.getCountries());
            paper.setPaperType(changePaperType(type, ttPaper.getPaperType()));
            paper.setArticleName(model.getArticleName());
            paper.setAddresses(model.getAddress());
        }
        return paper;
    }

    private List<PaperExcelModel> getPaperExcelModelByFilePath(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            AnalysisEventListener listener = new PaperExcelListener();
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            reader.read(new Sheet(1, 6, PaperExcelModel.class));
            return ((PaperExcelListener) listener).getVector();
        } catch (Exception e) {
            throw new ExcelException(e.getMessage());
        }
    }


    private PaperType getPaperType(Integer newType) {
        switch (newType) {
            case 1:
                return PaperType.HC_PAPER;
            case 2:
                return PaperType.HOT_PAPER;
            case 3:
                return PaperType.SHC_PAPER;
            case 4:
                return PaperType.SHOT_PAPER;
            default:
                throw new RuntimeException();
        }
    }

    private PaperType changePaperType(Integer newType, PaperType originType) {
        switch (newType) {
            case 1: {
                switch (originType) {
                    case HC_PAPER:
                        return PaperType.HC_PAPER;
                    case HOT_PAPER:
                        return PaperType.HC_HOT_PAPER;
                    case SHC_PAPER:
                        return PaperType.HC_SHC_PAPER;
                    case SHOT_PAPER:
                        return PaperType.HC_SHOT_PAPER;
                    case HC_HOT_PAPER:
                        return PaperType.HC_HOT_PAPER;
                    case HC_SHC_PAPER:
                        return PaperType.HC_SHC_PAPER;
                    case HC_SHOT_PAPER:
                        return PaperType.HC_SHOT_PAPER;
                    case HOT_SHC_PAPER:
                        return PaperType.HC_HOT_SHC_PAPER;
                    case HOT_SHOT_PAPER:
                        return PaperType.HC_HOT_SHOT_PAPER;
                    case SHC_SHOT_PAPER:
                        return PaperType.HC_SHC_SHOT_PAPER;
                    case HC_HOT_SHC_PAPER:
                        return PaperType.HC_HOT_SHC_PAPER;
                    case HC_HOT_SHOT_PAPER:
                        return PaperType.HC_HOT_SHOT_PAPER;
                    case HC_SHC_SHOT_PAPER:
                        return PaperType.HC_SHC_SHOT_PAPER;
                    case HOT_SHC_SHOT_PAPER:
                        return PaperType.ALL_PAPER;
                    case ALL_PAPER:
                        return PaperType.ALL_PAPER;
                }
            }
            case 2: {
                switch (originType) {
                    case HC_PAPER:
                        return PaperType.HC_HOT_PAPER;
                    case HOT_PAPER:
                        return PaperType.HOT_PAPER;
                    case SHC_PAPER:
                        return PaperType.HOT_SHC_PAPER;
                    case SHOT_PAPER:
                        return PaperType.HOT_SHOT_PAPER;
                    case HC_HOT_PAPER:
                        return PaperType.HC_HOT_PAPER;
                    case HC_SHC_PAPER:
                        return PaperType.HC_HOT_SHC_PAPER;
                    case HC_SHOT_PAPER:
                        return PaperType.HOT_SHOT_PAPER;
                    case HOT_SHC_PAPER:
                        return PaperType.HOT_SHC_PAPER;
                    case HOT_SHOT_PAPER:
                        return PaperType.HOT_SHOT_PAPER;
                    case SHC_SHOT_PAPER:
                        return PaperType.HOT_SHC_SHOT_PAPER;
                    case HC_HOT_SHC_PAPER:
                        return PaperType.HC_HOT_SHC_PAPER;
                    case HC_HOT_SHOT_PAPER:
                        return PaperType.HC_HOT_SHOT_PAPER;
                    case HC_SHC_SHOT_PAPER:
                        return PaperType.ALL_PAPER;
                    case HOT_SHC_SHOT_PAPER:
                        return PaperType.HOT_SHC_SHOT_PAPER;
                    case ALL_PAPER:
                        return PaperType.ALL_PAPER;
                }
            }
            case 3: {
                switch (originType) {
                    case HC_PAPER:
                        return PaperType.HC_SHC_PAPER;
                    case HOT_PAPER:
                        return PaperType.HOT_SHC_PAPER;
                    case SHC_PAPER:
                        return PaperType.SHC_PAPER;
                    case SHOT_PAPER:
                        return PaperType.SHC_SHOT_PAPER;
                    case HC_HOT_PAPER:
                        return PaperType.HC_HOT_SHC_PAPER;
                    case HC_SHC_PAPER:
                        return PaperType.HC_SHC_PAPER;
                    case HC_SHOT_PAPER:
                        return PaperType.HC_SHC_SHOT_PAPER;
                    case HOT_SHC_PAPER:
                        return PaperType.HOT_SHC_PAPER;
                    case HOT_SHOT_PAPER:
                        return PaperType.HOT_SHC_SHOT_PAPER;
                    case SHC_SHOT_PAPER:
                        return PaperType.SHC_SHOT_PAPER;
                    case HC_HOT_SHC_PAPER:
                        return PaperType.HC_HOT_SHC_PAPER;
                    case HC_HOT_SHOT_PAPER:
                        return PaperType.ALL_PAPER;
                    case HC_SHC_SHOT_PAPER:
                        return PaperType.HC_SHC_SHOT_PAPER;
                    case HOT_SHC_SHOT_PAPER:
                        return PaperType.HOT_SHC_SHOT_PAPER;
                    case ALL_PAPER:
                        return PaperType.ALL_PAPER;
                }
            }
            case 4: {
                switch (originType) {
                    case HC_PAPER:
                        return PaperType.HC_SHOT_PAPER;
                    case HOT_PAPER:
                        return PaperType.HOT_SHOT_PAPER;
                    case SHC_PAPER:
                        return PaperType.SHC_SHOT_PAPER;
                    case SHOT_PAPER:
                        return PaperType.SHOT_PAPER;
                    case HC_HOT_PAPER:
                        return PaperType.HC_HOT_SHOT_PAPER;
                    case HC_SHC_PAPER:
                        return PaperType.HC_SHC_SHOT_PAPER;
                    case HC_SHOT_PAPER:
                        return PaperType.HC_SHOT_PAPER;
                    case HOT_SHC_PAPER:
                        return PaperType.HOT_SHC_SHOT_PAPER;
                    case HOT_SHOT_PAPER:
                        return PaperType.HOT_SHOT_PAPER;
                    case SHC_SHOT_PAPER:
                        return PaperType.SHC_SHOT_PAPER;
                    case HC_HOT_SHC_PAPER:
                        return PaperType.ALL_PAPER;
                    case HC_HOT_SHOT_PAPER:
                        return PaperType.HC_HOT_SHOT_PAPER;
                    case HC_SHC_SHOT_PAPER:
                        return PaperType.HC_SHC_SHOT_PAPER;
                    case HOT_SHC_SHOT_PAPER:
                        return PaperType.HOT_SHC_SHOT_PAPER;
                    case ALL_PAPER:
                        return PaperType.ALL_PAPER;
                }
            }
            default:
                throw new RuntimeException();
        }
    }
}
