package com.zao.geofence.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GeofencePrecisionValidator implements ConstraintValidator<GeofencePrecisionConstraint, Double> {
    @Override
    public void initialize(GeofencePrecisionConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext constraintValidatorContext) {
        String string = value.toString();
        int index = string.indexOf(".");
        int decimalPlaces = index < 0 ? 0 : string.length() - index - 1;

        return decimalPlaces >= 1 && decimalPlaces <= 7;
    }
}
