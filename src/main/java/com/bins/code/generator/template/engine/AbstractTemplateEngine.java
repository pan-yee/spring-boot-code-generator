package com.bins.code.generator.template.engine;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.enums.FileTypeEnum;
import com.bins.code.generator.enums.TemplateEnum;
import com.bins.code.generator.model.BaseModel;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.strategy.IGenerator;
import com.bins.code.generator.utils.FileUtils;
import com.bins.code.generator.utils.GeneratorUtils;
import com.bins.code.generator.utils.RuntimeUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractTemplateEngine {

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 模板引擎初始化
     */
    public abstract AbstractTemplateEngine init(ConfigBuilder configBuilder);

    /**
     * 输出文件（3.5.4版本会删除此方法）
     *
     * @param file         文件
     * @param objectMap    渲染信息
     * @param templatePath 模板路径
     * @since 3.5.0
     */
    @Deprecated
    protected void outputFile(File file, Map<String, Object> objectMap, String templatePath) {
        outputFile(file, objectMap, templatePath, false);
    }

    /**
     * 输出文件
     *
     * @param file         文件
     * @param objectMap    渲染信息
     * @param templatePath 模板路径
     * @param fileOverride 是否覆盖已有文件
     * @since 3.5.2
     */
    protected void outputFile(File file, Map<String, Object> objectMap, String templatePath, boolean fileOverride) {
        if (isCreate(file, fileOverride)) {
            try {
                // 全局判断【默认】
                boolean exist = file.exists();
                if (!exist) {
                    File parentFile = file.getParentFile();
                    FileUtils.forceMkdir(parentFile);
                }
                writer(objectMap, templatePath, file);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * 获取路径信息
     *
     * @param templateCode 输出文件
     * @return 路径信息
     */
    protected String getPathInfo(String templateCode) {
        return getConfigBuilder().getPathInfo().get(templateCode);
    }

    /**
     * 批量输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            List<TableInfo> tableInfoList = config.getTableInfoList();
            List<IGenerator> commonGeneratorList = getCommonGeneratorList(config);
            if (!CollectionUtils.isEmpty(commonGeneratorList)) {
                for (IGenerator generator : commonGeneratorList) {
                    generator.setConfigBuilder(config);
                    generator.initModel(null);
                    BaseModel model = generator.getModel();
                    model.setTables(tableInfoList);
                    String modelName = generator.getModelFileName();
                    String modelPath = getPathInfo(generator.getTemplateCode());
                    String tsFilePath = GeneratorUtils.getTsFilePath();
                    if (ObjectUtils.isEmpty(modelName) || ObjectUtils.isEmpty(modelPath)) {
                        log.error("modelName或者modelPath为空, modelName:{}, modelPath:{}", modelName, modelPath);
                        continue;
                    }
                    TemplateEnum templateEnum = generator.getModelTemplate();
                    if (ObjectUtils.isEmpty(templateEnum)) {
                        log.error("templateEnum为空, , modelName:{}, modelPath:{}", modelName, modelPath);
                        continue;
                    }

                    if (FileTypeEnum.JAVA.getCode().equals(templateEnum.getFileType())) {
                        String modelTemplatePath = templateFilePath(generator.getModelTemplatePath());
                        String modelFile = String.format((modelPath + File.separator + "%s" + suffixJavaOrKt()), modelName);
                        writer(model, modelTemplatePath, new File(modelFile));
                    } else if (FileTypeEnum.TS.getCode().equals(templateEnum.getFileType())) {
                        String modelTemplatePath = templateFilePath(generator.getModelTemplatePath());
                        String tsFile = tsFilePath + File.separator + File.separator + modelName + File.separator + "index" + Constant.TS_SUFFIX;
                        writer(model, modelTemplatePath, new File(tsFile));
                    }
                }
            }
            List<IGenerator> generalGeneratorList = getGeneralGeneratorList(config);
            // List<IGenerator> generalGeneratorList = config.getStrategyConfig().getGeneratorList();
            if (!CollectionUtils.isEmpty(generalGeneratorList)) {
                tableInfoList.forEach(tableInfo -> {
                    for (IGenerator generator : generalGeneratorList) {
                        generator.setConfigBuilder(config);
                        generator.initModel(tableInfo);
                        BaseModel model = generator.getModel();
                        String modelName = generator.getModelFileName();
                        String modelPath = getPathInfo(generator.getTemplateCode());
                        String tsFilePath = GeneratorUtils.getTsFilePath();
                        if (ObjectUtils.isEmpty(modelName) || ObjectUtils.isEmpty(modelPath)) {
                            log.error("modelName或者modelPath为空, modelName:{}, modelPath:{}", modelName, modelPath);
                            continue;
                        }
                        TemplateEnum templateEnum = generator.getModelTemplate();
                        if (ObjectUtils.isEmpty(templateEnum)) {
                            log.error("templateEnum为空, , modelName:{}, modelPath:{}", modelName, modelPath);
                            continue;
                        }

                        if (FileTypeEnum.JAVA.getCode().equals(templateEnum.getFileType())) {
                            String modelTemplatePath = templateFilePath(generator.getModelTemplatePath());
                            String modelFile = String.format((modelPath + File.separator + "%s" + suffixJavaOrKt()), modelName);
                            writer(model, modelTemplatePath, new File(modelFile));
                        } else if (FileTypeEnum.XML.getCode().equals(templateEnum.getFileType())) {
                            String modelTemplatePath = templateFilePath(generator.getModelTemplatePath());
                            String xmlFile = String.format((modelPath + File.separator + tableInfo.getXmlName() + Constant.XML_SUFFIX), modelName);
                            writer(model, modelTemplatePath, new File(xmlFile));
                        } else if (FileTypeEnum.TS.getCode().equals(templateEnum.getFileType())) {
                            String modelTemplatePath = templateFilePath(generator.getModelTemplatePath());
                            String tsFile = tsFilePath + File.separator + "views" + File.separator + modelName + Constant.TS_SUFFIX;
                            writer(model, modelTemplatePath, new File(tsFile));
                        }
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    private List<IGenerator> getGeneralGeneratorList(ConfigBuilder config) {
        List<IGenerator> generatorList = TemplateEnum.getGeneralGeneratorList();
        return getCustomGeneratorList(config, generatorList);
    }

    private List<IGenerator> getCommonGeneratorList(ConfigBuilder config) {
        List<IGenerator> generatorList = TemplateEnum.getCommonGeneratorList();
        return getCustomGeneratorList(config, generatorList);
    }

    private List<IGenerator> getCustomGeneratorList(ConfigBuilder config, List<IGenerator> generatorList) {
        List<IGenerator> customGeneratorList = config.getStrategyConfig().getGeneratorList();
        Map<String, IGenerator> customGeneratorMap = Maps.newHashMap();
        for (IGenerator generator : customGeneratorList) {
            customGeneratorMap.put(generator.getGeneratorName(), generator);
        }
        List<IGenerator> newGeneratorList = Lists.newArrayList();
        for (IGenerator generator : generatorList) {
            IGenerator customGenerator = customGeneratorMap.get(generator.getGeneratorName());
            if (ObjectUtils.isEmpty(customGenerator)) {
                newGeneratorList.add(generator);
            } else {
                newGeneratorList.add(customGenerator);
            }
        }
        return newGeneratorList;
    }

    /**
     * 输出文件（3.5.4版本会删除此方法）
     *
     * @param objectMap    渲染数据
     * @param templatePath 模板路径
     * @param outputFile   输出文件
     * @throws Exception ex
     * @deprecated 3.5.0
     */
    @Deprecated
    protected void writerFile(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
        if (StringUtils.isNotBlank(templatePath)) {
            this.writer(objectMap, templatePath, outputFile);
        }
    }

    /**
     * 将模板转化成为文件（3.5.4版本会删除此方法）
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @see #writer(Map, String, File)
     * @deprecated 3.5.0
     */
    @Deprecated
    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {

    }

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     * @throws Exception 异常
     * @since 3.5.0
     */
    public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
        this.writer(objectMap, templatePath, outputFile.getPath());
        log.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }

    public void writer(BaseModel baseModel, String templatePath, File outputFile) {
        try {
            boolean exist = outputFile.exists();
            if (!exist) {
                File parentFile = outputFile.getParentFile();
                FileUtils.forceMkdir(parentFile);
            }
            this.writerFile(baseModel, templatePath, outputFile);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    void writerFile(BaseModel baseModel, String templatePath, File outputFile) throws Exception {

    }


    /**
     * 打开输出目录
     */
    public void open() {
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        if (StringUtils.isBlank(outDir) || !new File(outDir).exists()) {
            System.err.println("未找到输出目录：" + outDir);
        } else if (getConfigBuilder().getGlobalConfig().isOpen()) {
            try {
                RuntimeUtils.openDir(outDir);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String templateFilePath(String filePath);

    /**
     * 检查文件是否创建文件
     *
     * @param file         文件
     * @param fileOverride 是否覆盖已有文件
     * @return 是否创建文件
     * @since 3.5.2
     */
    protected boolean isCreate(File file, boolean fileOverride) {
        if (file.exists() && !fileOverride) {
            log.warn("文件[{}]已存在，且未开启文件覆盖配置，需要开启配置可到策略配置中设置！！！", file.getName());
        }
        return !file.exists() || fileOverride;
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return Constant.JAVA_SUFFIX;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}
