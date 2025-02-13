package com.bins.code.generator.model;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.po.TableField;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.template.ITemplate;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Data
public class MapperXmlModel extends BaseModel implements ITemplate {

    private static final MapperXmlModel model = new MapperXmlModel();

    private MapperXmlModel() {
        super();
    }

    public MapperXmlModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    public static MapperXmlModel getInstance() {
        return model;
    }

    /**
     * 表字段
     */
    private List<TableField> fields = new ArrayList<>();

    private String packageName;

    private String className;

    private String xmlName;

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
            importPackages.add(lombok.Data.class.getCanonicalName());
        }
        if (this.isSerialVersionUID()) {
            importPackages.add(java.io.Serializable.class.getCanonicalName());
        }
        this.importPackages = importPackages;
    }

    @Override
    public Map<String, Object> renderData(TableInfo tableInfo) {
        return null;
    }

}
