package org.duohuo.paper.manager;

import org.duohuo.paper.model.PaperType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("paperTypeManager")
public class PaperTypeManager {

    //获取本校的信息类别
    public List<Integer> createSchoolPaperType() {
        List<Integer> paperTypes = new ArrayList<>();
        paperTypes.add(2);
        paperTypes.add(3);
        paperTypes.add(5);
        paperTypes.add(6);
        paperTypes.add(7);
        paperTypes.add(8);
        paperTypes.add(9);
        paperTypes.add(10);
        paperTypes.add(11);
        paperTypes.add(12);
        paperTypes.add(13);
        paperTypes.add(14);
        return paperTypes;
    }

    //1.高被引，2.热点，3.本校高被引，4.本校热点
    public List<PaperType> createPaperType(Integer type) {
        List<PaperType> paperTypes = new ArrayList<>();
        switch (type) {
            case 1: {
                paperTypes.add(PaperType.HC_PAPER);
//                paperTypes.add(PaperType.HC_HOT_PAPER);
//                paperTypes.add(PaperType.HC_SHC_PAPER);
//                paperTypes.add(PaperType.HC_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_HOT_SHC_PAPER);
//                paperTypes.add(PaperType.HC_HOT_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_SHC_SHOT_PAPER);
            }
            break;
            case 2: {
                paperTypes.add(PaperType.HOT_PAPER);
//                paperTypes.add(PaperType.HC_HOT_PAPER);
//                paperTypes.add(PaperType.HOT_SHC_PAPER);
//                paperTypes.add(PaperType.HOT_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_HOT_SHC_PAPER);
//                paperTypes.add(PaperType.HC_HOT_SHOT_PAPER);
//                paperTypes.add(PaperType.HOT_SHC_SHOT_PAPER);
            }
            break;
            case 3: {
                paperTypes.add(PaperType.SHC_PAPER);
//                paperTypes.add(PaperType.HC_SHC_PAPER);
//                paperTypes.add(PaperType.HOT_SHC_PAPER);
//                paperTypes.add(PaperType.SHC_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_HOT_SHC_PAPER);
//                paperTypes.add(PaperType.HOT_SHC_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_SHC_SHOT_PAPER);
            }
            break;
            case 4: {
                paperTypes.add(PaperType.SHOT_PAPER);
//                paperTypes.add(PaperType.HC_SHOT_PAPER);
//                paperTypes.add(PaperType.HOT_SHOT_PAPER);
//                paperTypes.add(PaperType.SHC_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_HOT_SHOT_PAPER);
//                paperTypes.add(PaperType.HC_SHC_SHOT_PAPER);
//                paperTypes.add(PaperType.HOT_SHC_SHOT_PAPER);
            }
            break;
        }
//        paperTypes.add(PaperType.ALL_PAPER);
        return paperTypes;
    }

    //创建论文类别，数字同上
    public List<Integer> createPaperType2(Integer type) {
        List<Integer> paperTypeList = new ArrayList<>();
        switch (type) {
            case 1: {
                paperTypeList.add(0);
                paperTypeList.add(4);
                paperTypeList.add(5);
                paperTypeList.add(6);
                paperTypeList.add(10);
                paperTypeList.add(11);
                paperTypeList.add(12);
                paperTypeList.add(14);
            }
            break;
            case 2: {
                paperTypeList.add(1);
                paperTypeList.add(4);
                paperTypeList.add(7);
                paperTypeList.add(8);
                paperTypeList.add(10);
                paperTypeList.add(11);
                paperTypeList.add(13);
                paperTypeList.add(14);
            }
            break;
            case 3: {
                paperTypeList.add(2);
                paperTypeList.add(5);
                paperTypeList.add(7);
                paperTypeList.add(9);
                paperTypeList.add(10);
                paperTypeList.add(12);
                paperTypeList.add(13);
                paperTypeList.add(14);
            }
            break;
            case 4: {
                paperTypeList.add(3);
                paperTypeList.add(6);
                paperTypeList.add(8);
                paperTypeList.add(9);
                paperTypeList.add(11);
                paperTypeList.add(12);
                paperTypeList.add(13);
                paperTypeList.add(14);
            }
            break;
        }
        return paperTypeList;
    }

//    public PaperType changePaperType(Integer newType, PaperType originType) {
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

    public PaperType getPaperType(Integer newType) {
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
}
