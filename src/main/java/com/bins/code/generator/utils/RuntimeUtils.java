package com.bins.code.generator.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * 运行工具类
 *
 */
@Slf4j
public class RuntimeUtils {

    /**
     * 打开指定输出文件目录
     *
     * @param outDir 输出文件目录
     * @throws IOException
     */
    public static void openDir(String outDir) throws IOException {
        String osName = System.getProperty("os.name");
        if (osName != null) {
            if (osName.contains("Mac")) {
                Runtime.getRuntime().exec("open " + outDir);
            } else if (osName.contains("Windows")) {
                Runtime.getRuntime().exec(MessageFormat.format("cmd /c start \"\" \"{0}\"", outDir));
            } else {
                log.debug("文件输出目录:{}", outDir);
            }
        } else {
            log.warn("读取操作系统失败");
        }
    }
}
