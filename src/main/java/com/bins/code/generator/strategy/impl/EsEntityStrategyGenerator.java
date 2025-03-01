package com.bins.code.generator.strategy.impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.ControllerModel;
import com.bins.code.generator.model.EsEntityModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.ClassUtils;
import com.bins.code.generator.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class EsEntityStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static final EsEntityStrategyGenerator generator = new EsEntityStrategyGenerator();

    private EsEntityStrategyGenerator() {
    }

    public static EsEntityStrategyGenerator getInstance() {
        return generator;
    }

    private TemplateEnum modelTemplate = TemplateEnum.ES_ENTITY;

    private EsEntityModel model;

    @Override
    public String getGeneratorName() {
        return TemplateEnum.ES_ENTITY.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        model = new EsEntityModel(configBuilder);
        BeanUtils.copyProperties(tableInfo, model);
        BeanUtils.copyProperties(tableInfo.getStrategyConfig().esEntityBuilder().get(), model);
        modelTemplate = TemplateEnum.ES_ENTITY;
        model.initCommonConfig();
        model.setDate(getConfigBuilder().getGlobalConfig().getCommentDate());
        model.setPackageName(tableInfo.getEsEntityPackage());
        model.setEntityPackage(model.getEntity());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        model.setImportPackages(importPackages);
    }

    public EsEntityModel getModel() {
        return model;
    }

    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return model.getEsEntityName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getEsEntity();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public static class Builder extends BaseBuilder {

        private final EsEntityStrategyGenerator generator = new EsEntityStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
        }

        /**
         * 覆盖已有文件
         */
        public EsEntityStrategyGenerator.Builder enableFileOverride() {
            this.generator.model.setFileOverride(true);
            return this;
        }

        /**
         * 开启lombok模型
         */
        public EsEntityStrategyGenerator.Builder enableLombok() {
            this.generator.model.setLombok(true);
            return this;
        }

        /**
         * 数据库表字段映射到实体的命名策略
         */
        public EsEntityStrategyGenerator.Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.model.setColumnNaming(namingStrategy);
            return this;
        }

        /**
         * 数据库表映射到实体的命名策略
         */
        public EsEntityStrategyGenerator.Builder naming(NamingStrategy namingStrategy) {
            this.generator.model.setNaming(namingStrategy);
            return this;
        }

        /**
         * 指定生成的主键的ID类型
         *
         * @param idType ID类型
         */
        public EsEntityStrategyGenerator.Builder idType(IdType idType) {
            this.generator.model.setIdType(idType);
            return this;
        }

        /**
         * 开启链式模型
         */
        public EsEntityStrategyGenerator.Builder enableChainModel() {
            this.generator.model.setChain(true);
            return this;
        }

        /**
         * 禁用生成serialVersionUID
         */
        public EsEntityStrategyGenerator.Builder disableSerialVersionUID() {
            this.generator.model.setSerialVersionUID(false);
            return this;
        }

        /**
         *
         */
        public EsEntityStrategyGenerator.Builder enableTableFieldAnnotation() {
            this.generator.model.setTableFieldAnnotationEnable(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sEsEntity");
            return convertFileName((entityName) -> String.format(newFormat, entityName));
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         */
        public Builder convertFileName(ConverterFileName converter) {
            this.generator.model.setConverterFileName(converter);
            return this;
        }

        public EsEntityModel get() {
            String superClass = this.generator.getModel().getSuperClass();
            if (StringUtils.isNotBlank(superClass)) {
                tryLoadClass(superClass).ifPresent(this.generator.model::convertSuperEntityColumns);
            } else {
                if (!this.generator.model.getSuperEntityColumns().isEmpty()) {
                    log.warn("Forgot to set entity supper class ?" );
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
}
