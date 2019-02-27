package org.duohuo.paper.model;

/**
 * @author lwolvej
 */
public enum PaperType {

    /*
     * 高被引：0,4,5,6,10,11,12,14
     * 热点：1,4,7,8,10,11,13,14
     * 本校高被引：2,5,7,9,10,12,13,14
     * 本校热点：3,6,8,9,11,12,13,14
     * */

    HC_PAPER(0),
    HOT_PAPER(1),
    SHC_PAPER(2),
    SHOT_PAPER(3),
    HC_HOT_PAPER(4),
    HC_SHC_PAPER(5),
    HC_SHOT_PAPER(6),
    HOT_SHC_PAPER(7),
    HOT_SHOT_PAPER(8),
    SHC_SHOT_PAPER(9),
    HC_HOT_SHC_PAPER(10),
    HC_HOT_SHOT_PAPER(11),
    HC_SHC_SHOT_PAPER(12),
    HOT_SHC_SHOT_PAPER(13),
    ALL_PAPER(14);

    private Integer value;

    PaperType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static PaperType fromValue(Integer value) {
        for (PaperType type : PaperType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
}
