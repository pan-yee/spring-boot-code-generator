package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.EsServiceImplModel;
import com.bins.code.generator.model.EsServiceModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

public class EsServiceImplGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final EsServiceImplGenerator generator = new EsServiceImplGenerator();

    private EsServiceImplGenerator() {
    }

    public static EsServiceImplGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.ES_SERVICE_IMPL;

    private EsServiceImplModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.ES_SERVICE_IMPL.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new EsServiceImplModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().esServiceImplBuilder().get(), model);
        modelTemplate = TemplateEnum.ES_SERVICE_IMPL;
        model.initCommonConfig();
        model.setTableComment(tableInfo.getComment());
        model.setPackageName(tableInfo.getEsServiceImplPackage());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public EsServiceImplModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getEsServiceImplName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getEsServiceImpl();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final EsServiceImplGenerator generator = new EsServiceImplGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public EsServiceImplGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public EsServiceImplGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sEsServiceImpl");
            return convertFileName((serviceImplName) -> String.format(newFormat, serviceImplName));
        }

        public EsServiceImplGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public EsServiceImplModel get() {
            return this.generator.model;
        }
    }
}
