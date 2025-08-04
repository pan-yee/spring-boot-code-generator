package com.bins.code.generator.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.function.ConverterFileName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
public class SaveDtoModel extends BaseModel {

    public SaveDtoModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    private static final SaveDtoModel model = new SaveDtoModel();

    private SaveDtoModel() {
        super();
    }

    public static SaveDtoModel getInstance() {
        return model;
    }

    private String className;

    /**
     * 自定义继承的dto类全称，带包名
     */
    private String superClass;

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
     * 自定义基础的Dto类，公共字段
     */
    private final Set<String> superDtoColumns = new HashSet<>();

    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定按照 naming 执行</p>
     */
    private NamingStrategy columnNaming = null;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 数据库表映射到实体的命名策略，默认下划线转驼峰命名
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    public boolean isFileOverride() {
        return fileOverride;
    }

    public NamingStrategy getNaming() {
        return naming;
    }

    public boolean isSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isLombok() {
        return lombok;
    }

    public boolean isSwagger() {
        return swagger;
    }

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    public void convertSuperDtoColumns(Class<?> clazz) {
        List<Field> fields = TableInfoHelper.getAllFields(clazz);
        this.superDtoColumns.addAll(fields.stream().map(field -> {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null && StringUtils.isNotBlank(tableId.value())) {
                return tableId.value();
            }
            if (null == columnNaming || columnNaming == NamingStrategy.no_change) {
                return field.getName();
            }
            return StringUtils.camelToUnderline(field.getName());
        }).collect(Collectors.toSet()));
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
        }
        this.importPackages = importPackages;
    }
}
