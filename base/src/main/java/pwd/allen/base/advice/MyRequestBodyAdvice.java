package pwd.allen.base.advice;

/**
 * @author pwdan
 * @create 2023-12-06 17:13
 **/

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 在执行Controller接口之前，如果Controller参数是被@RequestBody修饰，可以对该参数进行一些特殊处理，比如参数解密，参数转换等。
 *
 * TODO https://blog.csdn.net/weixin_37672801/article/details/125307351
 */
@RestControllerAdvice(value = "pwd.allen.base.controller")
@Slf4j
public class MyRequestBodyAdvice implements RequestBodyAdvice {

    /**
     * 该方法用于判断当前请求，是否要执行beforeBodyRead方法
     * methodParameter方法的参数对象
     * type方法的参数类型
     * aClass 将会使用到的Http消息转换器类类型
     * 注意：此判断方法，会在beforeBodyRead 和 afterBodyRead方法前都触发一次。
     *
     * @return 返回true则会执行beforeBodyRead
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        // TODO 是否只有参数有@RequestBody的才会进来
        return true;
    }

    /**
     * 在Http消息转换器执转换，之前执行
     * inputMessage 客户端的请求数据
     * parameter方法的参数对象
     * targetType方法的参数类型
     * converterType 将会使用到的Http消息转换器类类型
     * @return 返回 一个自定义的HttpInputMessage
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * 在Http消息转换器执转换，之后执行
     * body 转换后的对象
     * inputMessage 客户端的请求数据
     * parameter 方法的参数类型
     * targetType 方法的参数类型
     * converterType 使用的Http消息转换器类类型
     * @return 返回一个新的对象
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * body为空时由该方法处理
     */
    @Override
    @Nullable
    public Object handleEmptyBody(@Nullable Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}
