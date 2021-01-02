package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Index;
import com.github.javahello.erm.generator.core.model.diff.DiffIndex;
import com.github.javahello.erm.generator.core.util.MapHelper;

import java.util.*;

/**
 * @author kaiv2
 */
public class DefaultIndexListDiffProcess implements IIndexListDiff {

    private IIndexDiff indexDiff = new DefaultIndexDiffProcess();


    @Override
    public Optional<List<DiffIndex>> diff(List<Index> t1, List<Index> t2) {
        if (t1 == t2) {
            return Optional.empty();
        }
        List<Index> d1 = Optional.ofNullable(t1).orElseGet(ArrayList::new);
        List<Index> d2 = Optional.ofNullable(t2).orElseGet(ArrayList::new);
        Map<String, Index> indexMap2 = MapHelper.uniqueGroup(d2, Index::getIndexName);
        List<DiffIndex> diffIndexList = new ArrayList<>();
        for (Index index1 : d1) {
            Index index2 = indexMap2.remove(index1.getIndexName());
            Optional<DiffIndex> diffIndex = indexDiff.diff(index1, index2);
            diffIndex.ifPresent(diffIndexList::add);
        }
        indexMap2.values().stream().sorted(Comparator.comparing(Index::getIndexName))
                .forEach(index2 -> indexDiff.diff(null, index2).ifPresent(diffIndexList::add));
        return Optional.of(diffIndexList);
    }
}
