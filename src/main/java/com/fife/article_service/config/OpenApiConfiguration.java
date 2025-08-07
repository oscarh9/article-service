package com.fife.article_service.config;

import com.fife.article_service.properties.OpenApiProperties;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

    private final OpenApiProperties openApiProperties;

    @Bean
    public GroupedOpenApi articleServiceOpenApi() {
        String[] packagesToScan = {"com.fife.article_service.controller"};
        return GroupedOpenApi.builder()
                .group("Article API")
                .packagesToScan(packagesToScan)
                .addOpenApiCustomizer(this.openApiCustomizer())
                .build();
    }

    private OpenApiCustomizer openApiCustomizer() {
        return openApi ->
                openApi.info(
                                new Info()
                                        .contact(
                                                new Contact()
                                                        .name(
                                                                this.openApiProperties
                                                                        .getInfo()
                                                                        .getContact()
                                                                        .getName())
                                                        .email(
                                                                this.openApiProperties
                                                                        .getInfo()
                                                                        .getContact()
                                                                        .getEmail())
                                                        .url(
                                                                this.openApiProperties
                                                                        .getInfo()
                                                                        .getContact()
                                                                        .getUrl()))
                                        .description(
                                                this.openApiProperties.getInfo().getDescription())
                                        .title(this.openApiProperties.getInfo().getTitle())
                                        .version(this.openApiProperties.getInfo().getVersion())
                                        .license(
                                                new License()
                                                        .name(
                                                                this.openApiProperties
                                                                        .getInfo()
                                                                        .getLicense()
                                                                        .getName())
                                                        .url(
                                                                this.openApiProperties
                                                                        .getInfo()
                                                                        .getLicense()
                                                                        .getUrl()))
                                        .termsOfService(
                                                this.openApiProperties
                                                        .getInfo()
                                                        .getTermsOfService()))
                        .servers(
                                List.of(
                                        new Server()
                                                .url(this.openApiProperties.getServer().getUrl())
                                                .description(
                                                        this.openApiProperties
                                                                .getServer()
                                                                .getDescription())));
    }
}
