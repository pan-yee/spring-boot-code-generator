package com.bins.code.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.generator.EntityGeneratorStrategy;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.template.TemplateType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Slf4j
@Getter
public class TemplateConfig {

    private EntityGeneratorStrategy entityTemplateStrategy;

    /**
     * 设置实体模板路径
     */
    private TemplateEnum entity;

    /**
     * 设置dto模板路径
     */
    private String dto;

    /**
     * 设置saveDto模板路径
     */
    private String saveDto;

    /**
     * 设置vo模板路径
     */
    private String vo;

    /**
     * 设置控制器模板路径
     */
    private String controller;

    /**
     * 设置Mapper模板路径
     */
    private String mapper;

    /**
     * 设置MapperXml模板路径
     */
    private String xml;

    /**
     * 设置Service模板路径
     */
    private String service;

    /**
     * 设置ServiceImpl模板路径
     */
    private String serviceImpl;

    /**
     * 设置实体模板路径
     */
    private String esEntity;

    /**
     * 设置Service模板路径
     */
    private String esService;

    /**
     * 设置ServiceImpl模板路径
     */
    private String esServiceImpl;

    /**
     * 设置pageDto模板路径
     */
    private String pageDto;

    /**
     * 设置constants模板路径
     */
    private String constants;

    /**
     * 设置constants模板路径
     */
    private String errorCode;

    /**
     * 设置constants模板路径
     */
    private String webRouter;

    /**
     * 设置constants模板路径
     */
    private String webPage;

    /**
     * 设置constants模板路径
     */
    private String webApi;

    /**
     * 是否禁用实体模板（默认 false）
     */
    private boolean disableEntity;

    /**
     * 是否禁用dto模板（默认 false）
     */
    private boolean disableDto;

    /**
     * 是否禁用vo模板（默认 false）
     */
    private boolean disableVo;

    /**
     * 不对外爆露
     */
    private TemplateConfig() {
        this.entity = TemplateEnum.ENTITY;
        this.dto = TemplateEnum.DTO.getTemplatePath();
        this.saveDto = TemplateEnum.SAVE_DTO.getTemplatePath();
        this.vo = TemplateEnum.VO.getTemplatePath();
        this.controller = TemplateEnum.CONTROLLER.getTemplatePath();
        this.mapper = TemplateEnum.MAPPER.getTemplatePath();
        this.xml = TemplateEnum.MAPPER_XML.getTemplatePath();
        this.service = TemplateEnum.SERVICE.getTemplatePath();
        this.serviceImpl = TemplateEnum.SERVICE_IMPL.getTemplatePath();
        this.esEntity = TemplateEnum.ES_ENTITY.getTemplatePath();
        this.esService = TemplateEnum.ES_SERVICE.getTemplatePath();
        this.esServiceImpl = TemplateEnum.ES_SERVICE_IMPL.getTemplatePath();
        this.webRouter = TemplateEnum.WEB_ROUTER.getTemplatePath();
        this.webPage = TemplateEnum.WEB_PAGE.getTemplatePath();
        this.webApi = TemplateEnum.WEB_API.getTemplatePath();
    }

    /**
     * 当模板赋值为空时进行日志提示打印
     *
     * @param value        模板值
     * @param templateType 模板类型
     */
    private void logger(String value, TemplateType templateType) {
        if (StringUtils.isBlank(value)) {
            log.warn("推荐使用disable(TemplateType.{})方法进行默认模板禁用." , templateType.name());
        }
    }

    /**
     * 获取实体模板路径
     *
     * @return 模板路径
     */
    public String getEntity() {
        if (!this.disableEntity) {
            return ObjectUtils.isEmpty(this.entity) ? TemplateEnum.ENTITY.getTemplatePath() : this.entity.getTemplatePath();
        }
        return null;
    }

    /**
     * 获取dto模板路径
     *
     * @return 模板路径
     */
    public String getDto() {
        if (!this.disableDto) {
            return StringUtils.isBlank(this.dto) ? Constant.TEMPLATE_DTO_JAVA : this.dto;
        }
        return null;
    }

    /**
     * 获取dto模板路径
     *
     * @return 模板路径
     */
    public String getSaveDto() {
        if (!this.disableDto) {
            return StringUtils.isBlank(this.saveDto) ? Constant.TEMPLATE_SAVE_DTO_JAVA : this.saveDto;
        }
        return null;
    }

    /**
     * 获vo模板路径
     *
     * @return 模板路径
     */
    public String getVo() {
        if (!this.disableVo) {
            return StringUtils.isBlank(this.vo) ? Constant.TEMPLATE_VO_JAVA : this.vo;
        }
        return null;
    }

    /**
     * 禁用模板
     *
     * @param templateTypes 模板类型
     * @return this
     * @since 3.3.2
     */
    public TemplateConfig disable(TemplateType... templateTypes) {
        if (templateTypes != null && templateTypes.length > 0) {
            for (TemplateType templateType : templateTypes) {
                switch (templateType) {
                    case ENTITY:
                        this.entity = null;
                        break;
                    case DTO:
                        this.dto = null;
                        break;
                    case VO:
                        this.vo = null;
                        break;
                    case CONTROLLER:
                        this.controller = null;
                        break;
                    case MAPPER:
                        this.mapper = null;
                        break;
                    case XML:
                        this.xml = null;
                        break;
                    case SERVICE:
                        this.service = null;
                        break;
                    case SERVICE_IMPL:
                        this.serviceImpl = null;
                        break;
                    default:
                }
            }
        }
        return this;
    }

    /**
     * 禁用全部模板
     *
     * @return this
     */
    public TemplateConfig disable() {
        return disable(TemplateType.values());
    }

    public String getService() {
        return service;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public String getMapper() {
        return mapper;
    }

    public String getXml() {
        return xml;
    }

    public String getController() {
        return controller;
    }

    public String getEsEntity() {
        return esEntity;
    }

    public String getEsService() {
        return esService;
    }

    public String getEsServiceImpl() {
        return esServiceImpl;
    }

    public String getPageDto() {
        return pageDto;
    }

    public String getConstants() {
        return constants;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getWebRouter() {
        return webRouter;
    }

    public String getWebPage() {
        return webPage;
    }

    public String getWebApi() {
        return webApi;
    }

    /**
     * 模板路径配置构建者
     */
    public static class Builder implements IConfigBuilder<TemplateConfig> {

        private final TemplateConfig templateConfig;

        /**
         * 默认生成一个空的
         */
        public Builder() {
            this.templateConfig = new TemplateConfig();
        }

        /**
         * 禁用所有模板
         *
         * @return this
         */
        public Builder disable() {
            this.templateConfig.disable();
            return this;
        }

        /**
         * 禁用模板
         *
         * @return this
         */
        public Builder disable(TemplateType... templateTypes) {
            this.templateConfig.disable(templateTypes);
            return this;
        }

        /**
         * 设置实体模板路径(JAVA)
         *
         * @param entityTemplate 实体模板
         * @return this
         */
        public Builder entity(String entityTemplate) {
            this.templateConfig.disableEntity = false;
            // this.templateConfig.entity = entityTemplate;
            return this;
        }

        /**
         * 设置实体模板路径(JAVA)
         *
         * @param dtoTemplate 实体模板
         * @return this
         */
        public Builder dto(String dtoTemplate) {
            this.templateConfig.disableDto = false;
            this.templateConfig.dto = dtoTemplate;
            return this;
        }

        /**
         * 设置实体模板路径(JAVA)
         *
         * @param voTemplate 实体模板
         * @return this
         */
        public Builder vo(String voTemplate) {
            this.templateConfig.disableVo = false;
            this.templateConfig.vo = voTemplate;
            return this;
        }

        /**
         * 设置service模板路径
         *
         * @param serviceTemplate service接口模板路径
         * @return this
         */
        public Builder service(String serviceTemplate) {
            this.templateConfig.service = serviceTemplate;
            return this;
        }

        /**
         * 设置serviceImpl模板路径
         *
         * @param serviceImplTemplate service实现类模板路径
         * @return this
         */
        public Builder serviceImpl(String serviceImplTemplate) {
            this.templateConfig.serviceImpl = serviceImplTemplate;
            return this;
        }

        /**
         * 设置mapper模板路径
         *
         * @param mapperTemplate mapper模板路径
         * @return this
         */
        public Builder mapper(String mapperTemplate) {
            this.templateConfig.mapper = mapperTemplate;
            return this;
        }

        /**
         * 设置mapperXml模板路径
         *
         * @param xmlTemplate xml模板路径
         * @return this
         */
        public Builder xml(String xmlTemplate) {
            this.templateConfig.xml = xmlTemplate;
            return this;
        }

        /**
         * 设置实体模板路径(JAVA)
         *
         * @param esEntityTemplate 实体模板
         * @return this
         */
        public Builder esEntity(String esEntityTemplate) {
            this.templateConfig.esEntity = esEntityTemplate;
            return this;
        }

        /**
         * 设置service模板路径
         *
         * @param esServiceTemplate service接口模板路径
         * @return this
         */
        public Builder esService(String esServiceTemplate) {
            this.templateConfig.esService = esServiceTemplate;
            return this;
        }

        /**
         * 设置serviceImpl模板路径
         *
         * @param esServiceImplTemplate service实现类模板路径
         * @return this
         */
        public Builder esServiceImpl(String esServiceImplTemplate) {
            this.templateConfig.esServiceImpl = esServiceImplTemplate;
            return this;
        }

        /**
         * 设置实体模板路径(JAVA)
         *
         * @param pageDtoTemplate 实体模板
         * @return this
         */
        public Builder pageDto(String pageDtoTemplate) {
            this.templateConfig.pageDto = pageDtoTemplate;
            return this;
        }

        /**
         * 设置常量类模板路径(JAVA)
         *
         * @param constantsTemplate 常量类模板
         * @return this
         */
        public Builder constants(String constantsTemplate) {
            this.templateConfig.constants = constantsTemplate;
            return this;
        }

        /**
         * 设置常量类模板路径(JAVA)
         *
         * @param template 常量类模板
         * @return this
         */
        public Builder errorCode(String template) {
            this.templateConfig.errorCode = template;
            return this;
        }

        /**
         * 设置控制器模板路径
         *
         * @param controllerTemplate 控制器模板路径
         * @return this
         */
        public Builder controller(String controllerTemplate) {
            this.templateConfig.controller = controllerTemplate;
            return this;
        }

        /**
         * 设置控制器模板路径
         *
         * @param webRouterTemplate 控制器模板路径
         * @return this
         */
        public Builder webRouter(String webRouterTemplate) {
            this.templateConfig.webRouter = webRouterTemplate;
            return this;
        }

        /**
         * 设置控制器模板路径
         *
         * @param webPageTemplate 控制器模板路径
         * @return this
         */
        public Builder webPage(String webPageTemplate) {
            this.templateConfig.webPage = webPageTemplate;
            return this;
        }

        /**
         * 设置控制器模板路径
         *
         * @param webApiTemplate 控制器模板路径
         * @return this
         */
        public Builder webApi(String webApiTemplate) {
            this.templateConfig.webApi = webApiTemplate;
            return this;
        }

        /**
         * 构建模板配置对象
         *
         * @return 模板配置对象
         */
        @Override
        public TemplateConfig build() {
            return this.templateConfig;
        }
    }
}
