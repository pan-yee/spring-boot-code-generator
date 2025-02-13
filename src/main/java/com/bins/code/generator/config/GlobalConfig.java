package com.bins.code.generator.config;

import com.bins.code.generator.config.rule.DateType;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

@Slf4j
public class GlobalConfig {

    private GlobalConfig() {
    }

    /**
     * 生成文件的输出目录【 windows:D://  linux or mac:/tmp 】
     */
    private String outputDir = System.getProperty("os.name").toLowerCase().contains("windows") ? "D://" : "/tmp";

    /**
     * 是否打开输出目录
     */
    private boolean open = true;

    /**
     * 作者
     */
    private String author = "bins";

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.ONLY_DATE;

    /**
     * 获取注释日期
     */
    private Supplier<String> commentDate = () -> new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    public String getOutputDir() {
        return outputDir;
    }

    public boolean isOpen() {
        return open;
    }

    public String getAuthor() {
        return author;
    }

    public DateType getDateType() {
        return dateType;
    }

    public String getCommentDate() {
        return commentDate.get();
    }

    public static class Builder implements IConfigBuilder<GlobalConfig> {

        private final GlobalConfig globalConfig;

        public Builder() {
            this.globalConfig = new GlobalConfig();
        }

        /**
         * 禁止打开输出目录
         */
        public GlobalConfig.Builder disableOpenDir() {
            this.globalConfig.open = false;
            return this;
        }

        /**
         * 输出目录
         */
        public GlobalConfig.Builder outputDir(String outputDir) {
            this.globalConfig.outputDir = outputDir;
            return this;
        }

        /**
         * 作者
         */
        public GlobalConfig.Builder author(String author) {
            this.globalConfig.author = author;
            return this;
        }

        /**
         * 时间类型对应策略
         */
        public GlobalConfig.Builder dateType(DateType dateType) {
            this.globalConfig.dateType = dateType;
            return this;
        }

        /**
         * 注释日期获取处理
         * example: () -> LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
         */
        public GlobalConfig.Builder commentDate(Supplier<String> commentDate) {
            this.globalConfig.commentDate = commentDate;
            return this;
        }

        /**
         * 指定注释日期格式化
         *
         * @param pattern 格式
         * @return this
         */
        public GlobalConfig.Builder commentDate(String pattern) {
            return commentDate(() -> new SimpleDateFormat(pattern).format(new Date()));
        }

        @Override
        public GlobalConfig build() {
            return this.globalConfig;
        }
    }
}
