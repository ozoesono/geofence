package com.rezolve.geofence.integration;

import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.repository.GeofenceRepository;
import com.rezolve.geofence.service.GeofenceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GeofenceServiceIntegrationTests {

    @Autowired
    private GeofenceService geofenceService;

    @Autowired
    private GeofenceRepository geofenceRepository;

    @Test
    public void testCanSaveGeoFence() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();

        Geofence saveGeofence = geofenceService.saveGeofence(geofence);
        assertNotNull(geofence, "Geofence is saved to the database");
        assertEquals(1L, saveGeofence.getId(), "Geofence has a new id");
    }

    @Test
    public void testCanGetGeofenceById() {
        Geofence geofence = Geofence.builder()
            .lng(19.10)
            .lat(-1.30)
            .build();

        Geofence savedGeofence = geofenceService.saveGeofence(geofence);

        Geofence retrievedGeofence = geofenceService.getGeofence(savedGeofence.getId());
        assertNotNull(retrievedGeofence, "Geofence is retrieved from the database");
        assertEquals(19.10, retrievedGeofence.getLng(), "Geofence with the right lng is gotten");
        assertEquals(-1.30, retrievedGeofence.getLat(), "Geofence with the right lat is gotten");
    }

    @Test
    public void testCanSaveEditFence() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();

        Geofence geofenceToEdit = geofenceService.saveGeofence(geofence);
        geofenceToEdit.setLat(20.0023);
        geofenceToEdit.setLng(2.3);

        Geofence editedGeofence = geofenceService.saveGeofence(geofenceToEdit);
        assertNotNull(editedGeofence, "Edited Geofence is saved to the database");
        assertEquals(20.0023, editedGeofence.getLat(), "Lat was edited and saved");
        assertEquals(2.3, editedGeofence.getLng(), "Lng was edited and saved");
    }

    @Test
    public void testCanDeleteFence() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();

        Geofence savedGeofence = geofenceService.saveGeofence(geofence);
        Long id = savedGeofence.getId();
        geofenceService.deleteGeoFence(id);
        assertThrows(RuntimeException.class, () -> geofenceService.getGeofence(id), "Should not find the Geofence by id");
    }

    @BeforeEach
    @Transactional
    public void setup() {
        geofenceRepository.deleteAll();
    }

}
