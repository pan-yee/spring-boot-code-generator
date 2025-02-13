package com.bins.code.generator.type;

import com.bins.code.generator.config.GlobalConfig;
import com.bins.code.generator.config.rule.IColumnType;
import com.bins.code.generator.model.po.TableField;

public interface ITypeConvertHandler {

    IColumnType convert(GlobalConfig globalConfig, TypeRegistry typeRegistry, TableField.MetaInfo metaInfo);

}
