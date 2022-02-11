package com.rezolve.geofence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeofenceModelTests {

    private Validator validator;

    @Test
    public void testCanCreateValidGeofence() {
        Geofence geofence = Geofence.builder()
            .lat(BigDecimal.valueOf(20.0))
            .lng(BigDecimal.valueOf(11.1))
            .build();
        assertEquals(BigDecimal.valueOf(20.0), geofence.getLat(), "Lat value is set when creating valid Geofence");
        assertEquals(BigDecimal.valueOf(11.1), geofence.getLng(), "Lng value is set when creating valid Geofence");
        assertTrue(isValid(geofence), "Can create a valid Geofence");
    }

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private boolean isValid(Geofence geofence) {
        Set<ConstraintViolation<Geofence>> constraintViolations = validator.validate(geofence);
        return constraintViolations.isEmpty();
    }
}
