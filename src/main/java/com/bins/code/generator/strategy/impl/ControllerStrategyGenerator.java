package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.ControllerModel;
import com.bins.code.generator.model.DtoModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

public class ControllerStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final ControllerStrategyGenerator generator = new ControllerStrategyGenerator();

    private ControllerStrategyGenerator() {
    }

    public static ControllerStrategyGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.CONTROLLER;

    private ControllerModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.CONTROLLER.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new ControllerModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().controllerBuilder().get(), model);
        modelTemplate = TemplateEnum.CONTROLLER;
        model.initCommonConfig();
        model.setAuthor(getConfigBuilder().getGlobalConfig().getAuthor());
        model.setDate(getConfigBuilder().getGlobalConfig().getCommentDate());
        model.setEntity(tableInfo.getEntityName());
        model.setTableComment(tableInfo.getComment());
        model.setTableName(tableInfo.getName());
        model.setPackageName(tableInfo.getControllerPackage());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
        model.setRestControllerStyle(true);
    }

    public ControllerModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getControllerName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getController();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final ControllerStrategyGenerator generator = new ControllerStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public ControllerStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 开启生成@RestController控制器
         *
         */
        public Builder enableRestStyle() {
            this.generator.model.setRestControllerStyle(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public ControllerStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sController");
            return convertFileName((mapperName) -> String.format(newFormat, mapperName));
        }

        public ControllerStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public ControllerModel get() {
            return this.generator.model;
        }
    }
}
