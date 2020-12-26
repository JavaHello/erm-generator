package com.github.javahello.erm.generator.core.tbdiff;

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
     * @param t1
     * @param t2
     * @return
     */
    Optional<R> diff(T t1, T t2);

}
