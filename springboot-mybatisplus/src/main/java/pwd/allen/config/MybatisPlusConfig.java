package pwd.allen.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @MapperScan注解引入了 {@link org.mybatis.spring.mapper.MapperScannerConfigurer} 会在容器初始化的时候扫描包下mapper并注册到容器中
 * 最后实例化时由 {@link org.mybatis.spring.mapper.MapperFactoryBean} 调用{@link org.mybatis.spring.SqlSessionTemplate}的getMapper获取jdk动态代理后的代理对象
 *
 *
 * @author 门那粒沙
 * @create 2021-04-18 16:13
 */
@MapperScan("pwd.allen.mapper")
@Configuration
public class MybatisPlusConfig {


    /**
     * mybatis封装的拦截器/插件，用于集成其他内部的拦截器，例如：分页、监控、
     * 只需加入spring容器中即可被自动装配 {@link com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration}
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(List<PaginationInnerInterceptor> innerInterceptorList) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        for (PaginationInnerInterceptor paginationInnerInterceptor : innerInterceptorList) {
            interceptor.addInnerInterceptor(paginationInnerInterceptor);
        }
        return interceptor;
    }

    /**
     * 分页插件，不加的话selectPage等分页方法没有分页效果
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
//        paginationInnerInterceptor.setDbType(DbType.ORACLE_12C);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInnerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，新版是setLimit()，默认500条-1不受限制；新版为setMaxLimit，默认不限制
        paginationInnerInterceptor.setMaxLimit(500L);
        return paginationInnerInterceptor;
    }

    /**
     * 自定义公共字段填充处理器
     * @return
     */
    @Bean
    public MyMetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
    }

    /**
     * 自定义jackson配置
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(){
        return builder -> {
            // 配置enum类型返回值使用toString方法
            // 默认情况：取enum类型中@JsonValue标注的字段，若没有指定，则取enum的name
            builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        };
    }
}
