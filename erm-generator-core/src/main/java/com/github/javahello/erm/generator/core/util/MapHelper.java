package com.github.javahello.erm.generator.core.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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
     * @param mapSupplier map 实现
     * @return 分组map
     */
    public static <K, V> Map<K, V> uniqueGroup(List<V> data, Function<V, K> kf, Supplier<Map<K, V>> mapSupplier) {
        Map<K, V> map = mapSupplier.get();
        for (V v : data) {
            map.put(kf.apply(v), v);
        }
        return map;
    }

    public static <K, V> Map<K, V> uniqueGroup(List<V> data, Function<V, K> kf) {
        return uniqueGroup(data, kf, HashMap::new);
    }

    public static <V, K> Map<K, V> uniqueGroup(V[] values, Function<V, K> kf, Supplier<Map<K, V>> mapSupplier) {
        return uniqueGroup(Arrays.asList(values), kf, mapSupplier);
    }

    public static <V, K> Map<K, V> uniqueGroup(V[] values, Function<V, K> kf) {
        return uniqueGroup(Arrays.asList(values), kf);
    }
}