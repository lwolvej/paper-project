//package org.duohuo.paper;
//
//import com.alibaba.excel.ExcelReader;
//import com.alibaba.excel.event.AnalysisEventListener;
//import com.alibaba.excel.metadata.Sheet;
//import com.alibaba.excel.support.ExcelTypeEnum;
//import net.lingala.zip4j.core.ZipFile;
//import net.lingala.zip4j.model.FileHeader;
//import org.duohuo.paper.excel.listener.BaseLineExcelListener;
//import org.duohuo.paper.excel.listener.JournalExcelListener;
//import org.duohuo.paper.excel.listener.PaperExcelListener;
//import org.duohuo.paper.excel.model.JournalExcelModel;
//import org.duohuo.paper.excel.model.PaperExcelModel;
//import org.duohuo.paper.model.*;
//import org.duohuo.paper.model.result.JournalResult;
//import org.duohuo.paper.model.result.JsonResult;
//import org.duohuo.paper.model.result.PageResult;
//import org.duohuo.paper.repository.*;
//import org.duohuo.paper.service.BaseLineService;
//import org.duohuo.paper.service.JournalSearchService;
//import org.duohuo.paper.service.PaperSearchService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ApplicationTests {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationTests.class);
//
//    @Test
//    public void contextLoads() {
//    }
//
//    @Resource(name = "paperRepository")
//    private PaperRepository paperRepository;
//
//    @Resource(name = "categoryRepository")
//    private CategoryRepository categoryRepository;
//
//    @Resource(name = "journalRepository")
//    private JournalRepository journalRepository;
//
//    @Resource(name = "timeRepository")
//    private TimeRepository timeRepository;
//
//    @Resource(name = "journalSearchService")
//    private JournalSearchService journalSearchService;
//
//    @Resource(name = "userRepository")
//    private UserRepository userRepository;
//
//    @Resource(name = "paperHotSearchService")
//    private PaperSearchService hotPaperSearchService;
//
//    @Resource(name = "baseLineRepository")
//    private BaseLineRepository baseLineRepository;
//
//    @Resource(name = "baseLineServiceImpl")
//    private BaseLineService baseLineService;
//
//    @Resource(name = "redisRepository")
//    private RedisRepository redisRepository;
//
//    @Test
//    public void saveJournal() {
//        List<JournalExcelModel> models = null;
//        try (InputStream inputStream = new FileInputStream("/Users/lwolvej/IdeaProjects/paper/src/main/resources/201803-ESIMasterJournalList-022018.xls")) {
//            AnalysisEventListener listener = new JournalExcelListener();
//            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLS, null, listener);
//            reader.read(new Sheet(1, 1, JournalExcelModel.class));
//            models = ((JournalExcelListener) listener).getVector();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (models != null) {
//            Time time = new Time();
//            time.setYear(2018);
//            time.setMonth(3);
//            time.setTimeId("201803".hashCode());
//            timeRepository.save(time);
//            final Map<String, Category> map = categoryRepository.findAll()
//                    .stream()
//                    .collect(Collectors.toMap(Category::getCategoryName, category -> category));
//            List<Journal> journals = models.stream()
//                    .map(elem -> {
//                        Category category;
//                        if (!map.containsKey(elem.getCategoryName())) {
//                            category = new Category();
//                            category.setCategoryName(elem.getCategoryName());
//                            category = categoryRepository.save(category);
//                            map.put(category.getCategoryName(), category);
//                        } else {
//                            category = map.get(elem.getCategoryName());
//                        }
//                        return elem.convertToJournal(category, time);
//                    })
//                    .collect(Collectors.toList());
//            journalRepository.saveAll(journals);
//            LOGGER.info("插入成功");
//        } else {
//            LOGGER.error("处理excel失败");
//        }
//    }
//
//    @Test
//    public void savePaper() {
//        List<PaperExcelModel> paperExcelModels = null;
//        try (InputStream inputStream = new FileInputStream("/Users/lwolvej/IdeaProjects/paper/src/main/resources/AGRICULTURAL SCIENCES-78.xlsx")) {
//            AnalysisEventListener listener = new PaperExcelListener();
//            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
//            reader.read(new Sheet(1, 6, PaperExcelModel.class));
//            paperExcelModels = ((PaperExcelListener) listener).getVector();
//        } catch (Exception ignore) {
//        }
//        if (paperExcelModels != null) {
//            Category category = new Category();
//            category.setCategoryName("AGRICULTURAL SCIENCES");
//            category.setCategoryId(1020);
//            Time time = new Time();
//            time.setYear(2018);
//            time.setMonth(9);
//            time.setTimeId("201809".hashCode());
//            List<Paper> papers = paperExcelModels
//                    .stream()
//                    .map(elem -> elem.convertToPaper(PaperType.HOT_PAPER, category, time))
//                    .collect(Collectors.toList());
//            paperRepository.saveAll(papers);
//            LOGGER.info("成功");
//        } else {
//            LOGGER.error("处理excel失败");
//        }
//    }
//
//    @Test
//    public void savePaper2() {
//        File[] files = new File("/Users/lwolvej/IdeaProjects/paper/src/main/resources/excel").listFiles();
//        if (files != null) {
//            Map<String, Category> categoryMap = categoryRepository.findAll().stream()
//                    .collect(Collectors.toMap(Category::getCategoryName, category -> category));
//            Time time = new Time();
//            time.setYear(2018);
//            time.setMonth(3);
//            time.setTimeId("201803".hashCode());
//            for (File file : files) {
//                String categoryName = file.getName().split("-")[0];
//                if (categoryMap.containsKey(categoryName)) {
//                    Category category = categoryMap.get(categoryName);
//                    List<PaperExcelModel> paperExcelModels = null;
//                    try (InputStream inputStream = new FileInputStream(file)) {
//                        AnalysisEventListener listener = new PaperExcelListener();
//                        ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
//                        reader.read(new Sheet(1, 6, PaperExcelModel.class));
//                        paperExcelModels = ((PaperExcelListener) listener).getVector();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (paperExcelModels != null) {
//                        List<Paper> papers = paperExcelModels
//                                .stream()
//                                .map(elem -> elem.convertToPaper(PaperType.HOT_PAPER, category, time))
//                                .collect(Collectors.toList());
//                        for (Paper paper : papers) {
//                            if (paperRepository.existsByAccessionNumber(paper.getAccessionNumber())) {
//                                System.out.println("paper已存在:" + paper);
//                                Paper tt = paperRepository.findByAccessionNumber(paper.getAccessionNumber());
//                                tt.setTime(paper.getTime());
//                                tt.setCategory(paper.getCategory());
//                                tt.setTimesCited(paper.getTimesCited());
//                                tt.setArticleName(paper.getArticleName());
//                                tt.setPaperType(paper.getPaperType());
//                                tt.setAuthors(paper.getAuthors());
//                                tt.setAddresses(paper.getAddresses());
//                                tt.setPublicationDate(paper.getPublicationDate());
//                                tt.setResearchField(paper.getResearchField());
//                                tt.setDoi(paper.getDoi());
//                                tt.setSource(paper.getSource());
//                                tt.setInstitutions(paper.getInstitutions());
//                                tt.setPmid(paper.getPmid());
//                                paperRepository.save(tt);
//                            } else {
//                                paperRepository.save(paper);
//                            }
//                        }
////                        paperRepository.saveAll(papers);
////                        LOGGER.info("成功");
//                    } else {
//                        LOGGER.error("处理excel失败");
//                    }
//                } else {
//                    System.out.println("类别不存在:" + categoryName);
//                }
//            }
//        }
//    }
//
//    @Test
//    public void saveUser() {
//        User user = new User();
//        user.setUserName("admin");
//        user.setPassword("admin");
//        user.setPermission("edit");
//        user.setRole("admin");
//        userRepository.save(user);
//    }
//
//    @Test
//    public void saveTime() {
//        Time time = new Time();
//        time.setYear(2018);
//        time.setMonth(7);
//        time.setTimeId("201807".hashCode());
//        timeRepository.save(time);
//    }
//
//    @Test
//    public void journalFind1() {
//        Pageable pageable = PageRequest.of(1, 10);
//        List<Integer> list = new ArrayList<>();
//        list.add(1002);
//        list.add(1003);
//        list.add(1004);
//        Page<Journal> page = journalRepository.findAllByCategory_CategoryIdIn(list, pageable);
//        List<Journal> journals = page.getContent();
//        for (Journal elem : journals) {
//            int cateId = elem.getCategory().getCategoryId();
//            if (cateId != 1002 && cateId != 1003 && cateId != 1004) {
//                LOGGER.error("类别失败");
//            }
//            System.out.println(elem);
//        }
//        System.out.println(journals.size());
//    }
//
//    @Test
//    public void journalFind2() {
//        Pageable pageable = PageRequest.of(0, 10);
//        List<Integer> list = new ArrayList<>();
//        list.add(1002);
//        list.add(1003);
//        list.add(1004);
//        Page<Journal> page = journalRepository.findAllByKeyWordAndCategory_CategoryIdIn("ONCOLOGICA", list, pageable);
//        List<Journal> journals = page.getContent();
//        for (Journal elem : journals) {
//            int cateId = elem.getCategory().getCategoryId();
//            if (cateId != 1002 && cateId != 1003 && cateId != 1004) {
//                LOGGER.error("类别失败");
//            }
//            System.out.println(elem);
//        }
//        System.out.println(journals.size());
//    }
//
//    @Test
//    public void journalFind3() {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Journal> page = journalRepository.findAllByKeyWord("ONCOLOGICA", pageable);
//        List<Journal> journals = page.getContent();
//        System.out.println(journals.size());
//    }
//
//    @Test
//    public void journalFind4() {
//        List<Long> journalIdList = new ArrayList<>();
//        journalIdList.add(121312312L);
//        journalIdList.add(2131231231L);
//        journalIdList.add(777777L);
//        journalRepository.findAllByJournalIdIn(journalIdList).forEach(System.out::println);
//    }
//
//    @Test
//    public void journalFind5() {
//        List<Integer> list = new ArrayList<>();
//        list.add(1002);
//        list.add(1003);
//        list.add(1004);
//        ((List<JournalResult>) ((PageResult) journalSearchService.searchByCategory(0, true, list).getData())
//                .getData()).forEach(System.out::println);
//    }
//
//    @Test
//    public void journalFind6() {
//        List<Integer> list = new ArrayList<>();
//        list.add(1002);
//        list.add(1003);
//        list.add(1004);
//        journalRepository.findAllByCategory_CategoryIdIn(list, PageRequest.of(0, 10, Sort.by(Sort.Order.desc("journalId")))).get().forEach(System.out::println);
//    }
//
//    @Test
//    public void journalService1() {
//        List<Integer> ids = new ArrayList<>();
//        ids.add(1003);
//        Page<JournalResult> page = (Page<JournalResult>) journalSearchService.searchByCategory(6, false, ids).getData();
//
//        System.out.println(page.getTotalElements());
//        System.out.println(page.getTotalPages());
//        System.out.println(page.getNumber());
//        System.out.println(page.getSize());
//        System.out.println(page.getNumberOfElements());
//    }
//
//    @Test
//    public void paperFind1() {
//        List<PaperType> paperTypes = new ArrayList<>();
//        paperTypes.add(PaperType.HOT_PAPER);
//        List<Integer> categoryList = new ArrayList<>();
//        categoryList.add(1020);
//        paperRepository.findAllByCategory_CategoryIdInAndPaperTypeIn(categoryList, paperTypes, PageRequest.of(1, 10))
//                .forEach(System.out::println);
//    }
//
//    @Test
//    public void paperFind2() {
//        List<PaperType> paperTypes = new ArrayList<>();
//        paperTypes.add(PaperType.HOT_PAPER);
//        paperRepository.findAllByAccessionNumberAndPaperTypeIn("WOS:000435182900042", paperTypes, PageRequest.of(0, 10)).forEach(System.out::println);
//    }
//
////    @Test
////    public void paperFind3() {
////        List<PaperType> paperTypes = new ArrayList<>();
////        paperTypes.add(PaperType.HC_PAPER);
////        paperRepository.findAllByKeyWordAndPaperTypeIn("GENETIC", paperTypes, PageRequest.of(0, 10)).forEach(System.out::println);
////    }
//
//    @Test
//    public void test11() {
//        System.out.println((PageResult) (journalSearchService.getCurrent(0, false, "", new ArrayList<>()).getData()));
//    }
//
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void test12() {
//        try {
//            String baseDir = "/Users/lwolvej/Desktop";
//            ZipFile zipFile = new ZipFile("/Users/lwolvej/IdeaProjects/paper/src/main/resources/Hot papers 201803.zip");
//            zipFile.setFileNameCharset("utf-8");
//            zipFile.extractAll(baseDir);
//            List<FileHeader> fileHeaders = zipFile.getFileHeaders();
//            List<File> files = new ArrayList<>();
//            if (fileHeaders != null) {
//                for (FileHeader fileHeader : fileHeaders) {
//                    if (!fileHeader.isDirectory()) {
//                        if (!fileHeader.getFileName().startsWith("__")
//                                && !fileHeader.getFileName().contains(".DS_Store")) {
//                            files.add(new File(baseDir, fileHeader.getFileName()));
//                        }
//                    }
//                }
//                Map<String, Category> categoryMap = categoryRepository.findAll()
//                        .stream()
//                        .collect(Collectors.toMap(Category::getCategoryName, category -> category));
//                for (File file : files) {
//                    System.out.println("当前文件名称:" + file.getName());
//                    String realFileName = file.getName().split("-")[0];
//                    if (categoryMap.containsKey(realFileName)) {
//                        System.out.println("存在类别:" + realFileName);
//                    } else {
//                        System.out.println("类别不存在:" + realFileName);
//                    }
//                }
//            } else {
//                System.out.println("转换错误");
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @Test
//    public void journalTest1() {
//        JsonResult result = journalSearchService.searchByKeyWord(0, false, "Messen");
//        System.out.println(result);
//    }
//
//    @Test
//    public void journalTest2() {
//        List<Integer> category = new ArrayList<>();
//        category.add(1001);
//        category.add(1002);
//        System.out.println(journalSearchService.searchByCategoryAndKeyWord(0, true, category, "Messen"));
//    }
//
//    @Test
//    public void journalTest3() {
//        List<Integer> monthList = new ArrayList<>();
//        monthList.add(1);
//        System.out.println(journalSearchService.searchByMonth(0, false, monthList));
//    }
//
//    @Test
//    public void paperTest1() {
//        List<Integer> paperTypes = new ArrayList<>();
//        paperTypes.add(1018);
//        System.out.println(hotPaperSearchService.searchByArticleName(0, false, "RESOLVED"));
//    }
//
//    @Test
//    public void paperTest2() {
//        List<Integer> paperTypes = new ArrayList<>();
//        paperTypes.add(1);
//        System.out.println(paperRepository.findAllByKeyWordAndPaperTypeIn("RESOLVED", paperTypes, PageRequest.of(0, 10)).getContent());
//    }
//
//    @Test
//    public void paperType3() {
//        System.out.println(paperRepository.findAllByKeyWord("RESOLVED"));
//    }
//
//    @Test
//    public void paperTest4() {
//        List<PaperType> paperTypes = new ArrayList<>();
//        paperTypes.add(PaperType.HOT_PAPER);
//        System.out.println(paperRepository.findAllByAccessionNumberAndPaperTypeIn("WOS:000419399800005", paperTypes, PageRequest.of(0, 10)).getContent());
//    }
//
//    @Test
//    public void paperTest5() {
//        System.out.println(hotPaperSearchService.searchByAccessionNumber(0, false, "WOS:000419399800005"));
//    }
//
//    @Test
//    public void paperTest6() {
//        System.out.println(hotPaperSearchService.searchByNone(0, false));
//    }
//
//    @Test
//    public void paperTest7() {
//        List<Integer> category = new ArrayList<>();
//        category.add(1018);
//        List<PaperType> paperTypes = new ArrayList<>();
//        paperTypes.add(PaperType.HOT_PAPER);
//        System.out.println(paperRepository.findAllByCategory_CategoryIdInAndPaperTypeIn(category, paperTypes, PageRequest.of(0, 10)));
//    }
//
//    @Test
//    public void journalTest4() {
//        Time max = timeRepository.findMax().orElse(null);
//        Time second = timeRepository.findSecond(max.getTimeId()).orElse(null);
//        List<Integer> maxList = new ArrayList<>();
//        maxList.add(max.getTimeId());
//        List<Integer> secondList = new ArrayList<>();
//        secondList.add(second.getTimeId());
//        List<Journal> journals1 = journalRepository.findAllByTime_TimeIdIn(maxList);
//        List<Journal> journals2 = journalRepository.findAllByTime_TimeIdIn(secondList);
//        Map<String, Journal> map1 = new HashMap<>();
////        Map<String, Journal> map2 = new HashMap<>();
//        for (Journal journal : journals1) {
//            String ll = journal.getFullTitle() + journal.getTitle29()
//                    + journal.getTitle20() + journal.getIssn()
//                    + journal.getEissn();
//            map1.put(ll, journal);
//        }
//        for (Journal journal : journals2) {
//            String ll = journal.getFullTitle() + journal.getTitle29()
//                    + journal.getTitle20() + journal.getIssn()
//                    + journal.getEissn();
//            if (map1.containsKey(ll)) {
//
//            } else {
//                System.out.println("出现差异:" + journal.getJournalId());
//            }
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    @Test
//    public void baseLineTest1() {
//        List<Object> objects = null;
//        try (InputStream inputStream = new FileInputStream("/Users/lwolvej/IdeaProjects/paper/src/main/resources/BaselinePercentiles 201801.xlsx")) {
//            AnalysisEventListener listener = new BaseLineExcelListener();
//            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
//            reader.read();
//            objects = ((BaseLineExcelListener) listener).getVector();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int yearNum = 0;
//        String maxYear = "";
//        String minYear = "Z";
//        boolean notContainsAllYear = true;
//        List<BaseLine> baseLines = new ArrayList<>();
//        if (objects != null) {
//            int size = objects.size();
//            for (int i = 1; i < size; i++) {
//                if (i == 2) {
//                    List<Object> objects1 = (List<Object>) objects.get(2);
//                    for (Object o : objects1) {
//                        if (o != null) {
//                            String year = ((String) o).trim();
//                            if (year.equals("RESEARCH FIELDS")) {
//                                continue;
//                            }
//                            if (year.equals("ALL YEARS")) {
//                                notContainsAllYear = false;
//                                continue;
//                            }
//                            if (year.compareTo(maxYear) > 0) {
//                                maxYear = year;
//                            }
//                            if (year.compareTo(minYear) < 0) {
//                                minYear = year;
//                            }
//                            yearNum++;
//                        }
//                    }
//                    if (notContainsAllYear) {
//                        throw new RuntimeException("没有包含规定数据");
//                    }
//                    int tempYearNum = Integer.parseInt(maxYear) - Integer.parseInt(minYear) + 1;
//                    if (tempYearNum != yearNum) {
//                        throw new RuntimeException("年的数据不规范!");
//                    } else {
//                        yearNum++;
//                    }
//                } else if ((i - 3) % 7 == 0 && i != 164) {
//                    String categoryName = null;
//                    List<Object> objects1 = (List<Object>) objects.get(i);
//                    for (Object o : objects1) {
//                        if (o != null) {
//                            categoryName = ((String) o).trim();
//                            break;
//                        }
//                    }
//                    Category category;
//                    if (!categoryRepository.existsByCategoryName(categoryName)) {
//                        throw new RuntimeException("Category不存在:" + categoryName);
//                    } else {
//                        category = categoryRepository.findByCategoryName(categoryName).orElse(null);
//                    }
//                    for (int k = 0; k < 6; k++) {
//                        List<Object> objects2 = (List<Object>) objects.get(k + i + 1);
//                        int indexP = 0;
//
//                        String baseLinePre = "";
//                        List<String> valueList = new ArrayList<>();
//                        for (Object o : objects2) {
//                            if (o != null) {
//                                if (indexP == 0) {
//                                    baseLinePre = ((String) o).trim();
//                                    indexP = 1;
//                                } else {
//                                    valueList.add(((String) o).trim());
//                                }
//                            }
//                        }
//                        int valueSize = valueList.size();
//                        if (valueSize != 12) {
//                            System.out.println(categoryName + " " + valueSize + " " + k);
//                            throw new RuntimeException("结果不正确");
//                        }
//                        int minYearInt = Integer.parseInt(minYear);
//                        for (int l = 0; l < 12; l++) {
//                            BaseLine baseLine = new BaseLine();
//                            if (l == 11) {
//                                baseLine.setYear("ALL YEARS");
//                            } else {
//                                baseLine.setYear(Integer.toString(minYearInt + l));
//                            }
//                            baseLine.setPercent(baseLinePre);
//                            baseLine.setValue(Integer.parseInt(valueList.get(l)));
//                            baseLine.setCategory(category);
//                            baseLine.setBaseLineId(baseLinePre + category.getCategoryName() + baseLine.getYear());
//                            baseLines.add(baseLine);
//                        }
//                    }
//                    i += 5;
//                }
//            }
//        }
//        baseLines.forEach(System.out::println);
//        Map<String, BaseLine> baseLineMap = new HashMap<>();
//        for (BaseLine baseLine : baseLines) {
//            if (baseLineMap.containsKey(baseLine.getBaseLineId())) {
//                System.out.println("出现错误: " + baseLine);
//            }
//            baseLineMap.put(baseLine.getBaseLineId(), baseLine);
//        }
//        System.out.println(baseLines.size());
//    }
//
//    @Test
//    public void baseLineTest2() {
//        System.out.println(baseLineRepository.findAllByYear("ALL YEARS"));
//    }
//
//    @Test
//    public void baseLineTest3() {
//        System.out.println(baseLineService.searchByAll());
//    }
//
//    @Test
//    public void redisRepository() {
//        redisRepository.delByPattern("journal_*");
//    }
//
//}
