package com.bins.code.generator.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.io.File;

public class GeneratorUtils {

    public static String getDefaultFormat(String format, String defaultFormat) {
        if (ObjectUtils.isEmpty(format)) {
            return defaultFormat;
        }
        return format;
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String getTsFilePath() {
        String projectPath = System.getProperty("user.dir");
        return projectPath + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "ts";
    }

}
