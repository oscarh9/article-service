package com.fife.article_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "open-api-properties")
public class OpenApiProperties {

    private Info info;
    private Server server;

    @Data
    public static class Info {
        private String title;
        private String description;
        private String version;
        private Contact contact;
        private License license;
        private String termsOfService;
    }

    @Data
    public static class Contact {
        private String name;
        private String email;
        private String url;
    }

    @Data
    public static class License {
        private String name;
        private String url;
    }

    @Data
    public static class Server {
        private String description;
        private String url;
    }
}
