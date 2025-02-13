package com.bins.code.generator.enums;

/**
 * 模板类型
 */
public enum TemplateTypeEnum {

    EMPTY(0, "空模板"),
    GENERAL(1, "通用模板"),
    COMMON(2, "公用模板");

    /**
     * 文件编码
     */
    private final int code;

    /**
     * 文件名称
     */
    private final String name;

    TemplateTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static boolean isCommon(int templateType) {
        if (TemplateTypeEnum.COMMON.code == templateType) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static boolean isGeneral(int templateType) {
        if (TemplateTypeEnum.GENERAL.code == templateType) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
