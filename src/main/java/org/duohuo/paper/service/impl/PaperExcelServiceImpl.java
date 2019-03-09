package org.duohuo.paper.service.impl;

import org.duohuo.paper.convert.PaperConverter;
import org.duohuo.paper.excel.model.PaperExcelModel;
import org.duohuo.paper.exceptions.ExcelException;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.exceptions.ZipFileException;
import org.duohuo.paper.manager.*;
import org.duohuo.paper.model.*;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperExcelService;
import org.duohuo.paper.utils.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service("paperExcelServiceImpl")
public class PaperExcelServiceImpl implements PaperExcelService {

    @Resource(name = "excelManager")
    private ExcelManager excelManager;

    @Resource(name = "timeManager")
    private TimeManager timeManager;

    @Resource(name = "categoryManager")
    private CategoryManager categoryManager;

    @Resource(name = "paperManager")
    private PaperManager paperManager;

    @Resource(name = "paperTypeManager")
    private PaperTypeManager paperTypeManager;

    @Resource(name = "schoolPaperImageManager")
    private SchoolPaperImageManager schoolPaperImageManager;


    @Override
    public JsonResult deleteByYearAndMonth(Integer year, Integer month, Integer type) {
        Optional<Time> time = timeManager.findByYearAndMonth(year, month);
        if (!time.isPresent()) {
            throw new NotFoundException("所查找的年月不存在:" + year + "/" + month);
        }
        List<Long> paperIdList = paperManager.findIdListByTimeAndPaperType(year, month, paperTypeManager.getPaperType(type));
        if (!ObjectUtil.ifNotNullCollection(paperIdList)) {
            throw new NotFoundException("无法通过该时间点找到该类型的论文,时间:" + year + "/" + month + ",类型:" + type);
        }
        schoolPaperImageManager.deleteByPaperIdList(paperIdList);
        paperManager.deleteByPaperIdList(paperIdList);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @Override
    public JsonResult insertSchoolPaperExcelData(final List<String> filePaths, final Integer year, final Integer month, final String fileName, final String typeFilePath, final Integer type) {
        //获取临时目录
//        String typeFilePath = FileUtil.getTypeFilePath(type);
        //过滤解压之后的文件目录
        List<String> newFilePaths = excelManager.filePathFilter(filePaths, typeFilePath);
        //获取这个时间点，如果不存在则保存
        Time time = timeManager.findByYearAndMonth(year, month)
                .orElseGet(() -> timeManager.save(year, month));
        //创建图片编号和路径的map
        Map<Integer, String> imageFilePathMap = new HashMap<>();
        String excelPath = null;
        for (String filePath : newFilePaths) {
            File file = new File(typeFilePath + filePath);
            //如果是目录，直接忽略
            if (file.isDirectory()) continue;
            String tempFileName = file.getName();
            //判断是不是图片文件
            if (RegexUtil.imageRegex(tempFileName)) {
                imageFilePathMap.put(
                        Integer.parseInt(tempFileName.split("-")[0]),
                        filePath
                );
            } else if (RegexUtil.excelFileValidation(tempFileName)) {
                //判断是不是excel文件同时判断是不是临时文件
                if (!tempFileName.contains("~$")) {
                    excelPath = filePath;
                }
            }
        }
        if (excelPath == null) {
            throw new ZipFileException("本校论文压缩包中没有找到符合规格的excel文件:" + fileName);
        }
        //读取excel中文件
        List<PaperExcelModel> excelModels = excelManager.paperExcelRead(typeFilePath + excelPath);
        int size = excelModels.size();
        //获取类别的map
        Map<String, Category> categoryMap = categoryManager.createCategoryNameMap();
        //开始遍历
        for (int i = 0; i < size; i++) {
            //如果编号不存在，抛出异常
            if (!imageFilePathMap.containsKey(i + 1)) {
                throw new ZipFileException("本校论文压缩包中没有找到符合规格图片文件:" + fileName);
            }
            //获取图片路径
            String imageFilePath = imageFilePathMap.get(i + 1);
            //获取临时的excelModel
            PaperExcelModel tempExcelModel = excelModels.get(i);
            //如果判断出类别不存在，抛出异常
            if (!categoryMap.containsKey(tempExcelModel.getResearchField().toUpperCase())) {
                throw new ZipFileException("本校论文压缩包中没有找到符合规格类别:" + tempExcelModel.getResearchField());
            }
            //获取类别
            Category category = categoryMap.get(tempExcelModel.getResearchField().toUpperCase());
            //如果是这个时间点不存在的paper，直接插入。如果存在，则获取旧的id以及将type改变。然后保存
            Paper paper = paperManager.save(getNewAndChangeOld(type, time, category, tempExcelModel));
            //获取image文件
            File imageFile = new File(typeFilePath + imageFilePath);
            String imageFileName = imageFile.getName();
            //读取image的byte
            byte[] data;
            try (InputStream inputStream = new FileInputStream(imageFile)) {
                data = new byte[inputStream.available()];
                //noinspection ResultOfMethodCallIgnored
                inputStream.read(data);
            } catch (IOException e) {
                throw new ZipFileException("读取图片出现错误:" + e.getMessage());
            }
            //保存image
            schoolPaperImageManager.save(data, imageFileName, paper.getPaperId());
        }
        //返回正确
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

    @Override
    public JsonResult insertEsiPaperExcelData(final List<String> filePaths, final Integer year, final Integer month, final String fileName, final String typeFilePath, final Integer type) {
        //获取临时目录
//        String typeFilePath = FileUtil.getTypeFilePath(type);
        //获取过滤之后的类别目录
        List<String> newFilePath = excelManager.filePathFilter(filePaths, typeFilePath);
        List<String> excelFilePathList = new ArrayList<>();
        //获取新的excel文件目录
        for (String path : newFilePath) {
            File excelFile = new File(typeFilePath + path);
            //如果是目录则跳过
            if (excelFile.isDirectory()) continue;
            String excelFileName = excelFile.getName();
            //如果是临时文件跳过
            if (excelFileName.contains("~$")) continue;
            //如果是xlsx文件加入
            if (RegexUtil.excelFileValidation(excelFileName)) {
                excelFilePathList.add(path);
            }
        }
        //查看时间是否存在，不存在则保存
        Time time = timeManager.findByYearAndMonth(year, month)
                .orElseGet(() -> timeManager.save(year, month));
        //获取类别hash表
        Map<String, Category> categoryMap = categoryManager.createCategoryNameMap();
        List<Paper> paperList = new ArrayList<>();
        //读取excel同时将数据插入
        for (String excelFilePath : excelFilePathList) {
            //读取
            List<PaperExcelModel> paperExcelModels = excelManager.paperExcelRead(typeFilePath + excelFilePath);
            //从第一个来获取类别名称
            String categoryName = paperExcelModels.get(0).getResearchField().toUpperCase();
            //如果不包括该类别，则抛出异常
            if (!categoryMap.containsKey(categoryName)) {
                throw new ExcelException("上传的excel文件中存在未知的类别:" + categoryName);
            }
            Category category = categoryMap.get(categoryName);
            //转换，如果存在则替换，不存在则直接插入
            for (PaperExcelModel excelModel : paperExcelModels) {
                paperList.add(getNewAndChangeOld(type, time, category, excelModel));
            }
        }
        //插入
        paperManager.saveAll(paperList);
        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
    }

//    @Override
//    public JsonResult insertPaperExcel(List<String> filePaths, Integer year, Integer month, Integer type, String fileName) {
//        String typeFilePath = getTypeFilePath(type);
//        try {
//            //这是解压之后文件所在目录
//            //mac多余文件的删除和过滤
//            filePaths = filePaths.stream()
//                    .peek(elem -> {
//                        if (elem.contains("_MACOSX") || elem.contains(".DS_Store")) {
//                            new File(typeFilePath + elem)
//                                    .deleteOnExit();
//                        }
//                    })
//                    .filter(elem -> !elem.contains("_MACOSX")
//                            && !elem.contains(".DS_Store"))
//                    .collect(Collectors.toList());
//            //如果可用文件为空，直接抛出异常，此时已经没有需要删除的文件
//            if (!ObjectUtil.ifNotNullList(filePaths)) {
//                throw new ZipFileException("zip文件中可用文件为空!" + fileName);
//            }
//            boolean timeExists = true;
//            Time time = timeRepository.findByYearAndMonth(year, month).orElse(null);
//            if (time == null) {
//                time = new Time();
//                time.setYear(year);
//                time.setMonth(month);
//                time.setTimeId(TimeUtil.createTimeIdByYearAndMonth(year, month));
//                timeExists = false;
//                timeRepository.save(time);
//            }
//            if (type == 1 || type == 2) {
//                List<Paper> papers = getEsiPaperInfos(type, time, filePaths, typeFilePath, timeExists);
//                paperRepository.saveAll(papers);
//            } else {
//                getSchoolPaperInfos(type, time, filePaths, typeFilePath);
//            }
//            redisRepository.delByPattern("paper_*");
//        } finally {
//            FileUtil.fileDelete(filePaths, typeFilePath);
//        }
//
//        return new JsonResult(HttpStatus.OK.value(), HttpStatus.OK.name());
//    }
//
//    private String getTypeFilePath(Integer type) {
//        switch (type) {
//            case 1:
//                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_ESI_HIGHLY_PAPER + File.separator;
//            case 2:
//                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_ESI_HOT_PAPER + File.separator;
//            case 3:
//                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_SCHOOL_HIGHLY_PAPER + File.separator;
//            case 4:
//                return ExcelUtil.getJarPath() + File.separator + Constants.TEMP_FILE_SCHOOL_HOT_PAPER + File.separator;
//            default:
//                throw new RuntimeException();
//        }
//    }
//
//    //这个文件夹中全部都是excel，并且根据文件名称筛选subject
//    private List<Paper> getEsiPaperInfos(Integer type, Time time, List<String> filePaths, String typeFilePath, Boolean timeExists) {
//        List<Paper> paperModels = new ArrayList<>();
//        for (String path : filePaths) {
//            File file = new File(typeFilePath + path);
//            if (file.isDirectory()) continue;
//            String fileName = file.getName();
//            if (!RegexUtil.excelFileValidation(fileName)) {
//                throw new ZipFileException("未知格式文件:" + fileName);
//            }
//            String categoryName = fileName.split("-")[0];
//            if (categoryName.contains("_")) {
//                categoryName = categoryName.replace("_", "/");
//            }
//            Category category = categoryRepository.findByCategoryName(categoryName).orElse(null);
//            if (category == null) {
//                throw new ExcelException("类别不存在" + categoryName);
//            }
//            List<PaperExcelModel> paperExcelModels = getPaperExcelModelByFilePath(typeFilePath + path);
//            if (!ObjectUtil.ifNotNullList(paperExcelModels)) {
//                throw new ExcelException("excel文件为空!" + fileName);
//            }
//            if (timeExists) {
//                //如果时间存在，判断这个论文是否已经存在，通过accessionNumber来获取
//                for (PaperExcelModel model : paperExcelModels) {
//                    paperModels.add(getPaperListByExcelModelTimeNotExist(type, time, model, category));
//                }
//            } else {
//                //如果时间不存在，说明这个时间点还没有不同种类的论文上传，直接将其转换即可
//                PaperType paperType = getPaperType(type);
//                paperModels = paperExcelModels.stream()
//                        .map(model -> model.convertToPaper(paperType, category, time))
//                        .collect(Collectors.toList());
//            }
//        }
//        return paperModels;
//    }
//
//    private void getSchoolPaperInfos(Integer type, Time time, List<String> filePaths, String typeFilePath) {
//        //excel文件的路径
//        String excelFilePath = null;
//        //找到excel文件的路径
//        for (String path : filePaths) {
//            File file = new File(typeFilePath + path);
//            //如果文件是一个目录，跳过
//            if (file.isDirectory()) continue;
//            //获得文件名称
//            String fileName = file.getName();
//            //验证文件是不是excel文件
//            if (RegexUtil.excelFileValidation(fileName)) {
//                //验证文件是不是临时文件
//                if (!fileName.contains("~$")) {
//                    excelFilePath = path;
//                    break;
//                }
//            }
//        }
//        System.out.println("1");
//        //如果为null说明没有找到
//        if (excelFilePath == null) {
//            throw new ExcelException("没有找到excel文件");
//        }
//        System.out.println("2");
//        //创建类别名称为key的hash表
//        Map<String, Category> categoryMap = categoryRepository.findAll().stream()
//                .collect(Collectors.toMap(Category::getCategoryName, category -> category));
//        //将图片的开头标号建立hash表
//        Map<Integer, String> imageFilePathMap = new HashMap<>();
//        for (String imageFilePath : filePaths) {
//            //image文件
//            File imageFile = new File(typeFilePath + imageFilePath);
//            String fileName = imageFile.getName();
//            if (RegexUtil.imageRegex(fileName)) {
//                //获取开头标号
//                String number = fileName.split("-")[0];
//                //放进hash
//                imageFilePathMap.put(Integer.parseInt(number), imageFilePath);
//            }
//        }
//        System.out.println("3");
//        //读取excel中文件信息，因为是list所以不用担心顺序问题
//        List<PaperExcelModel> paperExcelModels = getPaperExcelModelByFilePath(typeFilePath + excelFilePath);
//        System.out.println("4");
//        int size = paperExcelModels.size();
//        //循环，excel为顺序读，并存到list中，说明此时的顺序和excel中顺序相同，可以直接利用这个特性，找到文件夹中文件
//        for (int i = 0; i < size; i++) {
//            String imageFilePath;
//            //如果hash表中存在该序号表示图片存在，否则抛出异常，显示图片不存在
//            if (imageFilePathMap.containsKey(i + 1)) {
//                imageFilePath = imageFilePathMap.get(i + 1);
//            } else {
//                throw new ZipFileException("缺少所需图片文件");
//            }
//            //获取该顺序下excelModel
//            PaperExcelModel tempExcelModel = paperExcelModels.get(i);
//            Category category = null;
//            if (categoryMap.containsKey(tempExcelModel.getResearchField())) {
//                category = categoryMap.get(tempExcelModel.getResearchField());
//            }
//            //存储
//            Paper paper = paperRepository.save(getPaperListByExcelModelTimeNotExist(type, time, tempExcelModel, category));
//            File imageFile = new File(typeFilePath + imageFilePath);
//            String imageFileName = imageFile.getName();
//            byte[] data;
//            try (InputStream inputStream = new FileInputStream(imageFile)) {
//                data = new byte[inputStream.available()];
//                //noinspection ResultOfMethodCallIgnored
//                inputStream.read(data);
//            } catch (Exception e) {
//                throw new RuntimeException(e.getMessage());
//            }
//            SchoolPaperImage image = new SchoolPaperImage();
//            image.setImageData(data);
//            image.setImageName(imageFileName);
//            image.setPaperId(paper.getPaperId());
//            schoolPaperImageRepository.save(image);
//        }
//    }


    //查看是否存在同类别同时间，并且同accessionNumber，如果存在，则修改（使用旧的id，其他都用新的），不存在则直接插入
    private Paper getNewAndChangeOld(final Integer type, final Time time, final Category category, final PaperExcelModel model) {
        PaperType paperType = paperTypeManager.getPaperType(type);
        Paper tempPaper = paperManager.findByAccessionNumberAndTimeAndPaperType(model.getAccessionNumber(), time, paperType).orElse(null);
        Paper paper = new Paper();
        if (tempPaper == null) {
            //说明数据库中没有
            paper = PaperConverter.convertExcelPaperToEntity(model, category, time, paperType);
        } else {
            //数据库中存在，更新数据：使用新的值，旧的id，新的type
            paper.setPaperId(tempPaper.getPaperId());
            paper.setPmid(model.getPmid());
            paper.setInstitutions(model.getInstitutions());
            paper.setSource(model.getSource());
            paper.setDoi(model.getDoi());
            paper.setResearchField(model.getResearchField());
            paper.setAccessionNumber(model.getAccessionNumber());
            paper.setTime(time);
            paper.setAuthors(model.getAuthors());
            paper.setPublicationDate(model.getPublicationDate());
            paper.setTimesCited(model.getTimeCited());
            paper.setCategory(category);
            paper.setCountries(model.getCountries());
            paper.setArticleName(model.getArticleName());
            paper.setAddresses(model.getAddress());
            //这里被修改
            paper.setPaperType(paperType);
        }
        return paper;
    }

//    private Paper getPaperListByExcelModelTimeNotExist(Integer type, Time time, PaperExcelModel model, Category category) {
//        Paper paper = new Paper();
//        Paper ttPaper = paperRepository.findByAccessionNumberAndTime(model.getAccessionNumber(), time).orElse(null);
//        if (ttPaper == null) {
//            //说明数据库中没有
//            paper = model.convertToPaper(getPaperType(type), category, time);
//        } else {
//            //数据库中存在，更新数据：使用新的值，旧的id，新的type
//            paper.setPaperId(ttPaper.getPaperId());
//            paper.setPmid(model.getPmid());
//            paper.setInstitutions(model.getInstitutions());
//            paper.setSource(model.getSource());
//            paper.setDoi(model.getDoi());
//            paper.setResearchField(model.getResearchField());
//            paper.setAccessionNumber(model.getAccessionNumber());
//            paper.setTime(time);
//            paper.setAuthors(model.getAuthors());
//            paper.setPublicationDate(model.getPublicationDate());
//            paper.setTimesCited(model.getTimesCited());
//            paper.setCategory(category);
//            paper.setCountries(model.getCountries());
//            paper.setPaperType(changePaperType(type, ttPaper.getPaperType()));
//            paper.setArticleName(model.getArticleName());
//            paper.setAddresses(model.getAddress());
//        }
//        return paper;
//    }
//
//    private List<PaperExcelModel> getPaperExcelModelByFilePath(String filePath) {
//        try (InputStream inputStream = new FileInputStream(filePath)) {
//            AnalysisEventListener listener = new PaperExcelListener();
//            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
//            reader.read(new Sheet(1, 6, PaperExcelModel.class));
//            return ((PaperExcelListener) listener).getVector();
//        } catch (Exception e) {
//            throw new ExcelException(e.getMessage());
//        }
//    }
//
//
//    private PaperType getPaperType(Integer newType) {
//        switch (newType) {
//            case 1:
//                return PaperType.HC_PAPER;
//            case 2:
//                return PaperType.HOT_PAPER;
//            case 3:
//                return PaperType.SHC_PAPER;
//            case 4:
//                return PaperType.SHOT_PAPER;
//            default:
//                throw new RuntimeException();
//        }
//    }
//
//    private PaperType changePaperType(Integer newType, PaperType originType) {
//        switch (newType) {
//            case 1: {
//                switch (originType) {
//                    case HC_PAPER:
//                        return PaperType.HC_PAPER;
//                    case HOT_PAPER:
//                        return PaperType.HC_HOT_PAPER;
//                    case SHC_PAPER:
//                        return PaperType.HC_SHC_PAPER;
//                    case SHOT_PAPER:
//                        return PaperType.HC_SHOT_PAPER;
//                    case HC_HOT_PAPER:
//                        return PaperType.HC_HOT_PAPER;
//                    case HC_SHC_PAPER:
//                        return PaperType.HC_SHC_PAPER;
//                    case HC_SHOT_PAPER:
//                        return PaperType.HC_SHOT_PAPER;
//                    case HOT_SHC_PAPER:
//                        return PaperType.HC_HOT_SHC_PAPER;
//                    case HOT_SHOT_PAPER:
//                        return PaperType.HC_HOT_SHOT_PAPER;
//                    case SHC_SHOT_PAPER:
//                        return PaperType.HC_SHC_SHOT_PAPER;
//                    case HC_HOT_SHC_PAPER:
//                        return PaperType.HC_HOT_SHC_PAPER;
//                    case HC_HOT_SHOT_PAPER:
//                        return PaperType.HC_HOT_SHOT_PAPER;
//                    case HC_SHC_SHOT_PAPER:
//                        return PaperType.HC_SHC_SHOT_PAPER;
//                    case HOT_SHC_SHOT_PAPER:
//                        return PaperType.ALL_PAPER;
//                    case ALL_PAPER:
//                        return PaperType.ALL_PAPER;
//                }
//            }
//            case 2: {
//                switch (originType) {
//                    case HC_PAPER:
//                        return PaperType.HC_HOT_PAPER;
//                    case HOT_PAPER:
//                        return PaperType.HOT_PAPER;
//                    case SHC_PAPER:
//                        return PaperType.HOT_SHC_PAPER;
//                    case SHOT_PAPER:
//                        return PaperType.HOT_SHOT_PAPER;
//                    case HC_HOT_PAPER:
//                        return PaperType.HC_HOT_PAPER;
//                    case HC_SHC_PAPER:
//                        return PaperType.HC_HOT_SHC_PAPER;
//                    case HC_SHOT_PAPER:
//                        return PaperType.HOT_SHOT_PAPER;
//                    case HOT_SHC_PAPER:
//                        return PaperType.HOT_SHC_PAPER;
//                    case HOT_SHOT_PAPER:
//                        return PaperType.HOT_SHOT_PAPER;
//                    case SHC_SHOT_PAPER:
//                        return PaperType.HOT_SHC_SHOT_PAPER;
//                    case HC_HOT_SHC_PAPER:
//                        return PaperType.HC_HOT_SHC_PAPER;
//                    case HC_HOT_SHOT_PAPER:
//                        return PaperType.HC_HOT_SHOT_PAPER;
//                    case HC_SHC_SHOT_PAPER:
//                        return PaperType.ALL_PAPER;
//                    case HOT_SHC_SHOT_PAPER:
//                        return PaperType.HOT_SHC_SHOT_PAPER;
//                    case ALL_PAPER:
//                        return PaperType.ALL_PAPER;
//                }
//            }
//            case 3: {
//                switch (originType) {
//                    case HC_PAPER:
//                        return PaperType.HC_SHC_PAPER;
//                    case HOT_PAPER:
//                        return PaperType.HOT_SHC_PAPER;
//                    case SHC_PAPER:
//                        return PaperType.SHC_PAPER;
//                    case SHOT_PAPER:
//                        return PaperType.SHC_SHOT_PAPER;
//                    case HC_HOT_PAPER:
//                        return PaperType.HC_HOT_SHC_PAPER;
//                    case HC_SHC_PAPER:
//                        return PaperType.HC_SHC_PAPER;
//                    case HC_SHOT_PAPER:
//                        return PaperType.HC_SHC_SHOT_PAPER;
//                    case HOT_SHC_PAPER:
//                        return PaperType.HOT_SHC_PAPER;
//                    case HOT_SHOT_PAPER:
//                        return PaperType.HOT_SHC_SHOT_PAPER;
//                    case SHC_SHOT_PAPER:
//                        return PaperType.SHC_SHOT_PAPER;
//                    case HC_HOT_SHC_PAPER:
//                        return PaperType.HC_HOT_SHC_PAPER;
//                    case HC_HOT_SHOT_PAPER:
//                        return PaperType.ALL_PAPER;
//                    case HC_SHC_SHOT_PAPER:
//                        return PaperType.HC_SHC_SHOT_PAPER;
//                    case HOT_SHC_SHOT_PAPER:
//                        return PaperType.HOT_SHC_SHOT_PAPER;
//                    case ALL_PAPER:
//                        return PaperType.ALL_PAPER;
//                }
//            }
//            case 4: {
//                switch (originType) {
//                    case HC_PAPER:
//                        return PaperType.HC_SHOT_PAPER;
//                    case HOT_PAPER:
//                        return PaperType.HOT_SHOT_PAPER;
//                    case SHC_PAPER:
//                        return PaperType.SHC_SHOT_PAPER;
//                    case SHOT_PAPER:
//                        return PaperType.SHOT_PAPER;
//                    case HC_HOT_PAPER:
//                        return PaperType.HC_HOT_SHOT_PAPER;
//                    case HC_SHC_PAPER:
//                        return PaperType.HC_SHC_SHOT_PAPER;
//                    case HC_SHOT_PAPER:
//                        return PaperType.HC_SHOT_PAPER;
//                    case HOT_SHC_PAPER:
//                        return PaperType.HOT_SHC_SHOT_PAPER;
//                    case HOT_SHOT_PAPER:
//                        return PaperType.HOT_SHOT_PAPER;
//                    case SHC_SHOT_PAPER:
//                        return PaperType.SHC_SHOT_PAPER;
//                    case HC_HOT_SHC_PAPER:
//                        return PaperType.ALL_PAPER;
//                    case HC_HOT_SHOT_PAPER:
//                        return PaperType.HC_HOT_SHOT_PAPER;
//                    case HC_SHC_SHOT_PAPER:
//                        return PaperType.HC_SHC_SHOT_PAPER;
//                    case HOT_SHC_SHOT_PAPER:
//                        return PaperType.HOT_SHC_SHOT_PAPER;
//                    case ALL_PAPER:
//                        return PaperType.ALL_PAPER;
//                }
//            }
//            default:
//                throw new RuntimeException();
//        }
//    }
}
