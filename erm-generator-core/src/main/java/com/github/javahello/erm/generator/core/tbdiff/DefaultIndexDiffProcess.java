package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.util.DiffHelper;

import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public class DefaultIndexDiffProcess implements IIndexDiff {

    IColumnListDiff indexColumnDiff = new DefaultColumnListDiffProcess(new DefaultPrimaryKeyDiffProcess());
    @Override
    public Optional<DiffIndex> diff(Index t1, Index t2) {
        Index d1 = Optional.ofNullable(t1).orElseGet(Index::new);
        Index d2 = Optional.ofNullable(t2).orElseGet(Index::new);
        boolean diff = false;
        DiffIndex diffIndex = null;
        if (!Objects.equals(d1.getIndexName(), d2.getIndexName())) {
            diff = true;
        }

        if (indexColumnDiff.diff(d1.getColumns(), d2.getColumns()).filter(DiffHelper::isNotEmpty).isPresent()) {
            diff = true;
        }

        if (diff) {
            diffIndex = new DiffIndex();
            diffIndex.setIndexName(DiffHelper.or(d1.getIndexName(), d2.getIndexName()));
            diffIndex.setNewIndex(t1);
            diffIndex.setOldIndex(t2);
        }
        return Optional.ofNullable(diffIndex);
    }
}
