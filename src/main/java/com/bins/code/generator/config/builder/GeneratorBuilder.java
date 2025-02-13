package com.bins.code.generator.config.builder;

import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.PackageConfig;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.TemplateConfig;

public class GeneratorBuilder {

    /**
     * 全局配置
     *
     * @return GlobalConfig
     */
    public static GlobalConfig globalConfig() {
        return new GlobalConfig.Builder().build();
    }

    /**
     * 全局配置 Builder
     *
     * @return GlobalConfig.Builder
     */
    public static GlobalConfig.Builder globalConfigBuilder() {
        return new GlobalConfig.Builder();
    }

    /**
     * 包相关的配置项
     *
     * @return PackageConfig
     */
    public static PackageConfig packageConfig() {
        return new PackageConfig.Builder().build();
    }

    /**
     * 包相关的配置项 Builder
     *
     * @return PackageConfig.Builder
     */
    public static PackageConfig.Builder packageConfigBuilder() {
        return new PackageConfig.Builder();
    }

    /**
     * 策略配置项
     *
     * @return StrategyConfig
     */
    public static StrategyConfig strategyConfig() {
        return new StrategyConfig.Builder().build();
    }

    /**
     * 策略配置项 Builder
     *
     * @return StrategyConfig.Builder
     */
    public static StrategyConfig.Builder strategyConfigBuilder() {
        return new StrategyConfig.Builder();
    }

    /**
     * 模板路径配置项
     *
     * @return TemplateConfig
     */
    public static TemplateConfig templateConfig() {
        return new TemplateConfig.Builder().build();
    }

    /**
     * 模板路径配置项 Builder
     *
     * @return TemplateConfig.Builder
     */
    public static TemplateConfig.Builder templateConfigBuilder() {
        return new TemplateConfig.Builder();
    }

}
