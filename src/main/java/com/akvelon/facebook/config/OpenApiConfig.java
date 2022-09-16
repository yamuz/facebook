package com.akvelon.facebook.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.akvelon.facebook.config.AppConstants.AUTHENTICATION_URL;
import static com.akvelon.facebook.config.AppConstants.USERNAME_PARAMETER;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_AUTH_SCHEMA_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .addSecurityItem(buildSecurity())
                .paths(buildAuthenticationPath())
                .paths(buildPostPath())
                .components(buildComponents())
                .info(buildInfo());
    }

    private Paths buildAuthenticationPath() {
        return new Paths()
                .addPathItem(AUTHENTICATION_URL, buildAuthenticationPathItem());
    }

    private Paths buildPostPath() {
        return new Paths()
                .addPathItem("/api/posts/save", buildPostsPathItem());
    }

    private PathItem buildPostsPathItem() {
        return new PathItem().post( new Operation()
                        .addTagsItem("Операции с постами")
                        .requestBody(buildPostRequestBody())
                        .responses(buildPostResponses()));
    }

    private PathItem buildAuthenticationPathItem() {
        return new PathItem().post( new Operation()
                        .addTagsItem("Authentication")
                        .requestBody(buildAuthenticationRequestBody())
                        .responses(buildAuthenticationResponses()));

    }

    private RequestBody buildPostRequestBody() {
        return new RequestBody().content(
                new Content()
                        .addMediaType("multipart/form-data",
                                new MediaType()
                                        .schema(new Schema<>().$ref("postsParams"))));
    }

    private ApiResponses buildPostResponses() {
        return new ApiResponses()
                .addApiResponse("200",
                        new ApiResponse()
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("postsResponse")))));
    }


    private RequestBody buildAuthenticationRequestBody() {
        return new RequestBody().content(
                new Content()
                        .addMediaType("application/x-www-form-urlencoded",
                                new MediaType()
                                        .schema(new Schema<>()
                                                .$ref("EmailAndPassword"))));
    }

    private ApiResponses buildAuthenticationResponses() {
        return new ApiResponses()
                .addApiResponse("200",
                        new ApiResponse()
                                .content(new Content()
                                        .addMediaType("application/json",
                                                new MediaType()
                                                        .schema(new Schema<>()
                                                                .$ref("Tokens")))));
    }

    private SecurityRequirement buildSecurity() {
        return new SecurityRequirement().addList(BEARER_AUTH_SCHEMA_NAME);
    }

    private Info buildInfo() {
        return new Info().title("Facebook Service API").version("1.0");
    }

    private Components buildComponents() {
        Schema<?> emailAndPassword = new Schema<>()
                .type("object")
                .description("Email и пароль пользователя")
                .addProperties(USERNAME_PARAMETER, new Schema<>().type("string"))
                .addProperties("password", new Schema<>().type("string"));

        Schema<?> tokens = new Schema<>()
                .type("object")
                .description("Access и Refresh токены")
                .addProperties("accessToken", new Schema<>().type("string").description("Токен доступа"))
                .addProperties("refreshToken", new Schema<>().type("string").description("Токен для обновления"));

        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Schema<?> postsParams = new Schema<>()
                .type("object")
                .description("добавление поста")
                .addProperties("mediaStream", new Schema<>().type("string").format("binary"));

        Schema<?> postsResponse = new Schema<>()
                .type("object")
                .description("ответ добавления поста")
                .addProperties("posts", new Schema<>().type("string"));

        return new Components()
                .addSchemas("EmailAndPassword", emailAndPassword)
                .addSchemas("Tokens", tokens)
                .addSchemas("postsParams", postsParams)
                .addSchemas("postsResponse", postsResponse)
                .addSecuritySchemes("bearerAuth", securityScheme);
    }


}
