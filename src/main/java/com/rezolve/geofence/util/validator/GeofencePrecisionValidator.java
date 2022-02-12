package com.rezolve.geofence.util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class GeofencePrecisionValidator implements ConstraintValidator<GeoFencePrecisionConstraint, BigDecimal> {
    @Override
    public void initialize(GeoFencePrecisionConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BigDecimal bigDecimal, ConstraintValidatorContext constraintValidatorContext) {
        String string = bigDecimal.toPlainString();
        int index = string.indexOf(".");
        int decimalPlaces = index < 0 ? 0 : string.length() - index - 1;

        return decimalPlaces >= 1 && decimalPlaces <= 7;
    }
}
