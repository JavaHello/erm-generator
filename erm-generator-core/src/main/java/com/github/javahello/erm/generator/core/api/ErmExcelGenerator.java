package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.model.ErmDiffEnv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import com.github.javahello.erm.generator.core.model.db.Column;
import com.github.javahello.erm.generator.core.model.db.Table;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

/**
 * 比较差异输出 sql
 */
public class ErmExcelGenerator extends AbstractGenerator {

    private String templateFile = "/templates/export_tables.xls";

    public ErmExcelGenerator(ErmDiffEnv ermDiffEnv) {
        super(ermDiffEnv);
    }

    protected String outFileName;

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    @Override
    protected void beforeExec() {
        super.beforeExec();
        if (outFileName == null) {
            outFileName = ermDiffEnv.getOutDDLFilePath() + File.separator + ermDiffEnv.getDbName() + ".xls";
        }
    }

    public static class TableUtil {
        public String idxName(List<Column> columns) {
            return columns.stream().map(Column::getColumnName).collect(Collectors.joining(", "));
        }
    }

    @Override
    protected void runExec() throws IOException {
        Context context = PoiTransformer.createInitialContext();
        context.putVar("home", "#表清单!A1");
        context.putVar("dbName", ermDiffEnv.getDbName());
        context.putVar("tables", newCache.getTables());
        context.putVar("tbuitl", new TableUtil());

        try (InputStream in = getClass().getResourceAsStream(templateFile)) {
            File outFile = new File(outFileName);
            try(FileOutputStream out = new FileOutputStream(outFile)) {
                JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(in, out, context);
            }
        }
    }

    @Override
    protected void errorExec(Exception exception) {
        log.error("导出 Excel 异常", exception);
    }

}
