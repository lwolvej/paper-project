package org.duohuo.paper.controller;

import org.duohuo.paper.Constants;
import org.duohuo.paper.exceptions.CustomException;
import org.duohuo.paper.model.dto.ConditionDto;
import org.duohuo.paper.model.dto.SearchDto;
import org.duohuo.paper.model.result.JsonResult;
import org.duohuo.paper.service.PaperSearchService;
import org.duohuo.paper.utils.ObjectUtil;

public abstract class PaperController {

    JsonResult getJsonResult(Integer year, Integer month, PaperSearchService paperSearchService) {
        return paperSearchService.deleteByYearAndMonth(year, month);
    }

    JsonResult getJsonResult(SearchDto searchDto, PaperSearchService paperSearchService) {
        Integer page = searchDto.getPage();
        Boolean ifDesc = searchDto.getIfDesc();
        ConditionDto conditionDto = searchDto.getConditionData();
        if (page == null) page = 0;
        if (ifDesc == null) ifDesc = true;
        if (conditionDto == null) conditionDto = new ConditionDto();
        if (!ObjectUtil.ifNotNullList(conditionDto.getSubject())) {
            if (!ObjectUtil.ifNotNullList(conditionDto.getYear())) {
                if (!ObjectUtil.ifNotNullList(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByNone(page, ifDesc);
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByAccessionNumber(page, ifDesc, searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByArticleName(page, ifDesc, searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByDoi(page, ifDesc, searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByMonth(page, ifDesc, conditionDto.getMonth());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByMonthAndAccessionNumber(page, ifDesc, conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByMonthAndArticleName(page, ifDesc, conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByMonthAndDoi(page, ifDesc, conditionDto.getMonth(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullList(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByYear(page, ifDesc, conditionDto.getYear());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByYearAndAccessionNumber(page, ifDesc, conditionDto.getYear(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByYearAndArticleName(page, ifDesc, conditionDto.getYear(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByYearAndDoi(page, ifDesc, conditionDto.getYear(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByYearAndMonth(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByYearAndMonthAndAccessionNumber(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByYearAndMonthAndArticleName(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByYearAndMonthAndDoi(page, ifDesc, conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                }
            }
        } else {
            if (!ObjectUtil.ifNotNullList(conditionDto.getYear())) {
                if (!ObjectUtil.ifNotNullList(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategory(page, ifDesc, conditionDto.getSubject());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndArticleName(page, ifDesc, conditionDto.getSubject(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndDoi(page, ifDesc, conditionDto.getSubject(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategoryAndMonth(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndMonthAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndMonthAndArticleName(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndMonthAndDoi(page, ifDesc, conditionDto.getSubject(), conditionDto.getMonth(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                }
            } else {
                if (!ObjectUtil.ifNotNullList(conditionDto.getMonth())) {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategoryAndYear(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndYearAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndYearAndArticleName(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndYearAndDoi(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                } else {
                    if (!ObjectUtil.ifNotNullString(searchDto.getKeyWord())) {
                        return paperSearchService.searchByCategoryAndYearAndMonth(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth());
                    } else {
                        if (!ObjectUtil.ifNotNullString(searchDto.getKeyWordType())) {
                            throw new CustomException("关键字类别未选择!");
                        } else {
                            switch (searchDto.getKeyWordType()) {
                                case Constants.PAPER_KEYWORD_TYPE_AC:
                                    return paperSearchService.searchByCategoryAndYearAndMonthAndAccessionNumber(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_AR:
                                    return paperSearchService.searchByCategoryAndYearAndMonthAndArticleName(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord());
                                case Constants.PAPER_KEYWORD_TYPE_DOI:
                                    return paperSearchService.searchByCategoryAndYearAndMonthAndDoi(page, ifDesc, conditionDto.getSubject(), conditionDto.getYear(), conditionDto.getMonth(), searchDto.getKeyWord());
                                default:
                                    throw new CustomException("未知的关键词类别");
                            }
                        }
                    }
                }
            }
        }
    }
}
