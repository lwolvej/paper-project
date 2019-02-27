package org.duohuo.paper.utils;


import java.lang.reflect.Field;

public final class ValidationUtil {

    private ValidationUtil() {
    }

    public static Boolean validation(Object object) {
        Class cla = object.getClass();
        boolean flag = true;
        for (Field elem : cla.getDeclaredFields()) {
            elem.setAccessible(true);
            Object val;
            try {
                val = elem.get(object);
            } catch (IllegalAccessException e) {
                return false;
            }
            if (val == null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
