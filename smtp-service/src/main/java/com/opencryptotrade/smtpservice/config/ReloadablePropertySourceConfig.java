package com.opencryptotrade.smtpservice.config;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

@Configuration
public class ReloadablePropertySourceConfig {

    private final ConfigurableEnvironment env;

    public ReloadablePropertySourceConfig(@Autowired ConfigurableEnvironment env) {
        this.env = env;
    }

    @Bean
    @ConditionalOnProperty(name = "spring.config.location")
    public ReloadablePropertySource reloadablePropertySource(@Autowired PropertiesConfiguration properties) {
        ReloadablePropertySource ret = new ReloadablePropertySource("dynamic", properties);
        MutablePropertySources sources = env.getPropertySources();
        sources.addFirst(ret);
        return ret;
    }

}
