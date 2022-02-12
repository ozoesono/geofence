package com.rezolve.geofence.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class GeofenceModelTests {

    private Validator validator;

    @Test
    public void testCanCreateValidGeofence() {
        Geofence geofence = Geofence.builder()
            .lat(20.0)
            .lng(11.1000000)
            .build();
        assertEquals(20.0, geofence.getLat(), "Lat value is set when creating valid Geofence");
        assertEquals(11.1000000, geofence.getLng(), "Lng value is set when creating valid Geofence");
        assertTrue(isValid(geofence), "Can create a valid Geofence");
    }

    @Test
    public void testGeofenceWithPrecisionAboveSevenIsInvalid() {
        Geofence geofence = Geofence.builder()
            .lat(20.00290909092)
            .lng(11.20903902902)
            .build();
        assertFalse(isValid(geofence), "Geofence is invalid");
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
