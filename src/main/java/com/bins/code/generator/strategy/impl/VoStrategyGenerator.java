package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.ServiceModel;
import com.bins.code.generator.model.VoModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.GeneratorUtils;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.TreeSet;

public class VoStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private TemplateEnum modelTemplate;

    private VoModel model;

    private static VoStrategyGenerator generator = new VoStrategyGenerator();

    private VoStrategyGenerator() {
    }

    public static synchronized VoStrategyGenerator getInstance() {
        if (generator == null) {
            generator = new VoStrategyGenerator();
        }
        return generator;
    }

    @Override
    public String getGeneratorName() {
        return TemplateEnum.VO.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new VoModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().voBuilder().get(), model);
        modelTemplate = TemplateEnum.VO;
        model.initCommonConfig();
        model.setEntity(tableInfo.getEntityName());
        model.setTableComment(tableInfo.getComment());
        model.setPackageName(tableInfo.getVoPackage());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public VoModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getVoName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getVo();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final VoStrategyGenerator generator = new VoStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public VoStrategyGenerator.Builder naming(NamingStrategy namingStrategy) {
            this.generator.model.setNaming(namingStrategy);
            return this;
        }

        public VoStrategyGenerator.Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.model.setColumnNaming(namingStrategy);
            return this;
        }

        public VoStrategyGenerator.Builder enableLombok() {
            this.generator.model.setLombok(true);
            return this;
        }

        public VoStrategyGenerator.Builder enableSwagger() {
            this.generator.model.setSwagger(true);
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public VoStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public VoStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sVo");
            return convertFileName((voName) -> String.format(newFormat, voName));
        }

        public VoStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public VoModel get() {
            return this.generator.model;
        }
    }
}
