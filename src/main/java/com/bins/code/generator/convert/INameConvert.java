package com.bins.code.generator.convert;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.StrategyConfig;
import com.bins.code.generator.config.rule.NamingStrategy;
import com.bins.code.generator.model.po.TableField;
import com.bins.code.generator.model.po.TableInfo;

import java.util.Set;

/**
 * 名称转换接口类
 */
public interface INameConvert {

    /**
     * 执行实体名称转换
     *
     * @param tableInfo 表信息对象
     * @return
     */
    String entityNameConvert(TableInfo tableInfo);

    /**
     * 执行实体名称转换
     *
     * @param tableInfo 表信息对象
     * @return
     */
    String originNameConvert(TableInfo tableInfo);

    /**
     * 执行属性名称转换
     *
     * @param field 表字段对象，如果属性表字段命名不一致注意 convert 属性的设置
     * @return
     */
    String propertyNameConvert(TableField field);

    /**
     * 默认名称转换接口类
     */
    class DefaultNameConvert implements INameConvert {

        private final StrategyConfig strategyConfig;

        public DefaultNameConvert(StrategyConfig strategyConfig) {
            this.strategyConfig = strategyConfig;
        }

        @Override
        public String entityNameConvert(TableInfo tableInfo) {
            return NamingStrategy.capitalFirst(processName(tableInfo.getName(), strategyConfig.esEntityBuilder.get().getNaming(), strategyConfig.getTablePrefix(), strategyConfig.getTableSuffix()));
        }

        @Override
        public String originNameConvert(TableInfo tableInfo) {
            return processName(tableInfo.getName(), strategyConfig.esEntityBuilder.get().getNaming(), strategyConfig.getTablePrefix(), strategyConfig.getTableSuffix());
        }

        @Override
        public String propertyNameConvert(TableField field) {
            return processName(field.getName(), strategyConfig.esEntityBuilder.get().getColumnNaming(), strategyConfig.getFieldPrefix(), strategyConfig.getFieldSuffix());
        }

        private String processName(String name, NamingStrategy strategy, Set<String> prefix, Set<String> suffix) {
            String propertyName = name;
            if (prefix.size() > 0) {
                propertyName = NamingStrategy.removePrefix(propertyName, prefix);
            }
            if (suffix.size() > 0) {
                propertyName = NamingStrategy.removeSuffix(propertyName, suffix);
            }
            if (StringUtils.isBlank(propertyName)) {
                throw new RuntimeException(String.format("%s 的名称转换结果为空，请检查是否配置问题", name));
            }
            if (NamingStrategy.underline_to_camel.equals(strategy)) {
                return NamingStrategy.underlineToCamel(propertyName);
            }
            return propertyName;
        }
    }
}
