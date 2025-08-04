package com.bins.code.generator.model;

import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.model.po.TableField;
import com.bins.code.generator.model.po.TableInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@NoArgsConstructor
public class BaseModel implements IModel {

    public BaseModel(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 作者
     */
    private String author = "bins";

    /**
     * 日期
     */
    private String date;

    /**
     * 表字段
     */
    private List<TableField> fields = new ArrayList<>();

    /**
     * 表字段
     */
    private List<TableInfo> tables = new ArrayList<>();

    /**
     * 模块公用包名
     */
    private String modulePackage;

    /**
     * 基础公用包名
     */
    private String commonPackage;

    /**
     * 基础包名
     */
    private String baseServicePackage;

    /**
     * 组件包名
     */
    private String componentsPackage;

    /**
     * dto包名
     */
    private String dtoInterfacePackage;

    /**
     * vo包名
     */
    private String voInterfacePackage;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表备注
     */
    private String tableComment;

    /**
     * 实体名
     */
    private String entity;

    /**
     * 实体名称
     */
    private String entityName;

    /**
     * 实体包名
     */
    private String entityPackage;

    /**
     * 类名
     */
    private String className;

    /**
     * 对象原名
     */
    private String originName;

    /**
     * 首字母大写名称
     */
    private String capitalName;

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride;

    /**
     * 是否开启lombok
     */
    private boolean lombok;

    /**
     * 是否开启swagger
     */
    private boolean swagger;

    /**
     * 实体是否生成 serialVersionUID
     */
    private boolean serialVersionUID = true;

    /**
     * 是否禁用
     */
    private boolean disable = true;

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
     * 策略配置
     */
    private StrategyConfig strategyConfig;

    /**
     * 全局配置信息
     */
    private GlobalConfig globalConfig;

    /**
     * 包导入信息
     */
    private final Set<String> importPackages = new TreeSet<>();

    /**
     * 字段的包导入信息
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
     * 表注释
     */
    private String comment;

    /**
     * dto名称
     */
    private String dtoName;

    /**
     * saveDto名称
     */
    private String saveDtoName;

    /**
     * dto包名
     */
    private String dtoPackage;

    /**
     * vo名称
     */
    private String voName;

    /**
     * vo包名
     */
    private String voPackage;

    /**
     * mapper名称
     */
    private String mapperName;

    /**
     * mapper包名
     */
    private String mapperPackage;

    /**
     * xml名称
     */
    private String xmlName;

    /**
     * service名称
     */
    private String serviceName;

    /**
     * service包名
     */
    private String servicePackage;

    /**
     * serviceImpl名称
     */
    private String serviceImplName;

    /**
     * serviceImpl包名
     */
    private String serviceImplPackage;

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
     * controller名称
     */
    private String controllerName;

    /**
     * controller包名
     */
    private String controllerPackage;

    /**
     * 是否有主键
     */
    private boolean havePrimaryKey;

    /**
     * 公共字段
     */
    private final List<TableField> commonFields = new ArrayList<>();

    @Override
    public void setImportPackages(Set<String> importPackages) {

    }

    @Override
    public void initCommonConfig() {
        this.author = configBuilder.getGlobalConfig().getAuthor();
        this.date = configBuilder.getGlobalConfig().getCommentDate();
        this.baseServicePackage = "cn.commerce.base.interfaces";
        this.componentsPackage = "cn.commerce.components";
        this.dtoInterfacePackage = "cn.commerce.pms.interfaces.model.dto";
        this.voInterfacePackage = "cn.commerce.pms.interfaces.model.vo";
    }

    @Override
    public void initCommonPackage() {
        this.modulePackage = configBuilder.getPackageConfig().getPackageInfo().get(TemplateEnum.PARENT.getContent());
        this.commonPackage = configBuilder.getPackageConfig().getPackageInfo().get(TemplateEnum.COMMON.getContent());
    }

}
