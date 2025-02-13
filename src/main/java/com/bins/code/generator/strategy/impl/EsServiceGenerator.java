package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.DtoModel;
import com.bins.code.generator.model.EsServiceModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class EsServiceGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final EsServiceGenerator generator = new EsServiceGenerator();

    private EsServiceGenerator() {
    }

    public static EsServiceGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.ES_SERVICE;

    private EsServiceModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.ES_SERVICE.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new EsServiceModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().esServiceBuilder().get(), model);
        modelTemplate = TemplateEnum.ES_SERVICE;
        model.initCommonConfig();
        model.setPackageName(tableInfo.getEsServicePackage());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public EsServiceModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getEsServiceName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getEsService();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final EsServiceGenerator generator = new EsServiceGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public EsServiceGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public EsServiceGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sEsService");
            return convertFileName((mapperName) -> String.format(newFormat, mapperName));
        }

        public EsServiceGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public EsServiceModel get() {
            return this.generator.model;
        }
    }
}
