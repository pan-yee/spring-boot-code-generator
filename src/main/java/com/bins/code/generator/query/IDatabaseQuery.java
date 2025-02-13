package com.bins.code.generator.query;

import com.bins.code.generator.model.po.TableInfo;

import java.util.List;

public interface IDatabaseQuery {

    /**
     * 获取表信息
     *
     * @return 表信息
     */
    List<TableInfo> queryTables();

}
