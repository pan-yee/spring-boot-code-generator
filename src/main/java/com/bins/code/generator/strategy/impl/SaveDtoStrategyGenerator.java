package com.bins.code.generator.strategy.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.SaveDtoModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.ClassUtils;
import com.bins.code.generator.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class SaveDtoStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private TemplateEnum modelTemplate = TemplateEnum.SAVE_DTO;

    private SaveDtoModel model;

    private static SaveDtoStrategyGenerator generator;

    private SaveDtoStrategyGenerator() {
    }

    public static synchronized SaveDtoStrategyGenerator getInstance() {
        if (generator == null) {
            generator = new SaveDtoStrategyGenerator();
        }
        return generator;
    }

    @Override
    public String getGeneratorName() {
        return TemplateEnum.SAVE_DTO.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new SaveDtoModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().saveDtoBuilder().get(), model);
        modelTemplate = TemplateEnum.SAVE_DTO;
        model.initCommonConfig();
        model.setEntity(tableInfo.getEntityName());
        model.setTableComment(tableInfo.getComment());
        model.setTableName(tableInfo.getName());
        model.setPackageName(tableInfo.getDtoPackage());
        model.setSaveDtoName(tableInfo.getSaveDtoName());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public SaveDtoModel getModel() {
        return model;
    }

    @Override
    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getSaveDtoName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getSaveDto();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final SaveDtoStrategyGenerator generator = new SaveDtoStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        public SaveDtoStrategyGenerator.Builder enableLombok() {
            this.generator.model.setLombok(true);
            return this;
        }

        public SaveDtoStrategyGenerator.Builder enableSwagger() {
            this.generator.model.setSwagger(true);
            return this;
        }

        /**
         * 禁用生成serialVersionUID
         */
        public SaveDtoStrategyGenerator.Builder enableSerialVersionUID() {
            this.generator.model.setSerialVersionUID(true);
            return this;
        }

        /**
         * 覆盖已有文件
         */
        public SaveDtoStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 数据库表映射到实体的命名策略
         */
        public SaveDtoStrategyGenerator.Builder naming(NamingStrategy namingStrategy) {
            this.generator.model.setNaming(namingStrategy);
            return this;
        }

        /**
         * 数据库表字段映射到实体的命名策略
         */
        public SaveDtoStrategyGenerator.Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.model.setColumnNaming(namingStrategy);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public SaveDtoStrategyGenerator.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sSaveDto");
            return convertFileName((dtoName) -> String.format(newFormat, dtoName));
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         */
        public SaveDtoStrategyGenerator.Builder convertFileName(ConverterFileName converter) {
            this.generator.getModel().setConverterFileName(converter);
            return this;
        }

        public SaveDtoModel get() {
            String superClass = this.generator.getModel().getSuperClass();
            if (StringUtils.isNotBlank(superClass)) {
                tryLoadClass(superClass).ifPresent(this.generator.model::convertSuperDtoColumns);
            } else {
                if (!this.generator.model.getSuperDtoColumns().isEmpty()) {
                    log.warn("Forgot to set entity supper class ?");
                }
            }
            return this.generator.model;
        }

        private Optional<Class<?>> tryLoadClass(String className) {
            try {
                return Optional.of(ClassUtils.toClassConfident(className));
            } catch (Exception e) {
                //当父类实体存在类加载器的时候,识别父类实体字段，不存在的情况就只有通过指定superEntityColumns属性了。
            }
            return Optional.empty();
        }
    }

    private static String getNewFormat(String format) {
        String newFormat = format;
        if (ObjectUtils.isEmpty(newFormat)) {
            newFormat = "%sEntity";
        }
        return newFormat;
    }
}
