package com.chinatsp.device.utils;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * @author lizhe
 * @date 2020/12/3 17:49
 **/
public final class ObjectUtils {

    @SneakyThrows
    public static void copyFiledValue(Object o1, Object o2) {
        if (o1.getClass() != o2.getClass()) {
            throw new RuntimeException("class o1 is not equal o2");
        }
        Class<?> clazz1 = o1.getClass();
        Class<?> clazz2 = o2.getClass();
        Field[] fields1 = clazz1.getDeclaredFields();
        Field[] fields2 = clazz2.getDeclaredFields();
        for (Field f1 : fields1) {
            f1.setAccessible(true);
            for (Field f2 : fields2) {
                f2.setAccessible(true);
                if (f1.getName().equals(f2.getName())) {
                    Object value = f1.get(o1);
                    f2.set(o2, value);
                }
            }
        }
    }
}
