package com.bins.code.generator.enums;

/**
 * 文件类型
 */
public enum FileTypeEnum {

    JAVA("java", "java文件"),
    XML("xml", "xml文件"),
    TS("ts", "ts文件");

    /**
     * 文件编码
     */
    private final String code;

    /**
     * 文件名称
     */
    private final String name;

    FileTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

}
