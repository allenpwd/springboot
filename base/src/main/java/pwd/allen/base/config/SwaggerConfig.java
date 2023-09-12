package pwd.allen.base.config;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 引入Knife4j提供的扩展类，需要开启knife4j增强才有，即knife4j.enable=true
     */
    @Autowired
    private OpenApiExtensionResolver openApiExtensionResolver;

    /**
     * 创建Docket存入容器，Docket代表一个接口文档
     */
    @Bean
    public Docket webApiConfig() {
        String groupName = "myGroup";
        //指定使用openAPI 3.0
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .select()
                // 指定@ApiOperation标注的接口被加入文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 添加另一个过滤条件，和上面是and的关系
                .apis(RequestHandlerSelectors.basePackage("pwd.allen.base.controller"))
                .build();
    }

    /**
     * 另起一个分组展示自定义文档
     * @return
     */
    @Bean
    public Docket webApiConfig2() {
        String groupName = "myGroup2";
        //指定使用openAPI 3.0
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
                // 创建接口文档的具体信息
                .apiInfo(webApiInfo())
                // 创建选择器，控制哪些接口被加入文档
                .select()
                .apis(RequestHandlerSelectors.none())
                .build()
                // 拓展指定的自定义文档到该docket配置中
                .extensions(openApiExtensionResolver.buildExtensions(groupName));
    }

    /**
     * 创建接口文档的具体信息，会显示在接口文档页面中
     * @return
     */
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                // 文档标题
                .title("springboot学习接口文档")
                // 文档描述
                .description("描述：本文档描述了springboot学习的接口定义")
                // 版本
                .version("1.0")
                // 联系人信息
                .contact(new Contact("门那粒沙", "https://gitee.com/allenpwd", "994266136@qq.com"))
                // 版权
                .license("allenpwd")
                // 版权地址
                .licenseUrl("https://gitee.com/allenpwd")
                .build();
    }
}
