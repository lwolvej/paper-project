package org.duohuo.paper.facade;

import com.alibaba.excel.metadata.BaseRowModel;
import org.duohuo.paper.constants.FolderConstant;
import org.duohuo.paper.constants.SearchTypeConstant;
import org.duohuo.paper.convert.PaperConverter;
import org.duohuo.paper.exceptions.NotFoundException;
import org.duohuo.paper.exceptions.ZipFileException;
import org.duohuo.paper.model.SchoolPaperImage;
import org.duohuo.paper.model.dto.ConditionDto;
import org.duohuo.paper.model.dto.DeleteDto;
import org.duohuo.paper.model.dto.DownloadDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperExcelService;
import org.duohuo.paper.service.PaperSearchService;
import org.duohuo.paper.service.SchoolPaperImageService;
import org.duohuo.paper.utils.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component("paperFacade")
public class PaperFacade {

    @Resource(name = "paperSearchServiceImpl")
    private PaperSearchService paperSearchService;

    @Resource(name = "paperExcelServiceImpl")
    private PaperExcelService paperExcelService;

    @Resource(name = "schoolPaperImageServiceImpl")
    private SchoolPaperImageService schoolPaperImageService;

    public List<SchoolPaperImage> getImageByIdList(List<Long> paperIdList) {
        return schoolPaperImageService.getAllByPaperId(paperIdList);
    }

    public JsonResult uploadFileFacade(final MultipartFile file, final Integer year, final Integer month, final Integer type) {
        if (file == null || year == null || month == null) {
            throw new NotFoundException("论文上传信息不完整");
        }
        String jarPath = ExcelUtil.getJarPath();
        String random = new Date().toString();
        String resultPath = jarPath + File.separator;
        if (type == 1) {
            resultPath = resultPath.concat(FolderConstant.TEMP_FILE_ESI_HIGHLY_PAPER + random);
        } else if (type == 2) {
            resultPath = resultPath.concat(FolderConstant.TEMP_FILE_ESI_HOT_PAPER + random);
        } else if (type == 3) {
            resultPath = resultPath.concat(FolderConstant.TEMP_FILE_SCHOOL_HIGHLY_PAPER + random);
        } else {
            resultPath = resultPath.concat(FolderConstant.TEMP_FILE_SCHOOL_HOT_PAPER + random);
        }
        String fileName = file.getOriginalFilename();
        if (RegexUtil.zipFileValidation(fileName)) {
            throw new ZipFileException("上传zip文件不规范:" + fileName);
        }
        byte[] data;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new ZipFileException("处理zip文件出错:" + e.getMessage());
        }
        List<String> filePathList = FileUtil.decompressZipFile(data, jarPath, resultPath, fileName);
        if (type == 1 || type == 2) {
            return paperExcelService.insertEsiPaperExcelData(filePathList, year, month, fileName, resultPath, type);
        } else {
            return paperExcelService.insertSchoolPaperExcelData(filePathList, year, month, fileName, resultPath, type);
        }
    }

    public JsonResult deletePaperFacade(final DeleteDto deleteDto, final Integer type) {
        if (deleteDto == null) {
            throw new NotFoundException("删除信息不完整" + type);
        }
        if (deleteDto.getMonth() == null || deleteDto.getYear() == null) {
            throw new NotFoundException("删除信息不完整" + type);
        }
        return paperExcelService.deleteByYearAndMonth(deleteDto.getYear(), deleteDto.getMonth(), type);
    }

    public ResponseEntity<byte[]> downloadEsiPaperFacade(final DownloadDto downloadDto) {
        if (downloadDto == null) {
            throw new NotFoundException("缺少论文下载信息");
        }
        List<Long> paperIdList = downloadDto.getData();
        if (!ObjectUtil.ifNotNullCollection(paperIdList)) {
            throw new NotFoundException("缺少论文下载信息");
        }
        List<BaseRowModel> paperList = getDownloadModel(paperIdList);
        if (!ObjectUtil.ifNotNullList(paperList)) {
            throw new NotFoundException("缺少论文相关下载信息");
        }
        byte[] data = ExcelUtil.getDownByte(paperList, 1);
        String fileName = Integer.toString(("PAPER " + new Date()).hashCode());
        return DownloadUtil.getResponseEntity(fileName, data);
    }

    public List<BaseRowModel> getDownloadModel(final List<Long> paperIdList) {
        return paperSearchService.getPaperListById(paperIdList)
                .stream().map(PaperConverter::convertEntityToDownload)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "paper_facade_search_all", keyGenerator = "redisKeyGenerator")
    public JsonResult paperSearchFacade(final SearchDto searchDto, final Integer type) {
        if (searchDto == null) {
            throw new NotFoundException("论文检索条件为空");
        }
        Integer page = searchDto.getPage();
        Boolean ifDesc = searchDto.getIfDesc();
        ConditionDto conditionDto = searchDto.getConditionData();
        if (page == null) page = 0;
        if (ifDesc == null) ifDesc = true;
        if (conditionDto == null) conditionDto = new ConditionDto();
        if (!ObjectUtil.ifNotNullCollection(conditionDto.getSubject())) {
            if (!ObjectUtil.ifNotNullCollection(conditionDto.getYear())) {
                if (!ObjectUtil.ifNotNullCollection(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByNone(page, ifDesc, type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByAccessionNumber(page, ifDesc, searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByArticleName(page, ifDesc, searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByDoi(page, ifDesc, searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByMonth(page, ifDesc, conditionDto.getMonth(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByMonthAndAccessionNumber(page, ifDesc, conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByMonthAndArticleName(page, ifDesc, conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByMonthAndDoi(page, ifDesc, conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullCollection(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByYear(page, ifDesc, conditionDto.getYear(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByYearAndAccessionNumber(page, ifDesc, conditionDto.getYear(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByYearAndArticleName(page, ifDesc, conditionDto.getYear(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByYearAndDoi(page, ifDesc, conditionDto.getYear(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByYearAndMonth(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByYearAndMonthAndAccessionNumber(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByYearAndMonthAndArticleName(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByYearAndMonthAndDoi(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                }
            }
        } else {
            if (!ObjectUtil.ifNotNullCollection(conditionDto.getYear())) {
                if (!ObjectUtil.ifNotNullCollection(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategory(page, ifDesc, conditionDto.getSubject(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndArticleName(page, ifDesc, conditionDto.getSubject(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndDoi(page, ifDesc, conditionDto.getSubject(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategoryAndMonth(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndMonthAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndMonthAndArticleName(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndMonthAndDoi(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullCollection(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategoryAndYear(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndYearAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndYearAndArticleName(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndYearAndDoi(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategoryAndYearAndMonth(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), type);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new NotFoundException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndYearAndMonthAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndYearAndMonthAndArticleName(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                case SearchTypeConstant.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndYearAndMonthAndDoi(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord(), type);
                                default:
                                    throw new NotFoundException("未知的关键词类别");
                            }
                        }
                    }
                }
            }
        }
    }
}
