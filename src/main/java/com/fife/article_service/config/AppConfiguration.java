package com.fife.article_service.config;

import com.fife.article_service.properties.OpenApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OpenApiProperties.class})
public class AppConfiguration {}
