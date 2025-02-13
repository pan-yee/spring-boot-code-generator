package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.MapperXmlModel;
import com.bins.code.generator.model.ServiceImplModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

public class ServiceImplStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final ServiceImplStrategyGenerator generator = new ServiceImplStrategyGenerator();

    private ServiceImplStrategyGenerator() {
    }

    public static ServiceImplStrategyGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.SERVICE_IMPL;

    private ServiceImplModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.SERVICE_IMPL.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new ServiceImplModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().serviceImplBuilder().get(), model);
        modelTemplate = TemplateEnum.SERVICE_IMPL;
        model.initCommonConfig();
        model.setAuthor(getConfigBuilder().getGlobalConfig().getAuthor());
        model.setDate(getConfigBuilder().getGlobalConfig().getCommentDate());
        model.setEntity(tableInfo.getEntityName());
        model.setTableComment(tableInfo.getComment());
        model.setPackageName(tableInfo.getServiceImplPackage());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public ServiceImplModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getServiceImplName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getServiceImpl();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final ServiceImplStrategyGenerator serviceImpl = new ServiceImplStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public ServiceImplStrategyGenerator.Builder enableFileOverride() {
            this.serviceImpl.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public ServiceImplStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sEsServiceImpl");
            return convertFileName((serviceImplName) -> String.format(newFormat, serviceImplName));
        }

        public ServiceImplStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.serviceImpl.model.setConverterFileName(converter);
            return this;
        }

        public ServiceImplModel get() {
            return this.serviceImpl.model;
        }
    }
}
