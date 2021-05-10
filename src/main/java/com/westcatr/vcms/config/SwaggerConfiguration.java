package com.westcatr.vcms.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.westcatr.rd.base.bweb.BWebConfig.PATH_LIST;

/**
 * @author xieshuang
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfiguration {

    static {
        PATH_LIST.add("com.westcatr.vcms.controller");
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(basePackage(PATH_LIST))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("智能化展厅接口文档").contact(new Contact("admin",
                        "http://git.cqeips.com/gxq-relocation/gxq-relocation-back", "1312544013@qq.com"))
                .version("1.0").build();
    }

    public static Predicate<RequestHandler> basePackage(List<String> stringList) {
        return input -> declaringClass(input).transform(handlerPackage(stringList)).or(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(List<String> stringList) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : stringList) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}