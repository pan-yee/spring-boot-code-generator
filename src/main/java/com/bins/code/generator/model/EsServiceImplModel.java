package com.bins.code.generator.model;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.template.ITemplate;
import com.bins.code.generator.utils.ClassUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class EsServiceImplModel extends BaseModel implements ITemplate {

    private static final EsServiceImplModel model = new EsServiceImplModel();

    public EsServiceImplModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    private EsServiceImplModel() {
        super();
    }

    public static EsServiceImplModel getInstance() {
        return model;
    }

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride;

    @Override
    public boolean isFileOverride() {
        return fileOverride;
    }

    /**
     * ServiceImpl带包名父类全称
     */
    private String superClass = Constant.SUPER_SERVICE_IMPL_CLASS;

    private String superServiceImplClassPackage = superClass;

    private String superServiceImplClass = ClassUtils.getSimpleName(this.superClass);

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    @Override
    public Map<String, Object> renderData(TableInfo tableInfo) {
        return null;
    }

}
