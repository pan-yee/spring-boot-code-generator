package com.bins.code.generator.query;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.bins.code.generator.config.DataSourceConfig;
import com.bins.code.generator.config.StrategyConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class DbQueryDecorator extends AbstractDbQuery {

    private final IDbQuery dbQuery;
    private final Connection connection;
    private final DbType dbType;
    private final StrategyConfig strategyConfig;
    private final String schema;

    public DbQueryDecorator(DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig) {
        this.dbQuery = dataSourceConfig.getDbQuery();
        this.connection = dataSourceConfig.getConn();
        this.dbType = dataSourceConfig.getDbType();
        this.strategyConfig = strategyConfig;
        this.schema = dataSourceConfig.getSchemaName();
    }

    @Override
    public String tablesSql() {
        String tablesSql = dbQuery.tablesSql();
        if (strategyConfig.isEnableSqlFilter()) {
            /** StringBuilder sql = new StringBuilder(tablesSql);
            LikeTable table;
            Set<String> tables;
            if ((table = strategyConfig.getLikeTable()) != null) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" LIKE '").append(table.getValue()).append("'");
            } else if ((table = strategyConfig.getNotLikeTable()) != null) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" NOT LIKE '").append(table.getValue()).append("'");
            }
            if (!(tables = strategyConfig.getInclude()).isEmpty()) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" IN (")
                    .append(tables.stream().map(tb -> "'" + tb + "'").collect(Collectors.joining(","))).append(")");
            } else if (!(tables = strategyConfig.getExclude()).isEmpty()) {
                sql.append(" AND ").append(dbQuery.tableName()).append(" NOT IN (")
                    .append(tables.stream().map(tb -> "'" + tb + "'").collect(Collectors.joining(","))).append(")");
            }
            return sql.toString();*/
        }
        return tablesSql;
    }

    @Override
    public String tableFieldsSql() {
        return dbQuery.tableFieldsSql();
    }

    /**
     * 扩展{@link #tableFieldsSql()}方法
     *
     * @param tableName 表名
     * @return 查询表字段语句
     */
    public String tableFieldsSql(String tableName) {
        String tableFieldsSql = this.tableFieldsSql();
        tableFieldsSql = String.format(tableFieldsSql, tableName);
        return tableFieldsSql;
    }

    @Override
    public String tableName() {
        return dbQuery.tableName();
    }

    @Override
    public String tableComment() {
        return dbQuery.tableComment();
    }

    @Override
    public String fieldName() {
        return dbQuery.fieldName();
    }

    @Override
    public String fieldType() {
        return dbQuery.fieldType();
    }

    @Override
    public String fieldComment() {
        return dbQuery.fieldComment();
    }

    @Override
    public String fieldKey() {
        return dbQuery.fieldKey();
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) {
        try {
            return dbQuery.isKeyIdentity(results);
        } catch (SQLException e) {
            log.warn("判断主键自增错误:{}", e.getMessage());
            // ignore 这个看到在查H2的时候出了异常，先忽略这个异常了.
        }
        return false;
    }

    @Override
    public String[] fieldCustom() {
        return dbQuery.fieldCustom();
    }

    public Map<String, Object> getCustomFields(ResultSet resultSet) {
        String[] fcs = this.fieldCustom();
        if (null != fcs) {
            Map<String, Object> customMap = CollectionUtils.newHashMapWithExpectedSize(fcs.length);
            for (String fc : fcs) {
                try {
                    customMap.put(fc, resultSet.getObject(fc));
                } catch (SQLException sqlException) {
                    throw new RuntimeException("获取自定义字段错误:", sqlException);
                }
            }
            return customMap;
        }
        return Collections.emptyMap();
    }

    /**
     * 执行 SQL 查询，回调返回结果
     *
     * @param sql      执行SQL
     * @param consumer 结果处理
     * @throws SQLException
     */
    public void execute(String sql, Consumer<ResultSetWrapper> consumer) throws SQLException {
        log.debug("执行SQL:{}", sql);
        int count = 0;
        long start = System.nanoTime();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                consumer.accept(new ResultSetWrapper(resultSet, this, this.dbType));
                count++;
            }
            long end = System.nanoTime();
            log.debug("返回记录数:{},耗时(ms):{}", count, (end - start) / 1000000);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        Optional.ofNullable(connection).ifPresent((con) -> {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
    }

    public static class ResultSetWrapper {

        private final IDbQuery dbQuery;

        private final ResultSet resultSet;

        private final DbType dbType;

        ResultSetWrapper(ResultSet resultSet, IDbQuery dbQuery, DbType dbType) {
            this.resultSet = resultSet;
            this.dbQuery = dbQuery;
            this.dbType = dbType;
        }

        public ResultSet getResultSet() {
            return resultSet;
        }

        public String getStringResult(String columnLabel) {
            try {
                return resultSet.getString(columnLabel);
            } catch (SQLException sqlException) {
                throw new RuntimeException(String.format("读取[%s]字段出错!", columnLabel), sqlException);
            }
        }

        /**
         * @return 获取字段注释
         */
        public String getFiledComment() {
            return getComment(dbQuery.fieldComment());
        }

        /**
         * 获取格式化注释
         *
         * @param columnLabel 字段列
         * @return 注释
         */
        private String getComment(String columnLabel) {
            return StringUtils.isNotBlank(columnLabel) ? formatComment(getStringResult(columnLabel)) : StringUtils.EMPTY;
        }

        /**
         * 获取表注释
         *
         * @return 表注释
         */
        public String getTableComment() {
            return getComment(dbQuery.tableComment());
        }

        /**
         * @param comment 注释
         * @return 格式化内容
         */
        public String formatComment(String comment) {
            return StringUtils.isBlank(comment) ? StringUtils.EMPTY : comment.replaceAll("\r\n", "\t");
        }

        /**
         * @return 是否主键
         */
        public boolean isPrimaryKey() {
            String key = this.getStringResult(dbQuery.fieldKey());
            return StringUtils.isNotBlank(key) && "PRI".equalsIgnoreCase(key);
        }
    }
}