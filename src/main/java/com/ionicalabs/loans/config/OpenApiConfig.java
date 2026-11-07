package com.ionicalabs.loans.config;

import com.ionicalabs.loans.controllers.dto.ErrorResponseDto;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
public class OpenApiConfig {

    private static final String ERROR_SCHEMA = "ErrorResponseDto";

    @Bean
    public OpenAPI openApi(OpenApiProperties properties) {
        return new OpenAPI()
                .info(new Info()
                        .title(properties.title())
                        .description(properties.description())
                        .version(properties.version())
                        .contact(new Contact()
                                .name(properties.contact().name())
                                .email(properties.contact().email())
                                .url(properties.url()))
                        .license(new License()
                                .name(properties.license().name())
                                .url(properties.url())))
                .externalDocs(new ExternalDocumentation()
                        .description(properties.description())
                        .url(properties.url()));
    }

    @Bean
    public OpenApiCustomizer defaultErrorResponsesCustomizer() {
        return openApi -> {
            ModelConverters.getInstance()
                    .resolveAsResolvedSchema(new AnnotatedType(ErrorResponseDto.class))
                    .referencedSchemas
                    .forEach(openApi.getComponents()::addSchemas);

            openApi.getPaths().values().forEach(pathItem ->
                    pathItem.readOperations().forEach(operation -> {
                        operation.getResponses().computeIfAbsent("400", code -> errorResponse("Bad request"));
                        operation.getResponses().computeIfAbsent("500", code -> errorResponse("Internal server error"));
                    }));
        };
    }

    private ApiResponse errorResponse(String description) {
        return new ApiResponse()
                .description(description)
                .content(new Content().addMediaType(
                        org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
                        new MediaType().schema(new Schema<>().$ref("#/components/schemas/" + ERROR_SCHEMA))));
    }
}
