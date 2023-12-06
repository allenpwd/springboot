package pwd.allen.base.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pwd.allen.base.annotation.IgnoreResponseAdvice;
import pwd.allen.base.entity.MyResult;

/**
 * {@link ResponseBodyAdvice}
 *  用途：对RestController的响应进行拦截处理（在响应被{@link HttpMessageConverter}处理之前）
 *  用法：需要把实现类注册到RequestMappingHandlerAdapter和ExceptionHandlerExceptionResolver；加上RestControllerAdvice这个注解会被自动注册
 *
 * {@link RestControllerAdvice}
 *  value/basePackages：指定对那些路径进行拦截，如果都需要拦截的话不需要写value
 *  annotations：指定一个或多个注解，被这些注解所标记的 Controller 会被该 @ControllerAdvice管理
 */
@RestControllerAdvice(value = "pwd.allen.base.controller", annotations = IgnoreResponseAdvice.class)
@Slf4j
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 判断是否需要对响应进行处理
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!AbstractJackson2HttpMessageConverter.class.isAssignableFrom(converterType)) {
            return false;
        }
        //如果类标识了自定义注解，不做处理
        if (returnType.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        //如果方法标识了自定义注解，不做处理
        if (returnType.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)){
            return false;
        }
        return true;
    }

    /**
     * 响应给客户端之前做处理
     * @param body the body to be written
     * @param returnType the return type of the controller method
     * @param selectedContentType the content type selected through content negotiation
     * @param selectedConverterType the converter type selected to write to the response
     * @param request the current request
     * @param response the current response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof MyResult) {
            MyResult myResult = MyResult.class.cast(body);
            log.info("【{}】响应之前做处理，识别到code={}", this.getClass().getName(), myResult.getCode());
        }
        return body;
    }
}
