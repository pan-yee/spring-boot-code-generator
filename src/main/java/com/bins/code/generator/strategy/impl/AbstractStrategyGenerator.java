package com.bins.code.generator.strategy.impl;

import com.bins.code.generator.config.TemplateConfig;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.strategy.IGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public abstract class AbstractStrategyGenerator implements IGenerator {

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    public void setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    protected Optional<String> getTemplateFilePath(Function<TemplateConfig, String> function) {
        TemplateConfig templateConfig = configBuilder.getTemplateConfig();
        String filePath = function.apply(templateConfig);
        if (StringUtils.isNotBlank(filePath)) {
            return Optional.of(templateFilePath(filePath));
        }
        return Optional.empty();
    }

    public String templateFilePath(String filePath) {
        final String dotVm = ".vm";
        return filePath.endsWith(dotVm) ? filePath : filePath + dotVm;
    }

    protected String suffixJavaOrKt() {
        return Constant.JAVA_SUFFIX;
    }

    protected boolean isCreate(File file, boolean fileOverride) {
        if (file.exists() && !fileOverride) {
            log.warn("文件[{}]已存在，且未开启文件覆盖配置，需要开启配置可到策略配置中设置！！！", file.getName());
        }
        return !file.exists() || fileOverride;
    }
}
