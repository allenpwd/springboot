package pwd.allen.base.advice;

/**
 * @author pwdan
 * @create 2023-12-06 17:13
 **/

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.support.RequestContextUtils;
import pwd.allen.base.entity.MyEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 在执行Controller接口之前，如果Controller接口的参数是被@RequestBody修饰，可以对该参数进行一些特殊处理，比如参数解密，参数转换等。
 *
 * TODO https://blog.csdn.net/weixin_37672801/article/details/125307351
 */
@RestControllerAdvice(value = "pwd.allen.base.controller")
@Slf4j
public class MyRequestBodyAdvice implements RequestBodyAdvice {

    /**
     * 该方法用于判断当前请求的RequestBody参数是否要拦截，即是否要执行beforeBodyRead方法
     * 注意：此判断方法，会在beforeBodyRead 和 afterBodyRead方法前都触发一次。
     *
     * @param methodParameter 方法的参数对象
     * @param targetType 方法的参数类型
     * @param converterType 将会使用到的Http消息转换器类类型
     * @return 返回true则会执行beforeBodyRead
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return MyEntity.class.getTypeName().equals(targetType.getTypeName());
    }

    /**
     * 在Http消息转换器执转换，之前执行
     *
     * @param inputMessage 客户端的请求数据
     * @param parameter 方法的参数对象
     * @param targetType 方法的参数类型
     * @param converterType 将会使用到的Http消息转换器类类型
     * @return 返回 一个自定义的HttpInputMessage
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if (inputMessage.getBody().available() <= 0) {
            return inputMessage;
        }

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        // 获取前端入参jsonobj
        String requestData = IoUtil.read(inputMessage.getBody(), StandardCharsets.UTF_8);
//        byte[] requestBodyByte = new byte[inputMessage.getBody().available()];
//        inputMessage.getBody().read(requestBodyByte);
//        String requestData = new String(requestBodyByte, StandardCharsets.UTF_8);
        MyEntity myEntity = JSONUtil.toBean(requestData, MyEntity.class);
        myEntity.setStrA("我用RequestBodyAdvice改了值");

        // 使用解密后的数据，构造新的读取流
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(JSONUtil.toJsonStr(myEntity).getBytes());
            }
            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }
        };
    }

    /**
     * 在Http消息转换器执转换，之后执行
     *
     * @param body 转换后的对象
     * @param inputMessage 客户端的请求数据
     * @param parameter 方法的参数对象
     * @param targetType 方法的参数类型
     * @param converterType 将会使用到的Http消息转换器类类型
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
