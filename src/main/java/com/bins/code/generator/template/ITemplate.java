package com.bins.code.generator.template;

import com.bins.code.generator.model.po.TableInfo;

import java.util.Map;

public interface ITemplate {

    Map<String, Object> renderData(TableInfo tableInfo);

}
