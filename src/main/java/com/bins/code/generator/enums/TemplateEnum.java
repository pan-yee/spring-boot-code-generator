package com.bins.code.generator.enums;

import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.strategy.impl.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 模板枚举类
 *
 * @author bins
 */
public enum TemplateEnum {

    PARENT("parent" , Constant.PARENT, "" , "" , null, TemplateTypeEnum.EMPTY.getCode()),
    COMMON("common" , Constant.COMMON, "" , "" , null, TemplateTypeEnum.EMPTY.getCode()),
    MODULE_NAME("moduleName" , Constant.MODULE_NAME, "" , "" , null, TemplateTypeEnum.EMPTY.getCode()),
    ENTITY("entity" , Constant.ENTITY, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_ENTITY_JAVA, EntityStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    DTO("dto" , Constant.DTO, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_DTO_JAVA, DtoStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    VO("vo" , Constant.VO, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_VO_JAVA, VoStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    MAPPER("mapper" , Constant.MAPPER, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_MAPPER, MapperStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    MAPPER_XML("mapperXml" , Constant.XML, FileTypeEnum.XML.getCode(), Constant.TEMPLATE_XML, MapperXmlStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    SERVICE("service" , Constant.SERVICE, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_SERVICE, ServiceStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    SERVICE_IMPL("serviceImpl" , Constant.SERVICE_IMPL, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_SERVICE_IMPL, ServiceImplStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    CONTROLLER("controller" , Constant.CONTROLLER, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_CONTROLLER, ControllerStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    ES_ENTITY("esEntity" , Constant.ES_ENTITY, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_ES_ENTITY_JAVA, EsEntityStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    ES_SERVICE("esService" , Constant.ES_SERVICE, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_ES_SERVICE, EsServiceGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    ES_SERVICE_IMPL("esServiceImpl" , Constant.ES_SERVICE_IMPL, FileTypeEnum.JAVA.getCode(), Constant.TEMPLATE_ES_SERVICE_IMPL, EsServiceImplGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    WEB_ROUTER("routerIndex" , Constant.WEB_ROUTER, FileTypeEnum.TS.getCode(), Constant.TEMPLATE_WEB_ROUTER_TS, WebRouterStrategyGenerator.getInstance(), TemplateTypeEnum.COMMON.getCode()),
    WEB_PAGE("pageIndex" , Constant.WEB_PAGE, FileTypeEnum.TS.getCode(), Constant.TEMPLATE_WEB_PAGE_TS, WebPageStrategyGenerator.getInstance(), TemplateTypeEnum.GENERAL.getCode()),
    WEB_API("apiIndex" , Constant.WEB_API, FileTypeEnum.TS.getCode(), Constant.TEMPLATE_WEB_API_TS, WebApiStrategyGenerator.getInstance(), TemplateTypeEnum.COMMON.getCode()),
    ;
    /**
     * 枚举编码
     */
    private String code;

    /**
     * 枚举名称
     */
    private String content;

    /**
     * 枚举名称
     */
    private String fileType;

    /**
     * 模板路径
     */
    private String templatePath;

    /**
     * 输出文件
     */
    private IGenerator generator;

    /**
     * 模块分类
     * 0: 空模板
     * 1: 普通模板
     * 2: 公用模板
     */
    private int templateType;

    TemplateEnum(String code, String content, String fileType, String templatePath, IGenerator generator, int templateType) {
        this.code = code;
        this.content = content;
        this.fileType = fileType;
        this.generator = generator;
        this.templatePath = templatePath;
        this.templateType = templateType;
    }

    public String getCode() {
        return this.code;
    }

    public String getContent() {
        return this.content;
    }

    public String getFileType() {
        return this.fileType;
    }

    public IGenerator getGenerator() {
        return generator;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public int getTemplateType() {
        return templateType;
    }

    public static List<IGenerator> getGeneralGeneratorList() {
        List<IGenerator> list = new ArrayList<>();
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            if (TemplateTypeEnum.isGeneral(templateEnum.getTemplateType()) && ObjectUtils.isNotEmpty(templateEnum.getGenerator())) {
                list.add(templateEnum.getGenerator());
            }
        }
        return list;
    }

    public static List<IGenerator> getCommonGeneratorList() {
        List<IGenerator> list = new ArrayList<>();
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            if (TemplateTypeEnum.isCommon(templateEnum.getTemplateType()) && ObjectUtils.isNotEmpty(templateEnum.getGenerator())) {
                list.add(templateEnum.getGenerator());
            }
        }
        return list;
    }
}
