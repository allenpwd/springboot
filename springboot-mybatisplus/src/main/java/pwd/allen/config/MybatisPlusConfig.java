package pwd.allen.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置分页插件
 * @author 门那粒沙
 * @create 2021-04-18 16:13
 */
@MapperScan("pwd.allen.mapper")
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件，不加的话selectPage等分页方法没有分页效果
     * 只需加入spring容器中即可被自动装配
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 对于单一数据库类型来说,都建议配置该值,避免每次分页都去抓取数据库类型
//        paginationInnerInterceptor.setDbType(DbType.ORACLE_12C);
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInnerInterceptor.setOverflow(false);
        // 设置最大单页限制数量，新版是setLimit()，默认500条-1不受限制；新版为setMaxLimit，默认不限制
//        interceptor.setMaxLimit(500L);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
