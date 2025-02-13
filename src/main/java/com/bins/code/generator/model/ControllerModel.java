package com.bins.code.generator.model;

import com.bins.code.generator.config.builder.ConfigBuilder;
import com.bins.code.generator.function.ConverterFileName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class ControllerModel extends BaseModel {

    public ControllerModel(ConfigBuilder configBuilder) {
        super(configBuilder);
    }

    private static final ControllerModel model = new ControllerModel();

    private ControllerModel() {
        super();
    }

    public static ControllerModel getInstance() {
        return model;
    }

    private String controllerName;

    /**
     * REST模式
     */
    private boolean restControllerStyle;

    /**
     * 转换输出文件名称
     */
    private ConverterFileName converterFileName = (entityName -> entityName);
}
