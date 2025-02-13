package com.bins.code.generator.model;

import java.util.Set;

public interface IModel {

    void setImportPackages(Set<String> importPackages);

    void initCommonConfig();

    void initCommonPackage();
}
