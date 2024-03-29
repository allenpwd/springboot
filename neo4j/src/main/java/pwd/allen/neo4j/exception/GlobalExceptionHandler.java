package pwd.allen.neo4j.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pwd.allen.neo4j.entity.doc.MyResponse;
import pwd.allen.neo4j.enums.ResponseCodeEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获自定义业务异常
     * @return
     */
    @ExceptionHandler({BizException.class})
    @ResponseBody
    public MyResponse<Object> handleBizException(HttpServletRequest request, BizException e){
        log.warn("{} request fail, errorCode: {}, errorMsg: {}",
                request.getRequestURI(),e.getErrorCode(),e.getErrorMessage());
        return MyResponse.fail(e);
    }

    /**
     * 其他类型异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public MyResponse<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return MyResponse.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public MyResponse<Object> handleMethodArgumentNotValidException(HttpServletRequest request
            ,MethodArgumentNotValidException e){
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        // 获取BindingResult
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        // 获取校验不通过的字段，并组合错误信息，格式为：email 邮箱格式不正确，当前值：111qq.com
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors ->{
            errors.forEach(error ->
                    sb.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append(",当前值: '")
                            .append(error.getRejectedValue())
                            .append("';")
            );
        });
        // 错误信息
        String errorMessage = sb.toString();
        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return MyResponse.fail(errorCode,errorMessage);
    }
}
