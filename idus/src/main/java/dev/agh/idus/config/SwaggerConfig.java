package dev.agh.idus.config;


import dev.agh.idus.config.filter.LoginFilter;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;

// 스프링 버전에 따라서 Swagger의 버전도 맞춰서 변경
@Configuration
public class SwaggerConfig {
//    @Bean
//    //수동으로 엔드포인트 스웨거 대상에 포함시키는 방법
//    public OpenApiCustomizer springSecurityLoginEndpointCustomizer(ApplicationContext applicationContext) {
//        FilterChainProxy springSecurityFilterChain = applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
//
//        return (openApi) -> {
//            for (SecurityFilterChain filterChain : springSecurityFilterChain.getFilterChains()) {
//                // 스프링 시큐리티의 특정 필터를 받아오는 부분
//                Optional<LoginFilter> filter = filterChain.getFilters().stream()
//                        .filter(LoginFilter.class::isInstance)
//                        .map(LoginFilter.class::cast)
//                        .findAny();
//                if(filter.isPresent()) {
//                    // 문서 설정 객체
//                    Operation operation = new Operation();
//
//                    // 문서에서 요청 설정
//                    Schema<?> schema = new ObjectSchema()
//                            .addProperty("email", new StringSchema())
//                            .addProperty("password", new StringSchema());
//                    RequestBody requestBody = new RequestBody().content(
//                            new Content().addMediaType("application/json", new MediaType().schema(schema)));
//
//                    operation.setRequestBody(requestBody);
//
//
//                    // 문서에서 응답 설정
//                    ApiResponses response = new ApiResponses();
//                    response.addApiResponse(
//                            String.valueOf(HttpStatus.OK.value()),
//                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase())
//                    );
//                    response.addApiResponse(
//                            String.valueOf(HttpStatus.BAD_REQUEST.value()),
//                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase())
//                    );
//                    operation.setResponses(response);
//
//                    // 직접 만든 필터의 문서를 swagger에 등록
//                    operation.addTagsItem("회원 기능");
//                    operation.summary("로그인 기능");
//                    PathItem pathItem = new PathItem().post(operation);
//                    openApi.getPaths().addPathItem("/login", pathItem);
//
//                }
//
//            }
//        };
//    }

    @Bean
    public OpenApiCustomizer securityCustomizer() {
        return openApi -> {
            // ✅ 로그인 엔드포인트 추가
            openApi.path("/login", new PathItem()
                    .post(new Operation()
                            .summary("Spring Security 로그인")
                            .description("Spring Security 기본 로그인 엔드포인트")
                            .tags(List.of("회원 기능"))
                            .requestBody(createLoginRequestBody())  // 요청 바디 추가
                    ));

            openApi.path("/logout", new PathItem()
                    .post(new io.swagger.v3.oas.models.Operation()
                            .summary("Spring Security 로그아웃")
                            .description("Spring Security 기본 로그아웃 엔드포인트")
                            .tags(List.of("회원 기능"))
                            .responses(new ApiResponses()
                                    .addApiResponse(
                                            String.valueOf(HttpStatus.OK.value()),
                                            new ApiResponse().description(HttpStatus.OK.getReasonPhrase())
                                    ).addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                                            new ApiResponse().description(HttpStatus.BAD_REQUEST.getReasonPhrase()))

                    )));

        };
    }

    // ✅ 로그인 요청(RequestBody) 정의
    private RequestBody createLoginRequestBody() {
        return new RequestBody()
                .description("로그인 요청 바디")
                .content(new Content()
                        .addMediaType("application/json", new MediaType()
                                .schema(new Schema<>()
                                        .type("object")
                                        .addProperty("username", new Schema<>().type("string"))
                                        .addProperty("password", new Schema<>().type("string"))
                                )));
    }


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("idus 프로젝트 api 문서")
                .description("idus 프로젝트")
                .version("1.0.0");
    }
}
