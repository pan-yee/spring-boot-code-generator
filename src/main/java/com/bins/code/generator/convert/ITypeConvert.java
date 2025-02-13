package com.bins.code.generator.convert;

import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.rule.IColumnType;
import com.bins.code.generator.model.po.TableField;

public interface ITypeConvert {

    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param tableField   字段列信息
     * @return ignore
     */
    default IColumnType processTypeConvert(GlobalConfig globalConfig, TableField tableField) {
        return processTypeConvert(globalConfig, tableField.getType());
    }

    /**
     * 执行类型转换
     *
     * @param globalConfig 全局配置
     * @param fieldType    字段类型
     * @return ignore
     */
    IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType);

}
