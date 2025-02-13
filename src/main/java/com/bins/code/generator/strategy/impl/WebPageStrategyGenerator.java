package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.WebPageModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;


public class WebPageStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final WebPageStrategyGenerator generator = new WebPageStrategyGenerator();

    private WebPageStrategyGenerator() {
    }

    public static WebPageStrategyGenerator getInstance() {
        return generator;
    }

    private WebPageModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.WEB_PAGE.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new WebPageModel(configBuilder);
        BeanUtils.copyProperties(configBuilder.getStrategyConfig().webPageBuilder().get(), model);
        BeanUtils.copyProperties(tableInfo, model);
        model.initCommonConfig();
        model.setTableComment("备注");
        model.setClassName("WebPage");
        model.setCommonPackage(configBuilder.getPackageConfig().getPackageInfo().get(TemplateEnum.COMMON.getContent()));

    }

    @Override
    public WebPageModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return model.getTemplate().getCode();
    }

    @Override
    public String getModelFileName() {
        if (ObjectUtils.isNotEmpty(model.getCapitalName())) {
            return model.getCapitalName() + "Page";
        }
        return TemplateEnum.WEB_PAGE.getContent();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getWebPage();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return model.getTemplate();
    }

    public static class Builder extends BaseBuilder {

        private final WebPageStrategyGenerator generator = new WebPageStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public WebPageStrategyGenerator.Builder naming(NamingStrategy namingStrategy) {
            this.generator.model.setNaming(namingStrategy);
            return this;
        }

        public WebPageStrategyGenerator.Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.model.setColumnNaming(namingStrategy);
            return this;
        }

        public WebPageStrategyGenerator.Builder enableLombok() {
            this.generator.model.setLombok(true);
            return this;
        }

        public WebPageStrategyGenerator.Builder enableSwagger() {
            this.generator.model.setSwagger(true);
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public WebPageStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public WebPageStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sVo");
            return convertFileName((voName) -> String.format(newFormat, voName));
        }

        public WebPageStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public WebPageModel get() {
            return this.generator.model;
        }
    }
}
