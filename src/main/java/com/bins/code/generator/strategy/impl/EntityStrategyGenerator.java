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
}
