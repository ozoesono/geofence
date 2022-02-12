package com.rezolve.geofence.controller;

import com.rezolve.geofence.dto.GeofenceDto;
import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.service.GeofenceService;
import com.rezolve.geofence.web.GeofenceController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeofenceControllerTests {

    @Mock
    private GeofenceService geofenceService;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GeofenceController geofenceController;

    @Test
    public void testCanCreateGeofence() {
        Long id = 1L;
        GeofenceDto geofenceDto = GeofenceDto.builder().lat(20.001).lng(-23.32).radius(2.0).build();
        Geofence geofence = Geofence.builder().lat(20.001).lng(-23.32).build();
        Geofence savedGeofence = Geofence.builder().id(id).lat(20.001).lng(-23.32).build();
        when(modelMapper.map(geofenceDto, Geofence.class)).thenReturn(geofence);
        when(geofenceService.saveGeofence(geofence)).thenReturn(savedGeofence);

        ResponseEntity<?> response = geofenceController.createGeofence(geofenceDto);
        assertEquals(201, response.getStatusCode().value(), "Geofence created with created response code");
        List<String> locations = response.getHeaders().get("Location");
        assertTrue(locations.contains("/api/v1/geofence/1"));
    }

    @Test
    public void testCreatesBadRequestOnSaveWithBadData() {
        GeofenceDto geofenceDto = GeofenceDto.builder().lat(20.001).lng(-23.32).radius(2.0).build();
        Geofence geofence = Geofence.builder().lat(20.001).lng(-23.32).build();
        when(modelMapper.map(geofenceDto, Geofence.class)).thenReturn(geofence);
        when(geofenceService.saveGeofence(geofence)).thenReturn(null);

        ResponseEntity<?> response = geofenceController.createGeofence(geofenceDto);
        assertEquals(400, response.getStatusCode().value(), "Geofence is not created if save fails");
    }


}
