package com.rezolve.geofence.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GeofencePrecisionValidator implements ConstraintValidator<GeoFencePrecisionConstraint, Double> {
    @Override
    public void initialize(GeoFencePrecisionConstraint constraintAnnotation) {
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
