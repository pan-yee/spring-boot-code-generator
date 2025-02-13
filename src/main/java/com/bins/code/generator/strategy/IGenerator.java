package com.bins.code.generator.strategy;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.model.BaseModel;
import com.bins.code.generator.model.po.TableInfo;

public interface IGenerator {

    String getGeneratorName();

    void setConfigBuilder(ConfigBuilder configBuilder);

    void initModel(TableInfo tableInfo);

    String templateFilePath(String filePath);

    BaseModel getModel();

    String getTemplateCode();

    String getModelFileName();

    String getModelTemplatePath();

    TemplateEnum getModelTemplate();

}
