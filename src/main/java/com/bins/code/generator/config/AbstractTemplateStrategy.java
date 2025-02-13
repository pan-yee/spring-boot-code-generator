package com.bins.code.generator.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.convert.INameConvert;
import com.bins.code.generator.function.ConverterFileName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractTemplateStrategy implements ITemplateStrategy {

    /**
     * 是否禁用
     */
    private boolean disable = true;

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride = true;

    /**
     * 是否开启lombok
     */
    private boolean lombok = true;

    /**
     * 是否开启swagger
     */
    private boolean swagger = true;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean serialVersionUID = true;

    /**
     * 【实体】是否为链式模型（默认 false）
     **/
    private boolean chain = false;

    /**
     * 实体是否生成 TableField注解
     */
    private boolean tableFieldAnnotationEnable = true;

    /**
     * 模板路径
     */
    private String templatePath;

    /**
     * 名称转换
     */
    private INameConvert nameConvert;

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    /**
     * 数据库表映射到实体的命名策略，默认下划线转驼峰命名
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定按照 naming 执行</p>
     */
    private NamingStrategy columnNaming = NamingStrategy.underline_to_camel;

    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType = IdType.ASSIGN_ID;

    public INameConvert getNameConvert() {
        return nameConvert;
    }

    public void setNameConvert(INameConvert nameConvert) {
        this.nameConvert = nameConvert;
    }

    public ConverterFileName getConverterFileName() {
        return converterFileName;
    }

    public void setConverterFileName(ConverterFileName converterFileName) {
        this.converterFileName = converterFileName;
    }

    public NamingStrategy getNaming() {
        return naming;
    }

    public void setNaming(NamingStrategy naming) {
        this.naming = naming;
    }

    public boolean isFileOverride() {
        return fileOverride;
    }

    public void setFileOverride(boolean fileOverride) {
        this.fileOverride = fileOverride;
    }

    public boolean isLombok() {
        return lombok;
    }

    public void setLombok(boolean lombok) {
        this.lombok = lombok;
    }

    public boolean isSwagger() {
        return swagger;
    }

    public void setSwagger(boolean swagger) {
        this.swagger = swagger;
    }

    public boolean isSerialVersionUID() {
        return serialVersionUID;
    }

    public void setSerialVersionUID(boolean serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public boolean isChain() {
        return chain;
    }

    public void setChain(boolean chain) {
        this.chain = chain;
    }

    public boolean isTableFieldAnnotationEnable() {
        return tableFieldAnnotationEnable;
    }

    public void setTableFieldAnnotationEnable(boolean tableFieldAnnotationEnable) {
        this.tableFieldAnnotationEnable = tableFieldAnnotationEnable;
    }

    public NamingStrategy getColumnNaming() {
        return columnNaming;
    }

    public void setColumnNaming(NamingStrategy columnNaming) {
        this.columnNaming = columnNaming;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }
}
