package org.duohuo.paper.utils;

import org.duohuo.paper.exceptions.NotFoundException;

public final class TimeUtil {

    private TimeUtil() {

    }

    public static Integer createTimeIdByYearAndMonth(Integer year, Integer month) {
        String yearStr = Integer.toString(year);
        String monthStr;
        if (month < 10 && month > 1) {
            monthStr = "0" + month;
        } else {
            monthStr = Integer.toString(month);
        }
        return (yearStr.concat(monthStr)).hashCode();
    }
}
