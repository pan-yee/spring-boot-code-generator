package com.bins.code.generator.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.bins.code.generator.convert.ITypeConvert;
import com.bins.code.generator.query.*;
import com.bins.code.generator.type.ITypeConvertHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Data
@Slf4j
public class DataSourceConfig {

    /**
     * 驱动连接的URL
     */
    private String url;

    /**
     * 数据库连接用户名
     */
    private String username;

    /**
     * 数据库连接密码
     */
    private String password;

    /**
     * 数据源实例
     */
    private DataSource dataSource;

    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * schemaName
     */
    private String schemaName;

    /**
     * 数据库信息查询
     */
    private IDbQuery dbQuery;

    /**
     * 类型转换
     */
    private ITypeConvert typeConvert;

    /**
     * 查询方式
     *
     * @see DefaultQuery 默认查询方式，配合{@link #getTypeConvertHandler()} 使用
     * @see // SQLQuery SQL语句查询方式，配合{@link //#typeConvert} 使用
     */
    private Class<? extends AbstractDatabaseQuery> databaseQueryClass = DefaultQuery.class;

    /**
     * 数据库连接属性
     */
    private final Map<String, String> connectionProperties = new HashMap<>();

    /**
     * 类型转换处理
     */
    private ITypeConvertHandler typeConvertHandler;

    public Class<? extends IDatabaseQuery> getDatabaseQueryClass() {
        return databaseQueryClass;
    }

    public ITypeConvertHandler getTypeConvertHandler() {
        return typeConvertHandler;
    }

    /**
     * 获取数据库查询
     */
    public IDbQuery getDbQuery() {
        if (null == dbQuery) {
            DbType dbType = getDbType();
            DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
            dbQuery = Optional.ofNullable(dbQueryRegistry.getDbQuery(dbType))
                    .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));
        }
        return dbQuery;
    }

    /**
     * 判断数据库类型
     *
     * @return 类型枚举值
     */
    public DbType getDbType() {
        return DbType.MYSQL;
    }

    /**
     * 创建数据库连接对象，这方法只需要调用一次
     */
    public Connection getConn() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            } else {
                synchronized (this) {
                    if (dataSource != null) {
                        connection = dataSource.getConnection();
                    } else {
                        Properties properties = new Properties();
                        connectionProperties.forEach(properties::setProperty);
                        properties.put("user", username);
                        properties.put("password", password);
                        this.processProperties(properties);
                        this.connection = DriverManager.getConnection(url, properties);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    private void processProperties(Properties properties) {
        properties.put("remarks", "true");
        properties.put("useInformationSchema", "true");
    }

    public String getSchemaName() {
        return schemaName;
    }

    /**
     * 数据库配置构建者
     */
    public static class Builder implements IConfigBuilder<DataSourceConfig> {

        private final DataSourceConfig dataSourceConfig;

        private Builder() {
            this.dataSourceConfig = new DataSourceConfig();
        }

        /**
         * 构造初始化方法
         *
         * @param url      数据库连接地址
         * @param username 数据库账号
         * @param password 数据库密码
         */
        public Builder(String url, String username, String password) {
            this();
            if (StringUtils.isBlank(url)) {
                throw new RuntimeException("无法创建文件，请正确输入 url 配置信息！");
            }
            this.dataSourceConfig.url = url;
            this.dataSourceConfig.username = username;
            this.dataSourceConfig.password = password;
        }

        /**
         * 构造初始化方法
         *
         * @param dataSource 外部数据源实例
         */
        public Builder(DataSource dataSource) {
            this();
            this.dataSourceConfig.dataSource = dataSource;
            try {
                Connection conn = dataSource.getConnection();
                this.dataSourceConfig.url = conn.getMetaData().getURL();
                this.dataSourceConfig.connection = conn;
                try {
                    this.dataSourceConfig.schemaName = conn.getSchema();
                } catch (Throwable exception) {
                    log.error("获取schemaName异常", exception);
                }
                this.dataSourceConfig.username = conn.getMetaData().getUserName();
            } catch (SQLException ex) {
                throw new RuntimeException("构建数据库配置对象失败!", ex);
            }
        }

        /**
         * 设置数据库查询实现
         *
         * @param dbQuery 数据库查询实现
         * @return this
         */
        public DataSourceConfig.Builder dbQuery(IDbQuery dbQuery) {
            this.dataSourceConfig.dbQuery = dbQuery;
            return this;
        }

        /**
         * 设置类型转换器
         *
         * @param typeConvert 类型转换器
         * @return this
         */
        public DataSourceConfig.Builder typeConvert(ITypeConvert typeConvert) {
            this.dataSourceConfig.typeConvert = typeConvert;
            return this;
        }

        /**
         * 设置数据库关键字处理器
         *
         * @param keyWordsHandler 关键字处理器
         * @return this
         */
//        public DataSourceConfig.Builder keyWordsHandler(IKeyWordsHandler keyWordsHandler) {
//            this.dataSourceConfig.keyWordsHandler = keyWordsHandler;
//            return this;
//        }

        /**
         * 指定数据库查询方式
         *
         * @param databaseQueryClass 查询类
         * @return this
         */
        public DataSourceConfig.Builder databaseQueryClass(Class<? extends AbstractDatabaseQuery> databaseQueryClass) {
            this.dataSourceConfig.databaseQueryClass = databaseQueryClass;
            return this;
        }

        /**
         * 指定类型转换器
         *
         * @param typeConvertHandler 类型转换器
         * @return this
         */
//        public DataSourceConfig.Builder typeConvertHandler(ITypeConvertHandler typeConvertHandler) {
//            this.dataSourceConfig.typeConvertHandler = typeConvertHandler;
//            return this;
//        }

        /**
         * 增加数据库连接属性
         *
         * @param key   属性名
         * @param value 属性值
         * @return this
         */
        public DataSourceConfig.Builder addConnectionProperty(String key, String value) {
            this.dataSourceConfig.connectionProperties.put(key, value);
            return this;
        }

        /**
         * 构建数据库配置
         *
         * @return 数据库配置
         */
        @Override
        public DataSourceConfig build() {
            return this.dataSourceConfig;
        }
    }
}
