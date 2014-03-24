package com.sky.jSimple.utils;

import java.util.Collection;
import org.apache.commons.collections.CollectionUtils;

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
