package com.cyberalaer.hybrid.util;

import java.util.Collections;
import java.util.List;

/**
 * Collection Util
 */
public class CollectionUtils {

    /**
     * 列表是否为空
     *
     * @param list 列表
     * @return 是否为空
     */
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static <E> List<E> reverse(List<E> list) {
        if (isEmpty(list)) return list;
        Collections.reverse(list);
        return list;
    }

}
