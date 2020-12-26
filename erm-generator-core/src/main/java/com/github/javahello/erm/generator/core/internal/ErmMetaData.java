package com.github.javahello.erm.generator.core.internal;

import java.util.List;
import java.util.Optional;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Table;

/**
 * @author kaiv2
 */
public interface ErmMetaData extends TableCache {

    Optional<List<Column>> getPrimaryKeys(String table);

    Optional<List<Column>> getColumns(String table);


}
