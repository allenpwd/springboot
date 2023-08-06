package pwd.allen.base.controlleradvice;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import pwd.allen.base.annotation.IgnoreResponseAdvice;
import pwd.allen.base.entity.AlarmMessage;

/**
 * 通过 {@link ResponseBodyAdvice} 对RestController的响应进行拦截处理
 * value是指定对那些路径进行拦截，如果都需要拦截的话不需要写value
 */
@RestControllerAdvice(value = "pwd.allen.base.controller")
public class MyResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    /**
     * 断是否需要对响应进行处理
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
        if (body instanceof AlarmMessage) {
            AlarmMessage alarmMessage = AlarmMessage.class.cast(body);
            alarmMessage.setName("this is adding by MyResponseBodyAdvice");
        }
        return body;
    }
}
