package pwd.allen.neo4j.entity.doc;

import lombok.Data;
import pwd.allen.neo4j.exception.BaseExceptionInterface;
import pwd.allen.neo4j.exception.BizException;


/**
 * @Author: 门那粒沙
 * @Date: 2023/12/22 15:17
 * @Version: v1.0.0
 * @Description:
 **/
@Data
public class MyResponse<T> {
    // 是否成功，默认为 true
    private boolean success = true;
    // 响应消息
    private String message;
    // 异常码
    private String errorCode;
    // 响应数据
    private T data;

    // =================================== 成功响应 ===================================
    public static <T> MyResponse<T> success() {
        MyResponse<T> Result = new MyResponse<>();
        return Result;
    }

    public static <T> MyResponse<T> success(T data) {
        MyResponse<T> Result = new MyResponse<>();
        Result.setData(data);
        return Result;
    }

    // =================================== 失败响应 ===================================
    public static <T> MyResponse<T> fail() {
        MyResponse<T> Result = new MyResponse<>();
        Result.setSuccess(false);
        return Result;
    }

    public static <T> MyResponse<T> fail(String errorMessage) {
        MyResponse<T> Result = new MyResponse<>();
        Result.setSuccess(false);
        Result.setMessage(errorMessage);
        return Result;
    }

    public static <T> MyResponse<T> fail(String errorCode, String errorMessage) {
        MyResponse<T> Result = new MyResponse<>();
        Result.setSuccess(false);
        Result.setErrorCode(errorCode);
        Result.setMessage(errorMessage);
        return Result;
    }

    public static <T> MyResponse<T> fail(BizException bizException) {
        MyResponse<T> MyResponse = new MyResponse<>();
        MyResponse.setSuccess(false);
        MyResponse.setErrorCode(bizException.getErrorCode());
        MyResponse.setMessage(bizException.getErrorMessage());
        return MyResponse;
    }

    public static <T> MyResponse<T> fail(BaseExceptionInterface baseExceptionInterface) {
        MyResponse<T> MyResponse = new MyResponse<>();
        MyResponse.setSuccess(false);
        MyResponse.setErrorCode(baseExceptionInterface.getErrorCode());
        MyResponse.setMessage(baseExceptionInterface.getErrorMessage());
        return MyResponse;
    }
}
