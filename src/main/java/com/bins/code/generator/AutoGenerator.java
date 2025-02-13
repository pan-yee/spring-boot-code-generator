package com.bins.code.generator;

import com.bins.code.generator.config.*;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.template.engine.AbstractTemplateEngine;
import com.bins.code.generator.template.engine.VelocityTemplateEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoGenerator {

    /**
     * 配置信息
     */
    protected ConfigBuilder config;

    /**
     * 数据源配置
     */
    private DataSourceConfig dataSource;

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

    private AutoGenerator() {
        // 不推荐使用
    }

    /**
     * 构造方法
     *
     * @param dataSourceConfig 数据库配置
     */
    public AutoGenerator(DataSourceConfig dataSourceConfig) {
        this.dataSource = dataSourceConfig;
    }

    public AutoGenerator global(GlobalConfig globalConfig) {
        this.globalConfig = globalConfig;
        return this;
    }

    /**
     * 指定包配置信息
     *
     * @param packageConfig 包配置
     * @return this
     */
    public AutoGenerator packageInfo(PackageConfig packageConfig) {
        this.packageInfo = packageConfig;
        return this;
    }

    /**
     * 指定模板配置
     *
     * @param templateConfig 模板配置
     * @return this
     */
    public AutoGenerator template(TemplateConfig templateConfig) {
        this.template = templateConfig;
        return this;
    }

    /**
     * 生成策略
     *
     * @param strategyConfig 策略配置
     * @return this
     */
    public AutoGenerator strategy(StrategyConfig strategyConfig) {
        this.strategy = strategyConfig;
        return this;
    }

    /**
     * 生成代码
     *
     * @param templateEngine 模板引擎
     */
    public void execute(AbstractTemplateEngine templateEngine) {
        log.debug("==========================准备生成文件...==========================" );
        if (null == config) {
            config = new ConfigBuilder(packageInfo, dataSource, strategy, template, globalConfig);
        }
        if (null == templateEngine) {
            templateEngine = new VelocityTemplateEngine();
        }
        templateEngine.setConfigBuilder(config);
        templateEngine.init(config).batchOutput().open();
        log.debug("==========================文件生成完成！！！==========================" );
    }

}
