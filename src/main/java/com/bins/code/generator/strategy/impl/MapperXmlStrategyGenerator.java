package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.MapperModel;
import com.bins.code.generator.model.MapperXmlModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

public class MapperXmlStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final MapperXmlStrategyGenerator generator = new MapperXmlStrategyGenerator();

    private MapperXmlStrategyGenerator() {
    }

    public static MapperXmlStrategyGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.MAPPER_XML;

    private MapperXmlModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.MAPPER_XML.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new MapperXmlModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().mapperXmlBuilder().get(), model);
        modelTemplate = TemplateEnum.MAPPER_XML;
        model.setAuthor(getConfigBuilder().getGlobalConfig().getAuthor());
        model.setDate(getConfigBuilder().getGlobalConfig().getCommentDate());
        model.setEntity(tableInfo.getEntityName());
        model.setTableComment(tableInfo.getComment());
        model.setTableName(tableInfo.getName());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public MapperXmlModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getXmlName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getXml();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final MapperXmlStrategyGenerator generator = new MapperXmlStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public MapperXmlStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public MapperXmlStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sMapper");
            return convertFileName((mapperXmlName) -> String.format(newFormat, mapperXmlName));
        }

        public MapperXmlStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public MapperXmlModel get() {
            return this.generator.model;
        }
    }
}
