package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.Optional;

/**
 * 差异比对
 *
 * @author kaiv2
 */
public interface IDiff<T, R> {


    /**
     * 差异比对
     *
     * @param t1 表1
     * @param t2 表2
     * @return 差异
     */
    Optional<R> diff(T t1, T t2);

    default String diffId(T t) {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName() + "#diffId 未实现");
    }

}
