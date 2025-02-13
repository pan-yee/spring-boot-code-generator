package com.bins.code.generator.model;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.constants.Constant;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.model.po.TableInfo;
import com.bins.code.generator.template.ITemplate;
import com.bins.code.generator.utils.ClassUtils;
import lombok.Data;

import java.util.Map;

@Data
public class MapperModel extends BaseModel implements ITemplate {

    private static final MapperModel model = new MapperModel();

    private MapperModel() {
        super();
    }

    public MapperModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    public static MapperModel getInstance() {
        return model;
    }

    private String mapperName;

    /**
     * Mapper带包名父类全称
     */
    private String superClass = Constant.SUPER_MAPPER_CLASS;

    private String superMapperClassPackage = superClass;

    private String superMapperClass = ClassUtils.getSimpleName(this.superClass);

    /**
     * 是否覆盖已有文件（默认 false）
     */
    private boolean fileOverride;

    public boolean isFileOverride() {
        return fileOverride;
    }

    /**
     * 数据库表映射到实体的命名策略，默认下划线转驼峰命名
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 数据库表字段映射到实体的命名策略
     * <p>未指定按照 naming 执行</p>
     */
    private NamingStrategy columnNaming = null;

    public NamingStrategy getNaming() {
        return naming;
    }

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);

    @Override
    public Map<String, Object> renderData(TableInfo tableInfo) {
        return null;
    }

}
