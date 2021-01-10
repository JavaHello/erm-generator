package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;

import java.util.List;
import java.util.Optional;

/**
 * @author kaiv2
 */
public interface IIndexListDiff extends IDiff<List<Index>, List<DiffIndex>> {

    default String diffId(Index index) {
        return Optional.ofNullable(index.getId()).orElseGet(index::getIndexName);
    }
}
