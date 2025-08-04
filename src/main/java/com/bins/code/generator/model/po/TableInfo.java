package com.bins.code.generator.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.PackageConfig;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.IColumnType;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.model.EntityModel;
import com.bins.code.generator.utils.GeneratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class TableInfo {

    public TableInfo() {
    }

    /**
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /**
     * 包配置
     */
    private PackageConfig packageConfig;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 包导入信息
     */
    private final Set<String> importPackages = new TreeSet<>();

    /**
     * 包导入信息
     */
    private final Set<String> fieldImportPackages = new TreeSet<>();

    /**
     * model的包导入信息
     */
    private final Set<String> modelImportPackages = new TreeSet<>();

    /**
     * 是否转换
     */
    private boolean convert;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 原始名称
     */
    private String originName;

    /**
     * 首字母大写名称
     */
    private String capitalName;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * dto名称
     */
    private String dtoName;

    /**
     * saveDto名称
     */
    private String saveDtoName;

    /**
     * vo名称
     */
    private String voName;

    /**
     * mapper名称
     */
    private String mapperName;

    /**
     * xml名称
     */
    private String xmlName;

    /**
     * service名称
     */
    private String serviceName;

    /**
     * serviceImpl名称
     */
    private String serviceImplName;

    /**
     * controller名称
     */
    private String controllerName;

    /**
     * 实体名称
     */
    private String esEntityName;

    /**
     * service名称
     */
    private String esServiceName;

    /**
     * serviceImpl名称
     */
    private String esServiceImplName;

    /**
     * 模块公用包名
     */
    private String modulePackage;

    /**
     * 基础公用包名
     */
    private String commonPackage;

    /**
     * 实体包名
     */
    private String entityPackage;

    /**
     * dto包名
     */
    private String dtoPackage;

    /**
     * vo包名
     */
    private String voPackage;

    /**
     * mapper包名
     */
    private String mapperPackage;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * serviceImpl包名
     */
    private String serviceImplPackage;

    /**
     * controller包名
     */
    private String controllerPackage;

    /**
     * 实体包名
     */
    private String esEntityPackage;

    /**
     * service包名
     */
    private String esServicePackage;

    /**
     * serviceImpl包名
     */
    private String esServiceImplPackage;

    /**
     * 表字段
     */
    private final List<TableField> fields = new ArrayList<>();

    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;

    /**
     * 公共字段
     */
    private final List<TableField> commonFields = new ArrayList<>();

    /**
     * 字段名称集
     */
    private String fieldNames;

    /**
     * 实体
     */
    private EntityModel entityModel;

    /**
     * 构造方法
     *
     * @param configBuilder 配置构建
     * @param tableName     表名
     */
    public TableInfo(ConfigBuilder configBuilder, String tableName) {
        this.strategyConfig = configBuilder.getStrategyConfig();
        this.globalConfig = configBuilder.getGlobalConfig();
        this.packageConfig = configBuilder.getPackageConfig();
        EntityModel entityModel = new EntityModel(configBuilder);
        BeanUtils.copyProperties(this.strategyConfig.entityBuilder().get(), entityModel);
        this.entityModel = entityModel;
        this.tableName = tableName;
        entityModel.setIgnoreColumns("create_user","update_user","create_time","update_time");
    }

    /**
     *
     */
    protected TableInfo setConvert() {
        return this;
    }

    public String getEntityPath() {
        return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
    }

    /**
     * @param entityName 实体名称
     * @return this
     */
    public TableInfo setEntityName(String entityName) {
        this.entityName = entityName;
        setConvert();
        return this;
    }

    /**
     * 添加字段
     *
     * @param field 字段
     */
    public void addField(TableField field) {
        if (entityModel.matchIgnoreColumns(field.getColumnName())) {
            // 忽略字段不在处理
            return;
        }
        this.fields.add(field);
    }

    /**
     * @param pkgs 包空间
     * @return this
     */
    public TableInfo addImportPackages(String... pkgs) {
        return addImportPackages(Arrays.asList(pkgs));
    }

    public TableInfo addImportPackages(List<String> pkgList) {
        importPackages.addAll(pkgList);
        return this;
    }

    /**
     * 转换filed实体为 xml mapper 中的 base column 字符串信息
     */
    public String getFieldNames() {
        if (StringUtils.isBlank(fieldNames)) {
            this.fieldNames = this.fields.stream().map(TableField::getColumnName).collect(Collectors.joining(", "));
        }
        return this.fieldNames;
    }

    /**
     * 导包处理
     */
    public void importPackage() {
        String superEntity = entityModel.getSuperClass();
        if (StringUtils.isNotBlank(superEntity)) {
            this.importPackages.add(superEntity);
        }
        this.fields.forEach(field -> {
            IColumnType columnType = field.getColumnType();
            if (null != columnType && null != columnType.getPkg()) {
                importPackages.add(columnType.getPkg());
                fieldImportPackages.add(columnType.getPkg());
            }
            if (field.isKeyFlag()) {
                if (field.isConvert() || field.isKeyIdentityFlag()) {
                    importPackages.add(TableId.class.getCanonicalName());
                }
                if (field.isKeyIdentityFlag()) {
                    importPackages.add(IdType.class.getCanonicalName());
                }
            }
        });
    }

    /**
     * 导包处理
     */
    public void modelImportPackage() {
        this.modelImportPackages.add(Serializable.class.getCanonicalName());
        this.fields.forEach(field -> {
            IColumnType columnType = field.getColumnType();
            if (null != columnType && null != columnType.getPkg()) {
                modelImportPackages.add(columnType.getPkg());
            }
        });

    }

    /**
     * 处理表信息(文件名与导包)
     */
    public void processTable() {
        String entityName = entityModel.getNameConvert().entityNameConvert(this);
        this.setEntityName(entityModel.getConverterFileName().convert(entityName));
        this.originName = entityModel.getNameConvert().originNameConvert(this);
        this.capitalName = GeneratorUtils.capitalize(this.originName);
        this.dtoName = strategyConfig.dtoBuilder().get().getConverterFileName().convert(entityName);
        /**
         * saveDto名称
         */
        this.saveDtoName = strategyConfig.saveDtoBuilder().get().getConverterFileName().convert(entityName);
        this.voName = strategyConfig.voBuilder().get().getConverterFileName().convert(entityName);
        this.mapperName = strategyConfig.mapperBuilder().get().getConverterFileName().convert(entityName);
        this.xmlName = strategyConfig.mapperXmlBuilder().get().getConverterFileName().convert(entityName);
        this.serviceName = strategyConfig.serviceBuilder().get().getConverterFileName().convert(entityName);
        this.serviceImplName = strategyConfig.serviceImplBuilder().get().getConverterFileName().convert(entityName);
        this.esEntityName = strategyConfig.esEntityBuilder.get().getConverterFileName().convert(entityName);
        this.esServiceName = strategyConfig.esServiceBuilder().get().getConverterFileName().convert(entityName);
        this.esServiceImplName = strategyConfig.esServiceImplBuilder().get().getConverterFileName().convert(entityName);
        this.controllerName = strategyConfig.controllerBuilder().get().getConverterFileName().convert(entityName);
        this.commonPackage = packageConfig.getPackageInfo().get(TemplateEnum.COMMON.getContent());
        this.modulePackage = packageConfig.getPackageInfo().get(TemplateEnum.PARENT.getContent());
        this.entityPackage = packageConfig.getPackageInfo().get(TemplateEnum.ENTITY.getContent());
        this.dtoPackage = packageConfig.getPackageInfo().get(TemplateEnum.DTO.getContent());
        this.voPackage = packageConfig.getPackageInfo().get(TemplateEnum.VO.getContent());
        this.mapperPackage = packageConfig.getPackageInfo().get(TemplateEnum.MAPPER.getContent());
        this.servicePackage = packageConfig.getPackageInfo().get(TemplateEnum.SERVICE.getContent());
        this.serviceImplPackage = packageConfig.getPackageInfo().get(TemplateEnum.SERVICE_IMPL.getContent());
        this.controllerPackage = packageConfig.getPackageInfo().get(TemplateEnum.CONTROLLER.getContent());
        this.esEntityPackage = packageConfig.getPackageInfo().get(TemplateEnum.ES_ENTITY.getContent());
        this.esServicePackage = packageConfig.getPackageInfo().get(TemplateEnum.ES_SERVICE.getContent());
        this.esServiceImplPackage = packageConfig.getPackageInfo().get(TemplateEnum.ES_SERVICE_IMPL.getContent());
        this.importPackage();
        this.modelImportPackage();
    }

    public TableInfo setComment(String comment) {
        this.comment = StringUtils.isNotBlank(comment) ? comment.replace("\"", "\\\"") : comment;
        return this;
    }

    public TableInfo setHavePrimaryKey(boolean havePrimaryKey) {
        this.havePrimaryKey = havePrimaryKey;
        return this;
    }

    public Set<String> getImportPackages() {
        return importPackages;
    }

    public Set<String> getModelImportPackages() {
        return modelImportPackages;
    }

    public Set<String> getFieldImportPackages() {
        return fieldImportPackages;
    }

    public boolean isConvert() {
        return convert;
    }

    public TableInfo setConvert(boolean convert) {
        this.convert = convert;
        return this;
    }

    public String getName() {
        return tableName;
    }

    public String getOriginName() {
        return originName;
    }

    public String getComment() {
        return comment;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getDtoName() {
        return dtoName;
    }

    public String getSaveDtoName() {
        return saveDtoName;
    }

    public String getVoName() {
        return voName;
    }

    public String getMapperName() {
        return mapperName;
    }

    public String getXmlName() {
        return xmlName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceImplName() {
        return serviceImplName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public List<TableField> getFields() {
        return fields;
    }

    public boolean isHavePrimaryKey() {
        return havePrimaryKey;
    }

    public List<TableField> getCommonFields() {
        return commonFields;
    }

    public StrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public GlobalConfig getGlobalConfig() {
        return globalConfig;
    }

    public String getTableName() {
        return tableName;
    }

    public EntityModel getEntityModel() {
        return entityModel;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public String getDtoPackage() {
        return dtoPackage;
    }

    public String getVoPackage() {
        return voPackage;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public String getTableComment() {
        return this.comment;
    }

    public String getEsEntityName() {
        return esEntityName;
    }

    public String getEsServiceName() {
        return esServiceName;
    }

    public String getEsServiceImplName() {
        return esServiceImplName;
    }

    public String getEsEntityPackage() {
        return esEntityPackage;
    }

    public String getEsServicePackage() {
        return esServicePackage;
    }

    public String getEsServiceImplPackage() {
        return esServiceImplPackage;
    }

    public String getModulePackage() {
        return modulePackage;
    }

    public String getCommonPackage() {
        return commonPackage;
    }

    public String getCapitalName() {
        return capitalName;
    }
}