package com.github.javahello.erm.generator.mybatisplus;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.github.javahello.erm.generator.core.internal.ErmRead;
import com.github.javahello.erm.generator.mybatisplus.config.ErmConfigBuilder;
import com.github.javahello.erm.generator.mybatisplus.engine.ErmAbstractTemplateEngine;
import com.github.javahello.erm.generator.mybatisplus.engine.VelocityTemplateEngine;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 生成文件
 *
 * @author YangHu, tangguo, hubin, kaiv2
 * @since 2016-08-30
 */
@Data
@Accessors(chain = true)
public class ErmAutoGenerator  {
    private static final Log logger = LogFactory.getLog(ErmAutoGenerator.class);

    /**
     * 配置信息
     */
    protected ErmConfigBuilder config;
    /**
     * 注入配置
     */
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected ErmInjectionConfig injectionConfig;

    private List<String> ermList;

    /**
     * 数据库表配置
     */
    private StrategyConfig strategy;
    /**
     * 包 相关配置
     */
    private PackageConfig packageInfo;
    /**
     * 模板 相关配置
     */
    private TemplateConfig template;
    /**
     * 全局 相关配置
     */
    private GlobalConfig globalConfig;
    /**
     * 模板引擎
     */
    private ErmAbstractTemplateEngine templateEngine;

    /**
     * 生成代码
     */
    public void execute() {
        logger.debug("==========================准备生成文件...==========================");
        // 初始化配置
        if (null == config) {
            config = new ErmConfigBuilder(packageInfo, new ErmRead(ermList), strategy, template, globalConfig);
            if (null != injectionConfig) {
                injectionConfig.setConfig(config);
            }
        }
        if (null == templateEngine) {
            // 为了兼容之前逻辑，采用 Velocity 引擎 【 默认 】
            templateEngine = new VelocityTemplateEngine();
        }
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(config)).mkdirs().batchOutput().open();
        logger.debug("==========================文件生成完成！！！==========================");
    }

    /**
     * 开放表信息、预留子类重写
     *
     * @param config 配置信息
     * @return ignore
     */
    protected List<TableInfo> getAllTableInfoList(ErmConfigBuilder config) {
        return config.getTableInfoList();
    }

    /**
     * 预处理配置
     *
     * @param config 总配置信息
     * @return 解析数据结果集
     */
    protected ErmConfigBuilder pretreatmentConfigBuilder(ErmConfigBuilder config) {
        /*
         * 注入自定义配置
         */
        if (null != injectionConfig) {
            injectionConfig.initMap();
            config.setInjectionConfig(injectionConfig);
        }
        /*
         * 表信息列表
         */
        List<TableInfo> tableList = this.getAllTableInfoList(config);
        for (TableInfo tableInfo : tableList) {
            /* ---------- 添加导入包 ---------- */
            if (config.getGlobalConfig().isActiveRecord()) {
                // 开启 ActiveRecord 模式
                tableInfo.setImportPackages(Model.class.getCanonicalName());
            }
            if (tableInfo.isConvert()) {
                // 表注解
                tableInfo.setImportPackages(TableName.class.getCanonicalName());
            }
            if (config.getStrategyConfig().getLogicDeleteFieldName() != null && tableInfo.isLogicDelete(config.getStrategyConfig().getLogicDeleteFieldName())) {
                // 逻辑删除注解
                tableInfo.setImportPackages(TableLogic.class.getCanonicalName());
            }
            if (StringUtils.isNotBlank(config.getStrategyConfig().getVersionFieldName())) {
                // 乐观锁注解
                tableInfo.setImportPackages(Version.class.getCanonicalName());
            }
            boolean importSerializable = true;
            if (StringUtils.isNotBlank(config.getStrategyConfig().getSuperEntityClass())) {
                // 父实体
                tableInfo.setImportPackages(config.getStrategyConfig().getSuperEntityClass());
                importSerializable = false;
            }
            if (config.getGlobalConfig().isActiveRecord()) {
                importSerializable = true;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
            // Boolean类型is前缀处理
            if (config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()
                && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
                List<TableField> tableFields = tableInfo.getFields().stream().filter(field -> "boolean".equalsIgnoreCase(field.getPropertyType()))
                    .filter(field -> field.getPropertyName().startsWith("is")).collect(Collectors.toList());
                tableFields.forEach(field -> {
                    //主键为is的情况基本上是不存在的.
                    if (field.isKeyFlag()) {
                        tableInfo.setImportPackages(TableId.class.getCanonicalName());
                    } else {
                        tableInfo.setImportPackages(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
                    }
                    field.setConvert(true);
                    field.setPropertyName(StringUtils.removePrefixAfterPrefixToLower(field.getPropertyName(), 2));
                });
            }
        }
        return config.setTableInfoList(tableList);
    }

    public ErmInjectionConfig getCfg() {
        return injectionConfig;
    }

    public ErmAutoGenerator setCfg(ErmInjectionConfig injectionConfig) {
        this.injectionConfig = injectionConfig;
        return this;
    }
}
