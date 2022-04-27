package com.zao.geofence.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdvertUrlValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdvertUrlConstraint {
    String message() default "Invalid url";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
