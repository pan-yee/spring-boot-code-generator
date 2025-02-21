package com.bins.code.generator.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.util.ClassUtils;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.convert.INameConvert;
import com.bins.code.generator.function.ConverterFileName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
public class EntityModel extends BaseModel {

    private EntityModel() {
        super();
    }

    public EntityModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superClass;

    /**
     * 实体是否生成 TableField注解
     */
    private boolean tableFieldAnnotationEnable = true;

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride = true;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean serialVersionUID = true;

    /**
     * 是否开启lombok模型
     */
    private boolean lombok = true;

    /**
     * 【实体】是否为链式模型（默认 false）
     **/
    private boolean chain = true;

    /**
     * 自定义基础的Entity类，公共字段
     */
    private Set<String> superEntityColumns = new HashSet<>();

    /**
     * 自定义忽略字段
     * <a href="https://github.com/baomidou/generator/issues/46">...</a>
     */
    private final Set<String> ignoreColumns = new HashSet<>();

    /**
     * 包导入信息
     */
    private Set<String> importPackages = new TreeSet<>();

    public String getIdTypeName() {
        if (ObjectUtils.isEmpty(this.getIdType())) {
            return "AUTO";
        }
        if (this.getIdType().getKey() == 3) {
            return "ASSIGN_ID";
        } else {
            return "AUTO";
        }
    }

    /**
     * 指定生成的主键的ID类型
     */
    private IdType idType = IdType.ASSIGN_ID;

    /**
     * 指定生成的主键的ID类型
     */
    private String idTypeName;

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

    public boolean isFileOverride() {
        return fileOverride;
    }

    public boolean isLombok() {
        return lombok;
    }

    public boolean isSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isTableFieldAnnotationEnable() {
        return this.tableFieldAnnotationEnable;
    }

    public boolean isChain() {
        return chain;
    }

    public IdType getIdType() {
        return idType;
    }

    /**
     * 未指定以 naming 策略为准
     *
     * @return
     */
    public NamingStrategy getColumnNaming() {
        return Optional.ofNullable(columnNaming).orElse(naming);
    }

    public Set<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(Set<String> importPackages) {
        if (CollectionUtils.isEmpty(importPackages)) {
            importPackages = new HashSet<>();
        }
        if (this.isTableFieldAnnotationEnable()) {
            importPackages.add(com.baomidou.mybatisplus.annotation.TableField.class.getCanonicalName());
            importPackages.add(com.baomidou.mybatisplus.annotation.TableName.class.getCanonicalName());
        }
        if (this.isHavePrimaryKey()) {
            importPackages.add(com.baomidou.mybatisplus.annotation.IdType.class.getCanonicalName());
            importPackages.add(com.baomidou.mybatisplus.annotation.TableId.class.getCanonicalName());
        }
        if (this.isLombok()) {
            importPackages.add(lombok.Data.class.getCanonicalName());
            importPackages.add(lombok.EqualsAndHashCode.class.getCanonicalName());
        }
        this.importPackages = importPackages;
    }

    public void setIgnoreColumns(String... propertyNames) {
        ignoreColumns.addAll(Arrays.asList(propertyNames));
    }

    private void setSuperEntityColumns() {
        String superClass = this.superClass;
        if (StringUtils.isNotBlank(superClass)) {
            tryLoadClass(superClass).ifPresent(this::convertSuperEntityColumns);
        } else {
            if (!this.superEntityColumns.isEmpty()) {
                log.warn("Forgot to set entity supper class ?");
            }
        }
    }

    private Optional<Class<?>> tryLoadClass(String className) {
        try {
            return Optional.of(ClassUtils.toClassConfident(className));
        } catch (Exception e) {
            //当父类实体存在类加载器的时候,识别父类实体字段，不存在的情况就只有通过指定superEntityColumns属性了。
        }
        return Optional.empty();
    }

    /**
     * <p>
     * 父类 Class 反射属性转换为公共字段
     * </p>
     *
     * @param clazz 实体父类 Class
     */
    public void convertSuperEntityColumns(Class<?> clazz) {
        List<Field> fields = TableInfoHelper.getAllFields(clazz);
        this.superEntityColumns.addAll(fields.stream().map(field -> {
            TableId tableId = field.getAnnotation(TableId.class);
            if (tableId != null && StringUtils.isNotBlank(tableId.value())) {
                return tableId.value();
            }
            if (null == columnNaming || columnNaming == NamingStrategy.no_change) {
                return field.getName();
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField != null && StringUtils.isNotBlank(tableField.value())) {
                return tableField.value();
            }
            return StringUtils.camelToUnderline(field.getName());
        }).collect(Collectors.toSet()));
    }
    /**
     * 匹配忽略字段(忽略大小写)
     *
     * @param fieldName 字段名
     * @return 是否匹配
     * @since 3.5.0
     */
    public boolean matchIgnoreColumns(String fieldName) {
        return ignoreColumns.stream().anyMatch(e -> e.equalsIgnoreCase(fieldName));
    }
}
