package pwd.allen.base.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.base.entity.MyEntity;
import pwd.allen.base.entity.MyResult;
import pwd.allen.base.validator.MyValidator;

import javax.validation.Valid;

/**
 * @author pwdan
 * @create 2023-09-22 16:47
 **/
@Api(tags = "校验")
@RestController("valid")
@Slf4j
public class ValidController {

    @ApiOperation("binder")
    @PostMapping("binder")
    public MyResult<MyEntity> binder(@RequestBody MyEntity myEntity, BindingResult result) {
        return MyResult.success(myEntity);
    }
    @InitBinder
    private void initBinder2(WebDataBinder binder) {
        binder.addValidators(new MyValidator());
    }

    /**
     * 通过BindingResult的getFieldErrors方法取出具体的错误信息
     * @param myEntity
     * @param result
     * @return
     */
    @ApiOperation("valid")
    @PostMapping("valid")
    public MyResult<Object> valid(@RequestBody @Valid MyEntity myEntity, BindingResult result) {
        if (result.hasErrors()) {
            return MyResult.error(result.getFieldErrors());
        }
        return MyResult.success(myEntity);
    }
}
