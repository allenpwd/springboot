package pwd.allen.base.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pwd.allen.base.entity.MyEntity;

/**
 * 自定义数据校验器
 */
//@Component
public class MyValidator implements Validator {

    /**
     * 判断是否需要校验
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return MyEntity.class.equals(clazz);
    }

    /**
     * 校验逻辑
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        // 校验是否为空
        ValidationUtils.rejectIfEmpty(errors, "strA", "strA不能为空");
        // 校验intA的值
        MyEntity p = (MyEntity) target;
        if (p.getIntA() < 1) {
            errors.rejectValue("intA", "intA不能小于1");
        }
    }
}
