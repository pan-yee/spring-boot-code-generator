package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.ServiceImplModel;
import com.bins.code.generator.model.ServiceModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class ServiceStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final ServiceStrategyGenerator generator = new ServiceStrategyGenerator();

    private ServiceStrategyGenerator() {
    }

    public static ServiceStrategyGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.SERVICE;

    private ServiceModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.SERVICE.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new ServiceModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().esServiceBuilder().get(), model);
        modelTemplate = TemplateEnum.SERVICE;
        model.initCommonConfig();
        model.setAuthor(getConfigBuilder().getGlobalConfig().getAuthor());
        model.setDate(getConfigBuilder().getGlobalConfig().getCommentDate());
        model.setEntity(tableInfo.getEntityName());
        model.setTableComment(tableInfo.getComment());
        model.setTableName(tableInfo.getTableName());
        model.setPackageName(tableInfo.getServicePackage());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public ServiceModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getServiceName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getService();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final ServiceStrategyGenerator generator = new ServiceStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public ServiceStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public ServiceStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sService");
            return convertFileName((mapperName) -> String.format(newFormat, mapperName));
        }

        public ServiceStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public ServiceModel get() {
            return this.generator.model;
        }
    }
}
