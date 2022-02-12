package com.rezolve.geofence.integration;

import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.service.GeofenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GeofenceServiceIT {

    @Autowired
    private GeofenceService geofenceService;

    @Test
    public void testCanSaveGeoFence() {
        Geofence geofence = Geofence.builder()
            .lng(BigDecimal.valueOf(20.001))
            .lat(BigDecimal.valueOf(-1.3))
            .build();

        Geofence saveGeofence = geofenceService.saveGeofence(geofence);
        assertNotNull(geofence, "Geofence is saved to the database");
        assertEquals(1L, saveGeofence.getId(), "Geofence has a new id");
    }
}
