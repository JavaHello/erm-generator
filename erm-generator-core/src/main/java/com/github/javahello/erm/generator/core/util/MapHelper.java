package com.github.javahello.erm.generator.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author kaiv2
 */
public abstract class MapHelper {

    /**
     * 分组如用重复数据覆盖
     * 
     * @param <K>  key类型
     * @param <V>  数据类型
     * @param data 需要分组数据
     * @param kf   生成key方法
     * @return 分组map
     */
    public static <K, V> Map<K, V> uniqueGroup(List<V> data, Function<V, K> kf) {
        Map<K, V> map = new HashMap<>();
        for (V v : data) {
            map.put(kf.apply(v), v);
        }
        return map;
    }

}