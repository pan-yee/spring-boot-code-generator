package com.bins.code.generator.config.builder;

import com.bins.code.generator.config.*;
import com.bins.code.generator.config.handler.PathInfoHandler;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.query.IDatabaseQuery;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Pattern;

public class ConfigBuilder {

    /**
     * 模板路径配置信息
     */
    private final TemplateConfig templateConfig;

    /**
     * 数据库表信息
     */
    private final List<TableInfo> tableInfoList = new ArrayList<>();

    /**
     * 路径配置信息
     */
    private final Map<String, String> pathInfo = new HashMap<>();

    /**
     * 策略配置信息
     */
    private StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 过滤正则
     */
    private static final Pattern REGX = Pattern.compile("[~!/@#$%^&*()+\\\\\\[\\]|{};:'\",<.>?]+");

    /**
     * 包配置信息
     */
    private final PackageConfig packageConfig;

    /**
     * 数据库配置信息
     */
    private final DataSourceConfig dataSourceConfig;

    /**
     * 数据查询实例
     */
    private final IDatabaseQuery databaseQuery;

    /**
     * 在构造器中处理配置
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param templateConfig   模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilder(PackageConfig packageConfig, DataSourceConfig dataSourceConfig,
                         StrategyConfig strategyConfig, TemplateConfig templateConfig,
                         GlobalConfig globalConfig) {
        this.dataSourceConfig = dataSourceConfig;
        this.strategyConfig = Optional.ofNullable(strategyConfig).orElseGet(GeneratorBuilder::strategyConfig);
        this.globalConfig = Optional.ofNullable(globalConfig).orElseGet(GeneratorBuilder::globalConfig);
        this.templateConfig = Optional.ofNullable(templateConfig).orElseGet(GeneratorBuilder::templateConfig);
        this.packageConfig = Optional.ofNullable(packageConfig).orElseGet(GeneratorBuilder::packageConfig);
        this.pathInfo.putAll(new PathInfoHandler(this.globalConfig, this.templateConfig, this.packageConfig).getPathInfo());
        Class<? extends IDatabaseQuery> databaseQueryClass = dataSourceConfig.getDatabaseQueryClass();
        try {
            Constructor<? extends IDatabaseQuery> declaredConstructor = databaseQueryClass.getDeclaredConstructor(this.getClass());
            this.databaseQuery = declaredConstructor.newInstance(this);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("创建IDatabaseQuery实例出现错误:", exception);
        }
    }

    /**
     * 判断表名是否为正则表名(这表名规范比较随意,只能尽量匹配上特殊符号)
     *
     * @param tableName 表名
     * @return 是否正则
     */
    public static boolean matcherRegTable(String tableName) {
        return REGX.matcher(tableName).find();
    }

    public ConfigBuilder setStrategyConfig(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
        return this;
    }

    public ConfigBuilder setGlobalConfig(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    public TemplateConfig getTemplateConfig() {
        return templateConfig;
    }

    public List<TableInfo> getTableInfoList() {
        if (tableInfoList.isEmpty()) {
            List<TableInfo> tableInfos = this.databaseQuery.queryTables();
            if (!tableInfos.isEmpty()) {
                this.tableInfoList.addAll(tableInfos);
            }
        }
        return tableInfoList;
    }

    public Map<String, String> getPathInfo() {
        return pathInfo;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public PackageConfig getPackageConfig() {
        return packageConfig;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }
}