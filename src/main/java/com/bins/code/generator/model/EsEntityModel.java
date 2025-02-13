package com.bins.code.generator.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.convert.INameConvert;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.template.ITemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class EsEntityModel extends BaseModel implements ITemplate {

    private static final EsEntityModel model = new EsEntityModel();

    public EsEntityModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    private EsEntityModel() {
        super();
    }

    public static EsEntityModel getInstance() {
        return model;
    }

    /**
     * 自定义继承的Entity类全称，带包名
     */
    private String superClass;

    /**
     * 实体是否生成 TableField注解
     */
    private boolean tableFieldAnnotationEnable;

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
    private IdType idType;

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
    private NamingStrategy columnNaming = null;

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

    @Override
    public Map<String, Object> renderData(TableInfo tableInfo) {
        return null;
    }

    @Override
    public Set<String> getImportPackages() {
        return importPackages;
    }

    @Override
    public void setImportPackages(Set<String> importPackages) {
        if (CollectionUtils.isEmpty(importPackages)) {
            importPackages = new HashSet<>();
        }
        if (this.isLombok()) {
            importPackages.add(Data.class.getCanonicalName());
        }
        this.importPackages = importPackages;
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
            return StringUtils.camelToUnderline(field.getName());
        }).collect(Collectors.toSet()));
    }
}
