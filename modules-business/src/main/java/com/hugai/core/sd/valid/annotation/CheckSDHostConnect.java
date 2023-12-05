package com.hugai.core.sd.valid.annotation;


import com.hugai.core.sd.valid.SDCheckConnectValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SDCheckConnectValidator.class)
@Documented
public @interface CheckSDHostConnect {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
