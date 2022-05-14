package com.github.javahello.erm.generator.core.tbdiff;

import com.github.javahello.erm.generator.core.model.db.Column;

import java.util.Objects;

/**
 * @author kaiv2
 */
public class DefaultPrimaryKeyDiffProcess extends DefaultColumnDiffProcess implements IPrimaryKeyDiff {

    @Override
    protected boolean doDiff(Column d1, Column d2) {
        return !Objects.equals(d1.getColumnName(), d2.getColumnName());
    }
}
