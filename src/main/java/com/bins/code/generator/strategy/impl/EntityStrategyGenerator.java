package com.bins.code.generator.strategy.impl;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.convert.INameConvert;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.EntityModel;
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
public class EntityStrategyGenerator extends AbstractStrategyGenerator implements IGenerator {

    private static EntityStrategyGenerator generator;

    private static EntityModel entityModel;

    private static TemplateEnum modelTemplate;

    private EntityStrategyGenerator() {
    }

    public static synchronized EntityStrategyGenerator getInstance() {
        if (generator == null) {
            generator = new EntityStrategyGenerator();
        }
        return generator;
    }

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    @Override
    public String getGeneratorName() {
        return TemplateEnum.ENTITY.getCode();
    }

    @Override
    public void initModel(TableInfo tableInfo) {
        ConfigBuilder configBuilder = super.getConfigBuilder();
        entityModel = tableInfo.getEntityModel();
        BeanUtils.copyProperties(tableInfo, entityModel);
        modelTemplate = TemplateEnum.ENTITY;
        entityModel.initCommonConfig();
        entityModel.setAuthor(getConfigBuilder().getGlobalConfig().getAuthor());
        entityModel.setDate(getConfigBuilder().getGlobalConfig().getCommentDate());
        entityModel.setEntity(tableInfo.getEntityName());
        entityModel.setTableComment(tableInfo.getComment());
        entityModel.setTableName(tableInfo.getName());
        entityModel.setPackageName(tableInfo.getEntityPackage());
        entityModel.setEntityPackage(entityModel.getEntity());
        Set<String> importPackages = new TreeSet<>();
        importPackages.addAll(tableInfo.getFieldImportPackages());
        importPackages.addAll(tableInfo.getImportPackages());
        entityModel.setImportPackages(importPackages);
    }

    public EntityModel getModel() {
        return entityModel;
    }

    public String getTemplateCode() {
        return modelTemplate.getCode();
    }

    @Override
    public String getModelFileName() {
        return entityModel.getEntityName();
    }

    @Override
    public String getModelTemplatePath() {
        return getConfigBuilder().getTemplateConfig().getEntity();
    }

    @Override
    public TemplateEnum getModelTemplate() {
        return modelTemplate;
    }

    public ConverterFileName getConverterFileName() {
        return converterFileName;
    }

    public void setConverterFileName(ConverterFileName converterFileName) {
        this.converterFileName = converterFileName;
    }

    public static class Builder extends BaseBuilder {

        private final EntityStrategyGenerator generator = new EntityStrategyGenerator();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
            this.generator.entityModel.setNameConvert(new INameConvert.DefaultNameConvert(strategyConfig));
        }

        /**
         * 覆盖已有文件
         */
        public EntityStrategyGenerator.Builder enableFileOverride() {
            this.generator.entityModel.setFileOverride(true);
            return this;
        }

        /**
         * 开启lombok模型
         */
        public Builder enableLombok() {
            this.generator.entityModel.setLombok(true);
            return this;
        }

        /**
         * 数据库表字段映射到实体的命名策略
         */
        public Builder columnNaming(NamingStrategy namingStrategy) {
            this.generator.entityModel.setColumnNaming(namingStrategy);
            return this;
        }

        /**
         * 数据库表映射到实体的命名策略
         */
        public Builder naming(NamingStrategy namingStrategy) {
            this.generator.entityModel.setNaming(namingStrategy);
            return this;
        }

        /**
         * 指定生成的主键的ID类型
         *
         * @param idType ID类型
         */
        public Builder idType(IdType idType) {
            this.generator.entityModel.setIdType(idType);
            return this;
        }

        /**
         * 开启链式模型
         */
        public Builder enableChainModel() {
            this.generator.entityModel.setChain(true);
            return this;
        }

        /**
         * 禁用生成serialVersionUID
         */
        public Builder disableSerialVersionUID() {
            this.generator.entityModel.setSerialVersionUID(false);
            return this;
        }

        /**
         *
         */
        public Builder enableTableFieldAnnotation() {
            this.generator.entityModel.setTableFieldAnnotationEnable(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sEntity");
            return convertFileName((entityName) -> String.format(newFormat, entityName));
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         */
        public Builder convertFileName(ConverterFileName converter) {
            this.generator.entityModel.setConverterFileName(converter);
            return this;
        }

        public EntityModel get() {
            EntityModel model = this.generator.entityModel;
            String superClass = this.generator.entityModel.getSuperClass();
            if (StringUtils.isNotBlank(superClass)) {
                tryLoadClass(superClass).ifPresent(model::convertSuperEntityColumns);
            } else {
                if (!this.generator.getModel().getSuperEntityColumns().isEmpty()) {
                    log.warn("Forgot to set entity supper class ?" );
                }
            }
            return model;
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
