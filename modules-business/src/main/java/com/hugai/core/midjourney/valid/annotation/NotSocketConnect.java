package com.hugai.core.midjourney.valid.annotation;

import com.hugai.core.midjourney.valid.NotSocketContentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotSocketContentValidator.class)
@Documented
public @interface NotSocketConnect {
    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
