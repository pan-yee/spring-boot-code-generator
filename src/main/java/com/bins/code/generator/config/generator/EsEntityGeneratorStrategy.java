package com.bins.code.generator.config.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.bins.code.generator.config.AbstractTemplateStrategy;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.convert.INameConvert;
import com.bins.code.generator.function.ConverterFileName;
import com.bins.code.generator.utils.GeneratorUtils;
import lombok.Builder;

@Builder
public class EsEntityGeneratorStrategy extends AbstractTemplateStrategy {

    public static class Builder extends BaseBuilder {

        private final EsEntityGeneratorStrategy strategy = new EsEntityGeneratorStrategy();

        public Builder(StrategyConfig strategyConfig) {
            super(strategyConfig);
            this.strategy.setNameConvert(new INameConvert.DefaultNameConvert(strategyConfig));
        }

        /**
         * 覆盖已有文件
         */
        public EsEntityGeneratorStrategy.Builder enableFileOverride() {
            this.strategy.setFileOverride(true);
            return this;
        }

        /**
         * 开启lombok模型
         */
        public EsEntityGeneratorStrategy.Builder enableLombok() {
            this.strategy.setLombok(true);
            return this;
        }

        /**
         * 开启swagger
         */
        public EsEntityGeneratorStrategy.Builder enableSwagger() {
            this.strategy.setSwagger(true);
            return this;
        }

        /**
         * 数据库表字段映射到实体的命名策略
         */
        public EsEntityGeneratorStrategy.Builder columnNaming(NamingStrategy namingStrategy) {
            this.strategy.setColumnNaming(namingStrategy);
            return this;
        }

        /**
         * 数据库表映射到实体的命名策略
         */
        public EsEntityGeneratorStrategy.Builder naming(NamingStrategy namingStrategy) {
            this.strategy.setNaming(namingStrategy);
            return this;
        }

        /**
         * 指定生成的主键的ID类型
         *
         * @param idType ID类型
         */
        public EsEntityGeneratorStrategy.Builder idType(IdType idType) {
            this.strategy.setIdType(idType);
            return this;
        }

        /**
         * 开启链式模型
         */
        public EsEntityGeneratorStrategy.Builder enableChainModel() {
            this.strategy.setChain(true);
            return this;
        }

        /**
         * 禁用生成serialVersionUID
         */
        public EsEntityGeneratorStrategy.Builder disableSerialVersionUID() {
            this.strategy.setSerialVersionUID(false);
            return this;
        }

        /**
         * 实体是否生成 TableField注解
         */
        public EsEntityGeneratorStrategy.Builder enableTableFieldAnnotation() {
            this.strategy.setTableFieldAnnotationEnable(true);
            return this;
        }

        /**
         * 格式化文件名称
         *
         * @param format 　格式
         * @return this
         */
        public EsEntityGeneratorStrategy.Builder formatFileName(String format) {
            String newFormat = GeneratorUtils.getDefaultFormat(format, "%sEntity");
            return convertFileName((entityName) -> String.format(newFormat, entityName));
        }

        /**
         * 转换输出文件名称
         *
         * @param converter 　转换处理
         */
        public EsEntityGeneratorStrategy.Builder convertFileName(ConverterFileName converter) {
            this.strategy.setConverterFileName(converter);
            return this;
        }

        public EsEntityGeneratorStrategy get() {
            return strategy;
        }
    }

}
