package pwd.allen.neo4j.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@Configuration
@EnableSwagger2WebMvc
// @Profile("dev") //只在dev环境中开启
public class Knife4jConfig {
    @Bean("WebApi")
    public Docket createApiDoc(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .groupName("neo4j接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("pwd.allen.neo4j.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder()
                .title("neo4j接口文档") // 标题
                .description("知识表达与处理期末项目") // 描述
                .termsOfServiceUrl("https://www.ihua.com/") // API 服务条款
                .contact(new Contact("门那粒沙", "https://www.ihua.com", "2759335003@qq.com")) // 联系人
                .version("1.0") // 版本号
                .build();
    }

    @Bean
    public GeneratedValue.UUIDGenerator uuidGenerator(){
        return new GeneratedValue.UUIDGenerator();
    }
}
