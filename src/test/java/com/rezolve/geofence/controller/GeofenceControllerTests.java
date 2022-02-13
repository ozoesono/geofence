package com.rezolve.geofence.controller;

import com.rezolve.geofence.dto.AdvertDto;
import com.rezolve.geofence.dto.EntranceDto;
import com.rezolve.geofence.dto.GeofenceDto;
import com.rezolve.geofence.model.Advert;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
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

    @Test
    public void testCanCreateAdvertInGeofence() {
        AdvertDto advertDto = AdvertDto
            .builder()
            .href("https://google.com")
            .lat(20.001)
            .lng(-23.32)
            .build();

        Advert advert = Advert.builder().href("https://google.com").build();
        Geofence geofence = Geofence.builder().lat(20.001).lng(-23.32).build();

        when(modelMapper.map(advertDto, Advert.class)).thenReturn(advert);
        when(geofenceService.getGeofence(20.001, -23.32)).thenReturn(geofence);
        doNothing().when(geofenceService).addAdvertToGeofence(advert, geofence);

        ResponseEntity<?> response = geofenceController.createAdvert(advertDto);
        assertEquals(200, response.getStatusCode().value(), "Advert can be created in Geofence");
    }

    @Test
    public void testCanGetGeofenceEntrance() {
        final Set<Advert> adverts = new HashSet<>();
        adverts.add(Advert.builder().id(1L).href("https://google.com").build());
        adverts.add(Advert.builder().id(2L).href("https://oracle.com").build());

        when(geofenceService.getAdverts(-1.3, 20.001)).thenReturn(adverts);

        EntranceDto entranceDto = EntranceDto.builder().lat(-1.3).lng(20.001).build();
        ResponseEntity<?> response = geofenceController.getEntrance(entranceDto);
        assertEquals(200, response.getStatusCode().value(), "Advert can be gotten from coordinates in Geofence");
        assertTrue(response.getBody().toString().contains("https://google.com"));
        assertTrue(response.getBody().toString().contains("https://oracle.com"));
    }
}
