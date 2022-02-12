package com.rezolve.geofence.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeofenceTests extends BaseModelTests {


    @Test
    public void testCanCreateValidGeofence() {
        Geofence geofence = Geofence.builder().lat(20.0).lng(11.1000000).build();
        assertEquals(20.0, geofence.getLat(), "Lat value is set when creating valid Geofence");
        assertEquals(11.1000000, geofence.getLng(), "Lng value is set when creating valid Geofence");
        assertTrue(isValid(geofence), "Can create a valid Geofence");
    }

    @Test
    public void testGeofenceWithPrecisionAboveSevenIsInvalid() {
        Geofence geofence = Geofence.builder().lat(20.00290909092).lng(11.20903902902).build();
        assertFalse(isValid(geofence), "Geofence is invalid");
    }
}
