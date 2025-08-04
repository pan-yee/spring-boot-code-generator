package com.bins.code.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.bins.code.generator.config.DataSourceConfig;
import com.bins.code.generator.config.rule.DateType;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.convert.MySqlTypeConvert;
import com.bins.code.generator.query.MySqlQuery;
import com.bins.code.generator.template.engine.VelocityTemplateEngine;

public class CodeGeneratorMain {

    private static final String JDBC_URL = "jdbc:mysql://10.130.241.159:3306/supply_biz_common?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&useSSl=true";
    private static final String JDBC_USER_NAME = "root";
    private static final String JDBC_PASSWORD = "gdqOFkX3#Gqf6Siv";

    // 包名和模块名
    private static final String PACKAGE_NAME = "cn.commerce.biz.common.server";
    private static final String MODULE_NAME = "";

    private static final String[] TBL_NAMES = {"t_strategy_global"};
    // private static final String[] TBL_NAMES = {"t_purchase_req", "t_purchase_req_detail", "t_purchase_task", "t_purchase_order", "t_purchase_order_detail"};

    // 表名的前缀,从表生成代码时会去掉前缀
    private static final String TABLE_PREFIX = "t_";


    public static void main(String[] args) throws Exception {
        String projectPath = System.getProperty("user.dir");
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(JDBC_URL, JDBC_USER_NAME, JDBC_PASSWORD)
                .dbQuery(new MySqlQuery())
                .typeConvert(new MySqlTypeConvert());
        CustomGenerator customGenerator = CustomGenerator.create(dataSourceConfigBuilder);
        customGenerator.globalConfig(
                globalConfigBuilder -> globalConfigBuilder
                        .disableOpenDir()
                        .outputDir(projectPath + "/src/main/java")
                        .author("yp")
                        .commentDate("yyyy-MM-dd HH:mm:ss")
                        .dateType(DateType.ONLY_DATE)
        );

        customGenerator.packageConfig(
                packageConfigBuilder -> packageConfigBuilder
                        .parent(PACKAGE_NAME)
                        .moduleName(MODULE_NAME)
                        .entity("entity")
                        .vo("model.vo")
                        .dto("model.dto")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .esEntity("es.entity")
                        .esService("es.service")
                        .esServiceImpl("es.service.impl")
                        .pageDto("common.model.dto")
                        .controller("controller")
                        .xml("mapper.xml")
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder
                        .enableCapitalMode()
                        .addInclude(TBL_NAMES)
                        .addTablePrefix(TABLE_PREFIX)
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.entityBuilder()
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .idType(IdType.ASSIGN_ID)
                        .enableLombok()
                        .disableSerialVersionUID()
                        .enableTableFieldAnnotation()
                        .enableChainModel()
                        .enableFileOverride()
                        .formatFileName("%sDO")
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.dtoBuilder()
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .enableLombok()
                        .enableSwagger()
                        // .disableSerialVersionUID()
                        .formatFileName("%sDTO")
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.saveDtoBuilder()
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .enableLombok()
                        .enableSwagger()
                        // .disableSerialVersionUID()
                        .formatFileName("%sSaveDTO")
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.voBuilder()
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .enableLombok()
                        .enableSwagger()
                        // .disableSerialVersionUID()
                        .formatFileName("%sVO")
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.mapperBuilder()
                        .formatFileName("%sMapper"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.mapperXmlBuilder()
                        .formatFileName("%sMapper"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.serviceBuilder()
                        .formatFileName("%sService"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.serviceImplBuilder()
                        .formatFileName("%sServiceImpl"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.esEntityBuilder()
                        .naming(NamingStrategy.underline_to_camel)
                        .columnNaming(NamingStrategy.underline_to_camel)
                        .idType(IdType.ASSIGN_ID)
                        .enableLombok()
                        .disableSerialVersionUID()
                        .enableTableFieldAnnotation()
                        .enableChainModel()
                        .enableFileOverride()
                        .formatFileName("%sEsEntity")
        );

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.esServiceBuilder()
                        .formatFileName("%sEsService"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.esServiceImplBuilder()
                        .formatFileName("%sEsServiceImpl"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.controllerBuilder()
                        .enableRestStyle()
                        .formatFileName("%sController"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.webRouterBuilder()
                        .formatFileName("%s"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.webPageBuilder()
                        .formatFileName("%s"));

        customGenerator.strategyConfig(
                strategyConfigBuilder -> strategyConfigBuilder.webApiBuilder()
                        .formatFileName("%s"));

        customGenerator.templateEngine(new VelocityTemplateEngine());
        customGenerator.execute();


    }
}
