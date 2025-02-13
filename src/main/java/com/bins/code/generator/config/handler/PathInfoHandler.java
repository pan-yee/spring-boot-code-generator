package com.bins.code.generator.config.handler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.PackageConfig;
import com.bins.code.generator.config.TemplateConfig;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.constants.StringPool;
import com.bins.code.generator.enums.TemplateEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PathInfoHandler {

    /**
     * 输出文件Map
     */
    private final Map<String, String> pathInfo = new HashMap<>();

    /**
     * 输出目录
     */
    private final String outputDir;

    /**
     * 包配置信息
     */
    private final PackageConfig packageConfig;

    public PathInfoHandler(GlobalConfig globalConfig, TemplateConfig templateConfig, PackageConfig packageConfig) {
        this.outputDir = globalConfig.getOutputDir();
        this.packageConfig = packageConfig;
        this.setDefaultPathInfo(globalConfig, templateConfig);
        Map<String, String> pathInfo = packageConfig.getPathInfo();
        if (CollectionUtils.isNotEmpty(pathInfo)) {
            this.pathInfo.putAll(pathInfo);
        }
    }

    /**
     * 设置默认输出路径
     *
     * @param globalConfig   全局配置
     * @param templateConfig 模板配置
     */
    private void setDefaultPathInfo(GlobalConfig globalConfig, TemplateConfig templateConfig) {
        for (TemplateEnum templateEnum : TemplateEnum.values()) {
            putPathInfo(templateEnum.getCode(), templateEnum.getContent());
        }
        /**
        putPathInfo(templateConfig.getEntity(), TemplateEnum.ENTITY.getCode(), TemplateEnum.ENTITY.getContent());
        putPathInfo(templateConfig.getDto(), TemplateEnum.DTO.getCode(), TemplateEnum.DTO.getContent());
        putPathInfo(templateConfig.getVo(), TemplateEnum.VO.getCode(), TemplateEnum.VO.getContent());
        putPathInfo(templateConfig.getMapper(), TemplateEnum.MAPPER.getCode(), TemplateEnum.MAPPER.getContent());
        putPathInfo(templateConfig.getXml(), TemplateEnum.MAPPER_XML.getCode(), TemplateEnum.MAPPER_XML.getContent());
        putPathInfo(templateConfig.getService(), TemplateEnum.SERVICE.getCode(), TemplateEnum.SERVICE.getContent());
        putPathInfo(templateConfig.getServiceImpl(), TemplateEnum.SERVICE_IMPL.getCode(), TemplateEnum.SERVICE_IMPL.getContent());
        putPathInfo(templateConfig.getController(), TemplateEnum.CONTROLLER.getCode(), TemplateEnum.CONTROLLER.getContent());
        putPathInfo(templateConfig.getEsEntity(), TemplateEnum.ES_ENTITY.getCode(), TemplateEnum.ES_ENTITY.getContent());
        putPathInfo(templateConfig.getEsService(), TemplateEnum.ES_SERVICE.getCode(), TemplateEnum.ES_SERVICE.getContent());
        putPathInfo(templateConfig.getEsServiceImpl(), TemplateEnum.ES_SERVICE_IMPL.getCode(), TemplateEnum.ES_SERVICE_IMPL.getContent());
        putPathInfo(templateConfig.getPageDto(), TemplateEnum.PAGE_DTO.getCode(), TemplateEnum.PAGE_DTO.getContent());
        putPathInfo(templateConfig.getConstants(), TemplateEnum.CONSTANTS.getCode(), TemplateEnum.CONSTANTS.getContent());
        putPathInfo(templateConfig.getAuthUtils(), TemplateEnum.AUTH_UTILS.getCode(), TemplateEnum.AUTH_UTILS.getContent());
        putPathInfo(templateConfig.getBusinessException(), TemplateEnum.BUSINESS_EXCEPTION.getCode(), TemplateEnum.BUSINESS_EXCEPTION.getContent());
        putPathInfo(templateConfig.getErrorCode(), TemplateEnum.ERROR_CODE.getCode(), TemplateEnum.ERROR_CODE.getContent());
        putPathInfo(templateConfig.getResultBody(), TemplateEnum.RESULT_BODY.getCode(), TemplateEnum.RESULT_BODY.getContent());
        putPathInfo(templateConfig.getValidGroups(), TemplateEnum.VALID_GROUPS.getCode(), TemplateEnum.VALID_GROUPS.getContent());
        putPathInfo(TemplateEnum.PARENT.getCode(), TemplateEnum.PARENT.getContent());**/
    }

    public Map<String, String> getPathInfo() {
        return this.pathInfo;
    }

    private void putPathInfo(String template, String outputFile, String module) {
        if (StringUtils.isNotBlank(template)) {
            putPathInfo(outputFile, module);
        }
    }

    private void putPathInfo(String outputFile, String module) {
        pathInfo.putIfAbsent(outputFile, joinPath(outputDir, packageConfig.getPackageInfo(module)));
    }

    /**
     * 连接路径字符串
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(Constant.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        if (StringUtils.isBlank(packageName)) {
            return parentDir;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }
}
