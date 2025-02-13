package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.WebApiModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

public class WebApiStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final WebApiStrategyGenerator generator = new WebApiStrategyGenerator();

    private WebApiStrategyGenerator() {
    }

    public static WebApiStrategyGenerator getInstance() {
        return generator;
    }

    private WebApiModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.WEB_API.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new WebApiModel(configBuilder);
        BeanUtils.copyProperties(configBuilder.getStrategyConfig().webApiBuilder().get(), model);
        model.initCommonConfig();
        model.setTableComment("web api");
        model.setClassName("api");
        model.setCommonPackage(configBuilder.getPackageConfig().getPackageInfo().get(TemplateEnum.COMMON.getContent()));
    }

    public WebApiModel getModel() {
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
        return getConfigBuilder().getTemplateConfig().getWebApi();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return model.getTemplate();
    }

    public static class Builder extends BaseBuilder {

        private final WebApiStrategyGenerator generator = new WebApiStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public WebApiStrategyGenerator.Builder naming(NamingStrategy namingStrategy) {
            this.generator.model.setNaming(namingStrategy);
            return this;
        }

        public WebApiStrategyGenerator.Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.model.setColumnNaming(namingStrategy);
            return this;
        }

        public WebApiStrategyGenerator.Builder enableLombok() {
            this.generator.model.setLombok(true);
            return this;
        }

        public WebApiStrategyGenerator.Builder enableSwagger() {
            this.generator.model.setSwagger(true);
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public WebApiStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public WebApiStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sVo");
            return convertFileName((voName) -> String.format(newFormat, voName));
        }

        public WebApiStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public WebApiModel get() {
            return this.generator.model;
        }
    }
}
