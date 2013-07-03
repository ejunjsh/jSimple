package com.sky.jSimple.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

public class CollectionUtil {

    // 判断集合是否非空
    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    // 判断集合是否为空
    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }
}
