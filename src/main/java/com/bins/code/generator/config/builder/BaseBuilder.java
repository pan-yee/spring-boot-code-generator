package com.bins.code.generator.config.builder;

import com.bins.code.generator.config.*;
import com.bins.code.generator.config.generator.*;
import com.bins.code.generator.strategy.impl.*;

public class BaseBuilder implements IConfigBuilder<StrategyConfig> {

    private final StrategyConfig strategyConfig;

    public BaseBuilder(StrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    public EntityGeneratorStrategy.Builder entityBuilder() {
        strategyConfig.addGenerator(EntityStrategyGenerator.getInstance());
        return strategyConfig.entityBuilder();
    }

    public DtoGeneratorStrategy.Builder dtoBuilder() {
        strategyConfig.addGenerator(DtoStrategyGenerator.getInstance());
        return strategyConfig.dtoBuilder();
    }

    public VoGeneratorStrategy.Builder voBuilder() {
        strategyConfig.addGenerator(VoStrategyGenerator.getInstance());
        return strategyConfig.voBuilder();
    }

    public MapperGeneratorStrategy.Builder mapperBuilder() {
        strategyConfig.addGenerator(MapperStrategyGenerator.getInstance());
        return strategyConfig.mapperBuilder();
    }

    public MapperXmlGeneratorStrategy.Builder mapperXmlBuilder() {
        strategyConfig.addGenerator(MapperXmlStrategyGenerator.getInstance());
        return strategyConfig.mapperXmlBuilder();
    }

    public ServiceGeneratorStrategy.Builder serviceBuilder() {
        strategyConfig.addGenerator(ServiceStrategyGenerator.getInstance());
        return strategyConfig.serviceBuilder();
    }

    public ServiceImplGeneratorStrategy.Builder serviceImplBuilder() {
        strategyConfig.addGenerator(ServiceImplStrategyGenerator.getInstance());
        return strategyConfig.serviceImplBuilder();
    }

    public ControllerGeneratorStrategy.Builder controllerBuilder() {
        strategyConfig.addGenerator(ControllerStrategyGenerator.getInstance());
        return strategyConfig.controllerBuilder();
    }

    public EsEntityGeneratorStrategy.Builder esEntityBuilder() {
        strategyConfig.addGenerator(EsEntityStrategyGenerator.getInstance());
        return strategyConfig.esEntityBuilder();
    }

    public EsServiceGeneratorStrategy.Builder esServiceBuilder() {
        strategyConfig.addGenerator(EsServiceGenerator.getInstance());
        return strategyConfig.esServiceBuilder();
    }

    public EsServiceImplGeneratorStrategy.Builder esServiceImplBuilder() {
        strategyConfig.addGenerator(EsServiceImplGenerator.getInstance());
        return strategyConfig.esServiceImplBuilder();
    }

    public WebPageGeneratorStrategy.Builder webPageBuilder() {
        strategyConfig.addGenerator(WebPageStrategyGenerator.getInstance());
        return strategyConfig.webPageBuilder();
    }

    public WebRouterGeneratorStrategy.Builder webRouterBuilder() {
        strategyConfig.addGenerator(WebRouterStrategyGenerator.getInstance());
        return strategyConfig.webRouterBuilder();
    }

    public WebApiGeneratorStrategy.Builder webApiBuilder() {
        strategyConfig.addGenerator(WebApiStrategyGenerator.getInstance());
        return strategyConfig.webApiBuilder();
    }

    @Override
    public StrategyConfig build() {
        this.strategyConfig.validate();
        return this.strategyConfig;
    }
}
