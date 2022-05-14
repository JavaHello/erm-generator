package com.github.javahello.erm.generator.core.api;

import com.github.javahello.erm.generator.core.model.ErmDiffEnv;
import com.github.javahello.erm.generator.core.model.db.Column;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiTransformer;
import org.jxls.util.JxlsHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 比较差异输出 sql
 */
public class ErmExcelGenerator extends AbstractGenerator {

    private String templateFile = "/templates/export_tables.xlsx";

    public ErmExcelGenerator(ErmDiffEnv ermDiffEnv) {
        super(ermDiffEnv);
        Optional.ofNullable(ermDiffEnv.getTemplateFile())
                .ifPresent(this::setTemplateFile);
    }

    protected String outFileName;

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    public void setTemplateFile(String templateFile) {
        this.templateFile = templateFile;
    }

    @Override
    protected void beforeExec() {
        super.beforeExec();
        if (outFileName == null) {
            outFileName = ermDiffEnv.getOutFilePath() + File.separator + ermDiffEnv.getDbName() + ".xlsx";
        }
    }

    public static class TableUtil {
        private final Map<String, Integer> countMap = new HashMap<>();


        public String idxName(List<Column> columns) {
            return columns.stream().map(Column::getColumnName).collect(Collectors.joining(", "));
        }

        public void countInit(String key, int v) {
            countMap.put(key, v);
        }

        public Integer countAt(String key) {
            return countMap.computeIfPresent(key, (k, v) -> v + 1);
        }
    }

    @Override
    protected void runExec() throws IOException {
        Context context = PoiTransformer.createInitialContext();
        context.putVar("home", "#表清单!A1");
        context.putVar("dbName", ermDiffEnv.getDbName());
        context.putVar("tables", newCache.getTables());
        TableUtil tableUtil = new TableUtil();
        tableUtil.countInit("tablesIndex", 0);
        context.putVar("tbuitl", tableUtil);

        try (InputStream in = getClass().getResourceAsStream(templateFile)) {
            File outFile = new File(outFileName);
            try (FileOutputStream out = new FileOutputStream(outFile)) {
                JxlsHelper.getInstance().setUseFastFormulaProcessor(false).processTemplate(in, out, context);
            }
        }
    }

    @Override
    protected void errorExec(Exception exception) {
        log.error("导出 Excel 异常", exception);
    }

}
