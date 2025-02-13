package com.bins.code.generator.model;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.po.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
public class WebPageModel extends BaseModel {

    public WebPageModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    /**
     * 文件模板
     */
    private TemplateEnum template = TemplateEnum.WEB_PAGE;

    /**
     * 表字段
     */
    private List<TableField> fields = new ArrayList<>();

    private String packageName;

    private String className;

    private String voName;

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride;

    /**
     * 【实体】是否为lombok模型
     */
    private boolean lombok;

    /**
     * 【实体】是否开启swagger
     */
    private boolean swagger;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean serialVersionUID = true;

    /**
     * 包导入信息
     */
    private Set<String> importPackages = new TreeSet<>();

    /**
     * 数据库表映射到实体的命名策略，默认下划线转驼峰命名
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定按照 naming 执行</p>
     */
    private NamingStrategy columnNaming = null;

    public boolean isFileOverride() {
        return fileOverride;
    }

    public NamingStrategy getNaming() {
        return naming;
    }

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    public boolean isSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(Set<String> importPackages) {
        if (CollectionUtils.isEmpty(importPackages)) {
            importPackages = new HashSet<>();
        }
        if (this.isLombok()) {
            importPackages.add(Data.class.getCanonicalName());
        }
        if (this.isSerialVersionUID()) {
            importPackages.add(java.io.Serializable.class.getCanonicalName());
        }
        this.importPackages = importPackages;
    }
}
