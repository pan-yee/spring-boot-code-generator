package com.bins.code.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.enums.TemplateTypeEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PackageConfig {

    private PackageConfig() {
    }

    /**
     * 父包名
     */
    private String parent = "com.bins";

    /**
     * 模块名
     */
    private String moduleName = "";

    /**
     * Entity包名
     */
    private String entity = "entity";

    /**
     * Service包名
     */
    private String service = "service";

    /**
     * Service Impl包名
     */
    private String serviceImpl = "service.impl";

    /**
     * dto包名
     */
    private String dto = "interfaces.model.dto";

    /**
     * dto包名
     */
    private String pageDto = "common.model.dto";

    /**
     * constants包名
     */
    private String constants = "common.constants";

    /**
     * constants包名
     */
    private String authUtils = "common.utils";

    /**
     * constants包名
     */
    private String businessException = "common.exception";

    /**
     * constants包名
     */
    private String errorCode = "common.enums";

    /**
     * constants包名
     */
    private String resultBody = "common.model.vo";

    /**
     * constants包名
     */
    private String validGroups = "common.model.valid";

    /**
     * vo包名
     */
    private String vo = "interfaces.model.vo";

    /**
     * Mapper包名
     */
    private String mapper = "mapper";

    /**
     * Mapper XML包名
     */
    private String xml = "mapper.xml";

    /**
     * Entity包名
     */
    private String esEntity = "es.entity";

    /**
     * Service包名
     */
    private String esService = "es.service";

    /**
     * Service Impl包名
     */
    private String esServiceImpl = "es.service.impl";

    /**
     * Controller包名
     */
    private String controller = "controller";

    /**
     * 路径配置信息
     */
    private Map<String, String> pathInfo;

    /**
     * 包配置信息
     */
    private final Map<String, String> packageInfo = new HashMap<>();

    /**
     * 父包名
     */
    public String getParent() {
        if (StringUtils.isNotBlank(moduleName)) {
            return parent + Constant.DOT + moduleName;
        }
        return parent;
    }

    /**
     * 模块包名
     */
    public String getCommonPkg() {
        return parent;
    }

    /**
     * 连接父子包名
     *
     * @param subPackage 子包名
     * @return 连接后的包名
     */
    public String joinPackage(String subPackage, int templateType) {
        String parent = getCommonPkg();
        if (TemplateTypeEnum.isCommon(templateType)) {
            if (ObjectUtils.isEmpty(parent)) {
                return subPackage;
            }
            return parent + StringPool.DOT + subPackage;
        } else {
            if (ObjectUtils.isNotEmpty(parent) && ObjectUtils.isNotEmpty(this.moduleName)) {
                return parent + StringPool.DOT + moduleName + StringPool.DOT + subPackage;
            } else if (ObjectUtils.isNotEmpty(parent)) {
                return parent + StringPool.DOT + subPackage;
            } else if (ObjectUtils.isNotEmpty(moduleName)) {
                return moduleName + StringPool.DOT + subPackage;
            } else {
                return subPackage;
            }
        }
    }

    /**
     * 获取包配置信息
     */
    public Map<String, String> getPackageInfo() {
        if (packageInfo.isEmpty()) {
            packageInfo.put(TemplateEnum.MODULE_NAME.getContent(), this.getModuleName());
            packageInfo.put(TemplateEnum.PARENT.getContent(), this.getParent());
            packageInfo.put(TemplateEnum.COMMON.getContent(), this.getCommonPkg());
            packageInfo.put(TemplateEnum.ENTITY.getContent(), this.joinPackage(this.getEntity(), TemplateEnum.ENTITY.getTemplateType()));
            packageInfo.put(TemplateEnum.DTO.getContent(), this.joinPackage(this.getDto(), TemplateEnum.DTO.getTemplateType()));
            packageInfo.put(TemplateEnum.VO.getContent(), this.joinPackage(this.getVo(), TemplateEnum.VO.getTemplateType()));
            packageInfo.put(TemplateEnum.MAPPER.getContent(), this.joinPackage(this.getMapper(), TemplateEnum.MAPPER.getTemplateType()));
            packageInfo.put(TemplateEnum.MAPPER_XML.getContent(), this.joinPackage(this.getXml(), TemplateEnum.MAPPER_XML.getTemplateType()));
            packageInfo.put(TemplateEnum.SERVICE.getContent(), this.joinPackage(this.getService(), TemplateEnum.SERVICE.getTemplateType()));
            packageInfo.put(TemplateEnum.SERVICE_IMPL.getContent(), this.joinPackage(this.getServiceImpl(), TemplateEnum.SERVICE_IMPL.getTemplateType()));
            packageInfo.put(TemplateEnum.CONTROLLER.getContent(), this.joinPackage(this.getController(), TemplateEnum.CONTROLLER.getTemplateType()));
            packageInfo.put(TemplateEnum.ES_ENTITY.getContent(), this.joinPackage(this.getEsEntity(), TemplateEnum.ES_ENTITY.getTemplateType()));
            packageInfo.put(TemplateEnum.ES_SERVICE.getContent(), this.joinPackage(this.getEsService(), TemplateEnum.ES_SERVICE.getTemplateType()));
            packageInfo.put(TemplateEnum.ES_SERVICE_IMPL.getContent(), this.joinPackage(this.getEsServiceImpl(), TemplateEnum.ES_SERVICE_IMPL.getTemplateType()));
        }
        return Collections.unmodifiableMap(this.packageInfo);
    }

    /**
     * 获取包配置信息
     *
     * @param module 模块
     * @return 配置信息
     */
    public String getPackageInfo(String module) {
        return getPackageInfo().get(module);
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getEntity() {
        return entity;
    }

    public String getDto() {
        return dto;
    }

    public String getVo() {
        return vo;
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

    public String getAuthUtils() {
        return authUtils;
    }

    public String getBusinessException() {
        return businessException;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getResultBody() {
        return resultBody;
    }

    public String getValidGroups() {
        return validGroups;
    }

    public String getController() {
        return controller;
    }

    public Map<String, String> getPathInfo() {
        return pathInfo;
    }

    public static class Builder implements IConfigBuilder<PackageConfig> {

        private final PackageConfig packageConfig;

        public Builder() {
            this.packageConfig = new PackageConfig();
        }

        public Builder(String parent, String moduleName) {
            this();
            this.packageConfig.parent = parent;
            this.packageConfig.moduleName = moduleName;
        }

        /**
         * 指定父包名
         *
         * @param parent 父包名
         * @return this
         */
        public Builder parent(String parent) {
            this.packageConfig.parent = parent;
            return this;
        }

        /**
         * 指定模块名称
         *
         * @param moduleName 模块名
         * @return this
         */
        public Builder moduleName(String moduleName) {
            this.packageConfig.moduleName = moduleName;
            return this;
        }

        /**
         * 指定实体包名
         *
         * @param entity 实体包名
         * @return this
         */
        public Builder entity(String entity) {
            this.packageConfig.entity = entity;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param dto dto包名
         * @return this
         */
        public Builder dto(String dto) {
            this.packageConfig.dto = dto;
            return this;
        }

        /**
         * 指定vo包名
         *
         * @param vo vo包名
         * @return this
         */
        public Builder vo(String vo) {
            this.packageConfig.vo = vo;
            return this;
        }

        /**
         * 指定service接口包名
         *
         * @param service service包名
         * @return this
         */
        public Builder service(String service) {
            this.packageConfig.service = service;
            return this;
        }

        /**
         * service实现类包名
         *
         * @param serviceImpl service实现类包名
         * @return this
         */
        public Builder serviceImpl(String serviceImpl) {
            this.packageConfig.serviceImpl = serviceImpl;
            return this;
        }

        /**
         * 指定mapper接口包名
         *
         * @param mapper mapper包名
         * @return this
         */
        public Builder mapper(String mapper) {
            this.packageConfig.mapper = mapper;
            return this;
        }

        /**
         * 指定xml包名
         *
         * @param xml xml包名
         * @return this
         */
        public Builder xml(String xml) {
            this.packageConfig.xml = xml;
            return this;
        }

        /**
         * 指定实体包名
         *
         * @param esEntity 实体包名
         * @return this
         */
        public Builder esEntity(String esEntity) {
            this.packageConfig.esEntity = esEntity;
            return this;
        }

        /**
         * 指定service接口包名
         *
         * @param esService esService包名
         * @return this
         */
        public Builder esService(String esService) {
            this.packageConfig.esService = esService;
            return this;
        }

        /**
         * service实现类包名
         *
         * @param esServiceImpl service实现类包名
         * @return this
         */
        public Builder esServiceImpl(String esServiceImpl) {
            this.packageConfig.esServiceImpl = esServiceImpl;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param pageDto dto包名
         * @return this
         */
        public Builder pageDto(String pageDto) {
            this.packageConfig.pageDto = pageDto;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param constants 常量包名
         * @return this
         */
        public Builder constants(String constants) {
            this.packageConfig.constants = constants;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param pkg 常量包名
         * @return this
         */
        public Builder authUtils(String pkg) {
            this.packageConfig.authUtils = pkg;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param pkg 常量包名
         * @return this
         */
        public Builder businessException(String pkg) {
            this.packageConfig.businessException = pkg;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param pkg 常量包名
         * @return this
         */
        public Builder errorCode(String pkg) {
            this.packageConfig.errorCode = pkg;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param pkg 常量包名
         * @return this
         */
        public Builder resultBody(String pkg) {
            this.packageConfig.resultBody = pkg;
            return this;
        }

        /**
         * 指定dto包名
         *
         * @param pkg 常量包名
         * @return this
         */
        public Builder validGroups(String pkg) {
            this.packageConfig.validGroups = pkg;
            return this;
        }

        /**
         * 指定控制器包名
         *
         * @param controller 控制器包名
         * @return this
         */
        public Builder controller(String controller) {
            this.packageConfig.controller = controller;
            return this;
        }

        /**
         * 路径配置信息
         *
         * @param pathInfo 路径配置信息
         * @return this
         */
        public Builder pathInfo(Map<String, String> pathInfo) {
            this.packageConfig.pathInfo = pathInfo;
            return this;
        }

        /**
         * 构建包配置对象
         * <p>当指定{@link #parent(String)} 与 {@link #moduleName(String)}时,其他模块名字会加上这两个作为前缀</p>
         * <p>
         * 例如:
         * <p>当设置 {@link #parent(String)},那么entity的配置为 {@link #getParent()}.{@link #getEntity()}</p>
         * <p>当设置 {@link #parent(String)}与{@link #moduleName(String)},那么entity的配置为 {@link #getParent()}.{@link #getModuleName()}.{@link #getEntity()} </p>
         * </p>
         *
         * @return 包配置对象
         */
        @Override
        public PackageConfig build() {
            return this.packageConfig;
        }
    }
}
