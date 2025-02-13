package com.bins.code.generator.query;

import com.bins.code.generator.config.DataSourceConfig;
import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.PackageConfig;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.jdbc.DatabaseMetaDataWrapper;
import com.bins.code.generator.model.po.TableInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractDatabaseQuery implements IDatabaseQuery {

    protected final ConfigBuilder configBuilder;

    protected final DataSourceConfig dataSourceConfig;

    protected final StrategyConfig strategyConfig;

    protected final PackageConfig packageConfig;

    protected final GlobalConfig globalConfig;

    protected final DbQueryDecorator dbQuery;

    protected final DatabaseMetaDataWrapper databaseMetaDataWrapper;

    public AbstractDatabaseQuery(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        this.dataSourceConfig = configBuilder.getDataSourceConfig();
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.packageConfig = configBuilder.getPackageConfig();
        this.dbQuery = new DbQueryDecorator(dataSourceConfig, strategyConfig);
        this.globalConfig = configBuilder.getGlobalConfig();
        this.databaseMetaDataWrapper = new DatabaseMetaDataWrapper(dataSourceConfig);
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public DataSourceConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    protected void filter(List<TableInfo> tableList, List<TableInfo> includeTableList, List<TableInfo> excludeTableList) {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        if (isExclude || isInclude) {
            Map<String, String> notExistTables = new HashSet<>(isExclude ? strategyConfig.getExclude() : strategyConfig.getInclude())
                .stream()
                .filter(s -> !ConfigBuilder.matcherRegTable(s))
                .collect(Collectors.toMap(String::toLowerCase, s -> s, (o, n) -> n));
            // 将已经存在的表移除，获取配置中数据库不存在的表
            for (TableInfo tabInfo : tableList) {
                if (notExistTables.isEmpty()) {
                    break;
                }
                //解决可能大小写不敏感的情况导致无法移除掉
                notExistTables.remove(tabInfo.getName().toLowerCase());
            }
            if (notExistTables.size() > 0) {
                log.warn("表[{}]在数据库中不存在！！！", String.join(Constant.COMMA, notExistTables.values()));
            }
            // 需要反向生成的表信息
            if (isExclude) {
                tableList.removeAll(excludeTableList);
            } else {
                tableList.clear();
                tableList.addAll(includeTableList);
            }
        }
    }
}
