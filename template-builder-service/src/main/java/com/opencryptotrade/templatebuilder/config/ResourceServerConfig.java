package com.opencryptotrade.templatebuilder.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.opencryptotrade.templatebuilder.dto.BaseBlockLinkDTO;
import com.opencryptotrade.templatebuilder.entity.BaseBlockLink;
import com.opencryptotrade.templatebuilder.entity.Folder;
import com.opencryptotrade.templatebuilder.security.CustomUserInfoTokenServices;
import feign.RequestInterceptor;
import org.bson.types.ObjectId;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerProperties sso;

    @Autowired
    public ResourceServerConfig(ResourceServerProperties sso) {
        this.sso = sso;
    }

    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
        return new ClientCredentialsResourceDetails();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        // Converter any ObjectId field to field id.
        modelMapper.addConverter(new AbstractConverter<ObjectId, String>() {
            @Override
            protected String convert(ObjectId source) {
                return source == null ? null : source.toString();
            }
        });

        // Converter Folder object to folder id field.
        modelMapper.addConverter(new AbstractConverter<Folder, String>() {
            @Override
            protected String convert(Folder source) {
                return source == null ? null : source.getId().toString();
            }
        });

        // BaseBlockLink converters.
        modelMapper.typeMap(BaseBlockLink.class, BaseBlockLinkDTO.class).addMappings(mapper -> {
            Converter<BaseBlockLink, String> converter = new AbstractConverter<>() {
                @Override
                protected String convert(BaseBlockLink sourceArg) {
                    return sourceArg.getBaseBlockCopy() == null ? sourceArg.getBaseBlock().getHtml() : sourceArg.getBaseBlockCopy().getHtml();
                }
            };

            mapper.using(converter).map(source -> source, (dest, v) -> dest.setHtml((String) v));
        });
        return modelMapper;
    }

    @Bean
    Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS)
//                .featuresToEnable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                .findModulesViaServiceLoader(true);
    }

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(){
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
    }

    @Bean
    public OAuth2RestTemplate clientCredentialsRestTemplate() {
        return new OAuth2RestTemplate(clientCredentialsResourceDetails());
    }

    @Bean
    public ResourceServerTokenServices tokenServices() {
        return new CustomUserInfoTokenServices(sso.getUserInfoUri(), sso.getClientId());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated();
    }

}
