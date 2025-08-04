package com.bins.code.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.bins.code.generator.config.builder.BaseBuilder;
import com.bins.code.generator.config.generator.AuthUtilsGeneratorStrategy;
import com.bins.code.generator.config.generator.BusinessExceptionGeneratorStrategy;
import com.bins.code.generator.config.generator.ConstantsGeneratorStrategy;
import com.bins.code.generator.config.generator.ControllerGeneratorStrategy;
import com.bins.code.generator.config.generator.DtoGeneratorStrategy;
import com.bins.code.generator.config.generator.EntityGeneratorStrategy;
import com.bins.code.generator.config.generator.ErrorCodeGeneratorStrategy;
import com.bins.code.generator.config.generator.EsEntityGeneratorStrategy;
import com.bins.code.generator.config.generator.EsServiceGeneratorStrategy;
import com.bins.code.generator.config.generator.EsServiceImplGeneratorStrategy;
import com.bins.code.generator.config.generator.MapperGeneratorStrategy;
import com.bins.code.generator.config.generator.MapperXmlGeneratorStrategy;
import com.bins.code.generator.config.generator.PageDtoGeneratorStrategy;
import com.bins.code.generator.config.generator.ResultBodyGeneratorStrategy;
import com.bins.code.generator.config.generator.SaveDtoGeneratorStrategy;
import com.bins.code.generator.config.generator.ServiceGeneratorStrategy;
import com.bins.code.generator.config.generator.ServiceImplGeneratorStrategy;
import com.bins.code.generator.config.generator.ValidGroupGeneratorStrategy;
import com.bins.code.generator.config.generator.VoGeneratorStrategy;
import com.bins.code.generator.config.generator.WebApiGeneratorStrategy;
import com.bins.code.generator.config.generator.WebPageGeneratorStrategy;
import com.bins.code.generator.config.generator.WebRouterGeneratorStrategy;
import com.bins.code.generator.strategy.IGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StrategyConfig {

    private StrategyConfig() {
    }

    /**
     * 是否大写命名（默认 false）
     */
    private boolean isCapitalMode;

    /**
     * 过滤表前缀
     * example: addTablePrefix("t_")
     * result: t_simple -> Simple
     */
    private final Set<String> tablePrefix = new HashSet<>();

    /**
     * 过滤表后缀
     * example: addTableSuffix("_0")
     * result: t_simple_0 -> Simple
     */
    private final Set<String> tableSuffix = new HashSet<>();

    /**
     * 过滤字段前缀
     * example: addFieldPrefix("is_")
     * result: is_deleted -> deleted
     */
    private final Set<String> fieldPrefix = new HashSet<>();

    /**
     * 过滤字段后缀
     * example: addFieldSuffix("_flag")
     * result: deleted_flag -> deleted
     */
    private final Set<String> fieldSuffix = new HashSet<>();

    /**
     * 需要包含的表名，允许正则表达式（与exclude二选一配置）<br/>
     * 当{@link #enableSqlFilter}为true时，正则表达式无效.
     */
    private final Set<String> include = new HashSet<>();

    /**
     * 需要排除的表名，允许正则表达式<br/>
     * 当{@link #enableSqlFilter}为true时，正则表达式无效.
     */
    private final Set<String> exclude = new HashSet<>();

    /**
     * 启用sql过滤，语法不能支持使用sql过滤表的话，可以考虑关闭此开关.
     */
    private boolean enableSqlFilter = true;

    /**
     * 启用 schema 默认 false
     */
    private boolean enableSchema;

    public EntityGeneratorStrategy entityTemplateStrategy;

    public EntityGeneratorStrategy getEntityTemplateStrategy() {
        return entityTemplateStrategy;
    }

    public void setEntityTemplateStrategy(EntityGeneratorStrategy entityTemplateStrategy) {
        this.entityTemplateStrategy = entityTemplateStrategy;
    }

    public final EntityGeneratorStrategy.Builder entityBuilder = new EntityGeneratorStrategy.Builder(this);

    private final DtoGeneratorStrategy.Builder dtoBuilder = new DtoGeneratorStrategy.Builder(this);

    private final SaveDtoGeneratorStrategy.Builder saveDtoBuilder = new SaveDtoGeneratorStrategy.Builder(this);

    private final VoGeneratorStrategy.Builder voBuilder = new VoGeneratorStrategy.Builder(this);

    private final ControllerGeneratorStrategy.Builder controllerBuilder = new ControllerGeneratorStrategy.Builder(this);

    private final MapperGeneratorStrategy.Builder mapperBuilder = new MapperGeneratorStrategy.Builder(this);

    private final MapperXmlGeneratorStrategy.Builder mapperXmlBuilder = new MapperXmlGeneratorStrategy.Builder(this);

    private final ServiceGeneratorStrategy.Builder serviceBuilder = new ServiceGeneratorStrategy.Builder(this);

    private final ServiceImplGeneratorStrategy.Builder serviceImplBuilder = new ServiceImplGeneratorStrategy.Builder(this);

    public final EsEntityGeneratorStrategy.Builder esEntityBuilder = new EsEntityGeneratorStrategy.Builder(this);

    private final EsServiceGeneratorStrategy.Builder esServiceBuilder = new EsServiceGeneratorStrategy.Builder(this);

    private final EsServiceImplGeneratorStrategy.Builder esServiceImplBuilder = new EsServiceImplGeneratorStrategy.Builder(this);

    private final PageDtoGeneratorStrategy.Builder pageDtoBuilder = new PageDtoGeneratorStrategy.Builder(this);

    private final ConstantsGeneratorStrategy.Builder constantsBuilder = new ConstantsGeneratorStrategy.Builder(this);

    private final AuthUtilsGeneratorStrategy.Builder authUtilsBuilder = new AuthUtilsGeneratorStrategy.Builder(this);

    private final BusinessExceptionGeneratorStrategy.Builder businessExceptionBuilder = new BusinessExceptionGeneratorStrategy.Builder(this);

    private final ErrorCodeGeneratorStrategy.Builder errorCodeBuilder = new ErrorCodeGeneratorStrategy.Builder(this);

    private final ResultBodyGeneratorStrategy.Builder resultBodyBuilder = new ResultBodyGeneratorStrategy.Builder(this);

    private final ValidGroupGeneratorStrategy.Builder validGroupsBuilder = new ValidGroupGeneratorStrategy.Builder(this);

    private final WebPageGeneratorStrategy.Builder webPageBuilder = new WebPageGeneratorStrategy.Builder(this);

    private final WebRouterGeneratorStrategy.Builder webRouterBuilder = new WebRouterGeneratorStrategy.Builder(this);

    private final WebApiGeneratorStrategy.Builder webApiBuilder = new WebApiGeneratorStrategy.Builder(this);

    private List<IGenerator> generatorList = new ArrayList<>();

    public void addGenerator(IGenerator generator) {
        generatorList.add(generator);
    }

    public List<IGenerator> getGeneratorList() {
        return generatorList;
    }

    /**
     * 实体配置构建者
     *
     * @return 实体配置构建者
     */
    public EntityGeneratorStrategy.Builder entityBuilder() {
        return entityBuilder;
    }

    /**
     * dto配置构建者
     *
     * @return dto配置构建者
     */
    public DtoGeneratorStrategy.Builder dtoBuilder() {
        return dtoBuilder;
    }

    /**
     * dto配置构建者
     *
     * @return dto配置构建者
     */
    public SaveDtoGeneratorStrategy.Builder saveDtoBuilder() {
        return saveDtoBuilder;
    }

    /**
     * vo配置构建者
     *
     * @return vo配置构建者
     */
    public VoGeneratorStrategy.Builder voBuilder() {
        return voBuilder;
    }

    /**
     * 控制器配置构建者
     *
     * @return 控制器配置构建者
     */
    public ControllerGeneratorStrategy.Builder controllerBuilder() {
        return controllerBuilder;
    }

    /**
     * Mapper配置构建者
     *
     * @return Mapper配置构建者
     */
    public MapperGeneratorStrategy.Builder mapperBuilder() {
        return mapperBuilder;
    }

    /**
     * MapperXml配置构建者
     *
     * @return MapperXml配置构建者
     */
    public MapperXmlGeneratorStrategy.Builder mapperXmlBuilder() {
        return mapperXmlBuilder;
    }

    /**
     * Service配置构建者
     *
     * @return Service配置构建者
     */
    public ServiceGeneratorStrategy.Builder serviceBuilder() {
        return serviceBuilder;
    }

    /**
     * Service配置构建者
     *
     * @return Service配置构建者
     */
    public ServiceImplGeneratorStrategy.Builder serviceImplBuilder() {
        return serviceImplBuilder;
    }

    /**
     * 实体配置构建者
     *
     * @return 实体配置构建者
     */
    public EsEntityGeneratorStrategy.Builder esEntityBuilder() {
        return esEntityBuilder;
    }

    /**
     * Service配置构建者
     *
     * @return Service配置构建者
     */
    public EsServiceGeneratorStrategy.Builder esServiceBuilder() {
        return esServiceBuilder;
    }

    /**
     * Service配置构建者
     *
     * @return Service配置构建者
     */
    public EsServiceImplGeneratorStrategy.Builder esServiceImplBuilder() {
        return esServiceImplBuilder;
    }

    /**
     * pageDto配置构建者
     *
     * @return pageDto配置构建者
     */
    public PageDtoGeneratorStrategy.Builder pageDtoBuilder() {
        return pageDtoBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public ConstantsGeneratorStrategy.Builder constantsBuilder() {
        return constantsBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public AuthUtilsGeneratorStrategy.Builder authUtilsBuilder() {
        return authUtilsBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public BusinessExceptionGeneratorStrategy.Builder businessExceptionBuilder() {
        return businessExceptionBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public ErrorCodeGeneratorStrategy.Builder errorCodeBuilder() {
        return errorCodeBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public ResultBodyGeneratorStrategy.Builder resultBodyBuilder() {
        return resultBodyBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public ValidGroupGeneratorStrategy.Builder validGroupsBuilder() {
        return validGroupsBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public WebRouterGeneratorStrategy.Builder webRouterBuilder() {
        return webRouterBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public WebPageGeneratorStrategy.Builder webPageBuilder() {
        return webPageBuilder;
    }

    /**
     * constants配置构建者
     *
     * @return constants配置构建者
     */
    public WebApiGeneratorStrategy.Builder webApiBuilder() {
        return webApiBuilder;
    }

    /**
     * 大写命名、字段符合大写字母数字下划线命名
     *
     * @param word 待判断字符串
     */
    public boolean isCapitalModeNaming(String word) {
        return isCapitalMode && StringUtils.isCapitalMode(word);
    }

    /**
     * 表名称匹配过滤表前缀
     *
     * @param tableName 表名称
     */
    public boolean startsWithTablePrefix(String tableName) {
        return this.tablePrefix.stream().anyMatch(tableName::startsWith);
    }

    /**
     * 验证配置项
     */
    public void validate() {
        boolean isInclude = this.getInclude().size() > 0;
        boolean isExclude = this.getExclude().size() > 0;
        if (isInclude && isExclude) {
            throw new IllegalArgumentException("<strategy> 标签中 <include> 与 <exclude> 只能配置一项！" );
        }
    }

    /**
     * 包含表名匹配
     *
     * @param tableName 表名
     * @return 是否匹配
     */
    public boolean matchIncludeTable(String tableName) {
        return matchTable(tableName, this.getInclude());
    }

    /**
     * 排除表名匹配
     *
     * @param tableName 表名
     * @return 是否匹配
     */
    public boolean matchExcludeTable(String tableName) {
        return matchTable(tableName, this.getExclude());
    }

    /**
     * 表名匹配
     *
     * @param tableName   表名
     * @param matchTables 匹配集合
     * @return 是否匹配
     */
    private boolean matchTable(String tableName, Set<String> matchTables) {
        return matchTables.stream().anyMatch(t -> tableNameMatches(t, tableName));
    }

    /**
     * 表名匹配
     *
     * @param matchTableName 匹配表名
     * @param dbTableName    数据库表名
     * @return 是否匹配
     */
    private boolean tableNameMatches(String matchTableName, String dbTableName) {
        return matchTableName.equalsIgnoreCase(dbTableName) || StringUtils.matches(matchTableName, dbTableName);
    }

    public boolean isCapitalMode() {
        return isCapitalMode;
    }

    public Set<String> getTablePrefix() {
        return tablePrefix;
    }

    public Set<String> getTableSuffix() {
        return tableSuffix;
    }

    public Set<String> getFieldPrefix() {
        return fieldPrefix;
    }

    public Set<String> getFieldSuffix() {
        return fieldSuffix;
    }

    public Set<String> getInclude() {
        return include;
    }

    public Set<String> getExclude() {
        return exclude;
    }

    public boolean isEnableSqlFilter() {
        return enableSqlFilter;
    }

    public boolean isEnableSchema() {
        return enableSchema;
    }

    public static class Builder extends BaseBuilder {

        private final StrategyConfig strategyConfig;

        public Builder() {
            super(new StrategyConfig());
            strategyConfig = super.build();
        }

        @Override
        public StrategyConfig build() {
            this.strategyConfig.validate();
            return this.strategyConfig;
        }

        /**
         * 开启大写命名
         *
         * @return this
         */
        public Builder enableCapitalMode() {
            this.strategyConfig.isCapitalMode = true;
            return this;
        }

        /**
         * 增加过滤表前缀
         *
         * @param tablePrefix 过滤表前缀
         * @return this
         */
        public Builder addTablePrefix(String... tablePrefix) {
            return addTablePrefix(Arrays.asList(tablePrefix));
        }

        public Builder addTablePrefix(List<String> tablePrefixList) {
            this.strategyConfig.tablePrefix.addAll(tablePrefixList);
            return this;
        }

        /**
         * 增加过滤表后缀
         *
         * @param tableSuffix 过滤表后缀
         * @return this
         */
        public Builder addTableSuffix(String... tableSuffix) {
            return addTableSuffix(Arrays.asList(tableSuffix));
        }

        public Builder addTableSuffix(List<String> tableSuffixList) {
            this.strategyConfig.tableSuffix.addAll(tableSuffixList);
            return this;
        }

        /**
         * 增加包含的表名
         *
         * @param include 包含表
         * @return this
         */
        public Builder addInclude(String... include) {
            this.strategyConfig.include.addAll(Arrays.asList(include));
            return this;
        }

        public Builder addInclude(List<String> includes) {
            this.strategyConfig.include.addAll(includes);
            return this;
        }

        public Builder addInclude(String include) {
            this.strategyConfig.include.addAll(Arrays.asList(include.split("," )));
            return this;
        }

    }
}
