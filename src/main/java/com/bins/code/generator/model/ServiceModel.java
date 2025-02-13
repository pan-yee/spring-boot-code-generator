package com.bins.code.generator.model;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.template.ITemplate;
import com.bins.code.generator.utils.ClassUtils;
import lombok.Data;

import java.util.Map;

@Data
public class ServiceModel extends BaseModel implements ITemplate {

    private static final ServiceModel model = new ServiceModel();

    private ServiceModel() {
        super();
    }

    public ServiceModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    public static ServiceModel getInstance() {
        return model;
    }

    private String serviceName;

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride;

    @Override
    public boolean isFileOverride() {
        return fileOverride;
    }

    /**
     * Service带包名父类全称
     */
    private String superClass = Constant.SUPER_SERVICE_CLASS;

    private String superServiceClassPackage = superClass;

    private String superServiceClass = ClassUtils.getSimpleName(this.superClass);

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    @Override
    public Map<String, Object> renderData(TableInfo tableInfo) {
        return null;
    }
}
