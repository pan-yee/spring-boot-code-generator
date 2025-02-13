package com.bins.code.generator.template.engine;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.model.BaseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class VelocityTemplateEngine extends AbstractTemplateEngine {

    private VelocityEngine velocityEngine;

    {
        try {
            Class.forName("org.apache.velocity.util.DuckType");
        } catch (ClassNotFoundException e) {
            log.warn("加载velocity异常", e);
        }
    }

    @Override
    public VelocityTemplateEngine init(ConfigBuilder configBuilder) {
        if (null == velocityEngine) {
            Properties p = new Properties();
            p.setProperty(Constant.VM_LOAD_PATH_KEY, Constant.VM_LOAD_PATH_VALUE);
            p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, StringPool.EMPTY);
            p.setProperty(Velocity.ENCODING_DEFAULT, Constant.UTF8);
            p.setProperty(Velocity.INPUT_ENCODING, Constant.UTF8);
            p.setProperty("file.resource.loader.unicode", StringPool.TRUE);
            velocityEngine = new VelocityEngine(p);
        }
        return this;
    }

    @Override
    public void writer(Map<String, Object> objectMap, String templatePath, File outputFile) throws Exception {
        Template template = velocityEngine.getTemplate(templatePath, Constant.UTF8);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, Constant.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            template.merge(new VelocityContext(objectMap), writer);
        }
    }

    @Override
    public void writerFile(BaseModel baseModel, String templatePath, File outputFile) throws Exception {
        Template template = velocityEngine.getTemplate(templatePath, Constant.UTF8);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, Constant.UTF8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            VelocityContext context = new VelocityContext();
            context.put("model", baseModel);
            template.merge(context, writer);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    public String templateFilePath(String filePath) {
        final String dotVm = ".vm";
        return filePath.endsWith(dotVm) ? filePath : filePath + dotVm;
    }
}
