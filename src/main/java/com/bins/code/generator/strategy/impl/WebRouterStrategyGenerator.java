package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.WebRouterModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

public class WebRouterStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final WebRouterStrategyGenerator generator = new WebRouterStrategyGenerator();

    private WebRouterStrategyGenerator() {
    }

    public static WebRouterStrategyGenerator getInstance() {
        return generator;
    }

    private WebRouterModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.WEB_ROUTER.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new WebRouterModel(configBuilder);
        BeanUtils.copyProperties(configBuilder.getStrategyConfig().webRouterBuilder().get(), model);
        model.initCommonConfig();
        model.setTableComment("请求参数验证组");
        model.setClassName("router");
        model.setCommonPackage(configBuilder.getPackageConfig().getPackageInfo().get(TemplateEnum.COMMON.getContent()));
    }

    public WebRouterModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return model.getTemplate().getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getClassName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getWebRouter();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return model.getTemplate();
    }

    public static class Builder extends BaseBuilder {

        private final WebRouterStrategyGenerator generator = new WebRouterStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public WebRouterStrategyGenerator.Builder naming(NamingStrategy namingStrategy) {
            this.generator.model.setNaming(namingStrategy);
            return this;
        }

        public WebRouterStrategyGenerator.Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.model.setColumnNaming(namingStrategy);
            return this;
        }

        public WebRouterStrategyGenerator.Builder enableLombok() {
            this.generator.model.setLombok(true);
            return this;
        }

        public WebRouterStrategyGenerator.Builder enableSwagger() {
            this.generator.model.setSwagger(true);
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public WebRouterStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public WebRouterStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sVo");
            return convertFileName((voName) -> String.format(newFormat, voName));
        }

        public WebRouterStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public WebRouterModel get() {
            return this.generator.model;
        }
    }
}
