package com.zao.geofence.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GeofencePrecisionValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GeofencePrecisionConstraint {
    String message() default "Invalid coordinate precision";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
