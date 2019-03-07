package org.duohuo.paper.utils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public final class ObjectUtil {

    /**
     * 判断list集合是否为null或者空
     *
     * @param data list集合
     * @return 当为null或者为空时返回false
     */
    public static Boolean ifNotNullList(final Collection<?> data) {
        return Optional.ofNullable(data)
                .map(elem -> elem.size() != 0)
                .orElse(false);
    }

    public static Boolean ifNotNullCollection(final Collection<?> data) {
        return Optional.ofNullable(data)
                .map(elem -> elem.size() != 0)
                .orElse(false);
    }

    public static Boolean ifNotNullString(final String data) {
        return Optional.ofNullable(data)
                .map(elem -> elem.length() != 0)
                .orElse(false);
    }
}
