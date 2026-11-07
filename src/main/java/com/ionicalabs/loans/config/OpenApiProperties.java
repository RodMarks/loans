package com.ionicalabs.loans.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.openapi")
public record OpenApiProperties(
        String title,
        String description,
        String version,
        String url,
        Contact contact,
        License license
) {
    public record Contact(String name, String email) {
    }

    public record License(String name) {
    }
}
