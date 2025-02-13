package com.bins.code.generator.query;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.IColumnType;
import com.bins.code.generator.jdbc.DatabaseMetaDataWrapper;
import com.bins.code.generator.model.EntityModel;
import com.bins.code.generator.model.po.TableField;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.type.ITypeConvertHandler;
import com.bins.code.generator.type.TypeRegistry;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Mysql无法读取表注释: 链接增加属性
 * # remarks=true&useInformationSchema=true 或者通过{@link # DataSourceConfig.Builder#addConnectionProperty(String, String)}设置
 */
@Slf4j
public class DefaultQuery extends AbstractDatabaseQuery {

    private final TypeRegistry typeRegistry;

    public DefaultQuery(ConfigBuilder configBuilder) {
        super(configBuilder);
        typeRegistry = new TypeRegistry(configBuilder.getGlobalConfig());
    }

    @Override
    public List<TableInfo> queryTables() {
        boolean isInclude = strategyConfig.getInclude().size() > 0;
        boolean isExclude = strategyConfig.getExclude().size() > 0;
        List<DatabaseMetaDataWrapper.Table> tables = getTables();
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyList();
        }
        List<TableInfo> tableList = Lists.newArrayListWithCapacity(tables.size());
        List<TableInfo> includeTableList = new ArrayList<>();
        List<TableInfo> excludeTableList = new ArrayList<>();
        for (DatabaseMetaDataWrapper.Table table: tables) {
            String tableName = table.getName();
            if (StringUtils.isBlank(tableName)) {
                continue;
            }
            TableInfo tableInfo = new TableInfo(this.configBuilder, tableName);
            tableInfo.setComment(table.getRemarks());
            if (isInclude && strategyConfig.matchIncludeTable(tableName)) {
                includeTableList.add(tableInfo);
            } else if (isExclude && strategyConfig.matchExcludeTable(tableName)) {
                excludeTableList.add(tableInfo);
            }
            tableList.add(tableInfo);
        }
        filter(tableList, includeTableList, excludeTableList);
        tableList.forEach(this::convertTableFields);
        return tableList;
    }

    protected List<DatabaseMetaDataWrapper.Table> getTables() {
        return databaseMetaDataWrapper.getTables(null, new String[]{"TABLE"});
    }

    protected void convertTableFields(TableInfo tableInfo) {
        String tableName = tableInfo.getName();
        Map<String, DatabaseMetaDataWrapper.Column> columnsInfoMap = getColumnsInfo(tableName);
        EntityModel entity = tableInfo.getEntityModel();
        columnsInfoMap.forEach((k, columnInfo) -> {
            TableField.MetaInfo metaInfo = new TableField.MetaInfo(columnInfo);
            String columnName = columnInfo.getName();
            TableField field = new TableField(this.configBuilder, columnName, tableInfo);
            if (columnInfo.isPrimaryKey()) {
                field.primaryKey(columnInfo.isAutoIncrement());
                tableInfo.setHavePrimaryKey(true);
                entity.setHavePrimaryKey(true);
            }
            field.setColumnName(columnName).setComment(columnInfo.getRemarks());
            String propertyName = entity.getNameConvert().propertyNameConvert(field);
            IColumnType columnType = typeRegistry.getColumnType(metaInfo);
            ITypeConvertHandler typeConvertHandler = dataSourceConfig.getTypeConvertHandler();
            if (typeConvertHandler != null) {
                columnType = typeConvertHandler.convert(globalConfig, typeRegistry, metaInfo);
            }
            field.setPropertyName(propertyName, columnType);
            field.setMetaInfo(metaInfo);
            tableInfo.addField(field);
        });
        tableInfo.processTable();
    }

    protected Map<String, DatabaseMetaDataWrapper.Column> getColumnsInfo(String tableName) {
        return databaseMetaDataWrapper.getColumnsInfo(tableName, true);
    }
}
