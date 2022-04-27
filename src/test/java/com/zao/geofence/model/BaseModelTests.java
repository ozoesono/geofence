package com.zao.geofence.model;

import org.junit.jupiter.api.BeforeEach;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class BaseModelTests {

    private Validator validator;

    <K> boolean isValid(K k) {
        Set<ConstraintViolation<K>> constraintViolations = validator.validate(k);
        return constraintViolations.isEmpty();
    }

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
