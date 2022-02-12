package com.rezolve.geofence.service;

import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.repository.GeofenceRepository;
import com.rezolve.geofence.service.impl.GeofenceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GeofenceServiceTests {
    @Mock
    private GeofenceRepository geofenceRepository;
    @Mock
    private Validator validator;
    @InjectMocks
    private GeofenceServiceImpl geofenceService;

    @Test
    public void testCanCreateGeofence() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();

        Geofence persistedGeofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .build();
        when(geofenceRepository.save(geofence)).thenReturn(persistedGeofence);
        when(validator.validate(geofence)).thenReturn(new HashSet<>());

        Geofence savedGeofence = geofenceService.saveGeofence(geofence);
        Assertions.assertNotNull(savedGeofence, "Getting a saved Geofence");
        Assertions.assertEquals(1L, savedGeofence.getId(), "Geofence has an id");
    }

    @Test
    public void testCanGetGeofence() {
        Geofence geofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .build();
        when(geofenceRepository.findById(1L)).thenReturn(Optional.of(geofence));

        Geofence existingGeofence = geofenceService.getGeofence(1L);
        Assertions.assertNotNull(existingGeofence, "Getting a Geofence");
        Assertions.assertEquals(1L, existingGeofence.getId(), "The Geofence has the id used to get it");
    }

    @Test
    public void testGettingGeofenceThatDoesNotExistThrowsException() {
        when(geofenceRepository.findById(1L)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> geofenceService.getGeofence(1L));
    }


    @Test
    public void testCanUpdateGeofence() {
        Geofence geofence = Geofence.builder()
            .id(1L)
            .lng(25.001)
            .lat(5.3)
            .build();

        when(geofenceRepository.save(geofence)).thenReturn(geofence);

        Geofence updateGeofence = geofenceService.saveGeofence(geofence);
        Assertions.assertNotNull(updateGeofence, "Getting an updated Geofence");
        Assertions.assertEquals(1L, updateGeofence.getId(), "Geofence has an id");
    }

    @Test
    public void testCanDeleteGeofence() {
        Geofence geofence = Geofence.builder()
            .id(1L)
            .lng(25.001)
            .lat(5.3)
            .build();

        when(geofenceRepository.findById(1L)).thenReturn(Optional.of(geofence));
        doNothing().when(geofenceRepository).delete(geofence);
        geofenceService.deleteGeoFence(1L);
        verify(geofenceRepository, times(1)).delete(geofence);
    }
}
