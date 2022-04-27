package com.zao.geofence.controller;

import com.zao.geofence.dto.AdvertRequestDto;
import com.zao.geofence.dto.AdvertResponseDto;
import com.zao.geofence.dto.EntranceRequestDto;
import com.zao.geofence.dto.GeofenceRequestDto;
import com.zao.geofence.model.Advert;
import com.zao.geofence.model.Geofence;
import com.zao.geofence.service.GeofenceService;
import com.zao.geofence.web.GeofenceController;
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
        GeofenceRequestDto geofenceRequestDto = GeofenceRequestDto.builder().lat(20.001).lng(-23.32).radius(2.0).build();
        Geofence geofence = Geofence.builder().lat(20.001).lng(-23.32).build();
        Geofence savedGeofence = Geofence.builder().id(id).lat(20.001).lng(-23.32).build();
        when(modelMapper.map(geofenceRequestDto, Geofence.class)).thenReturn(geofence);
        when(geofenceService.saveGeofence(geofence)).thenReturn(savedGeofence);

        ResponseEntity<?> response = geofenceController.createGeofence(geofenceRequestDto);
        assertEquals(201, response.getStatusCode().value(), "Geofence created with created response code");
        List<String> locations = response.getHeaders().get("Location");
        assertTrue(locations.contains("/api/v1/geofence/1"));
    }

    @Test
    public void testCreatesBadRequestOnSaveWithBadData() {
        GeofenceRequestDto geofenceRequestDto = GeofenceRequestDto.builder().lat(20.001).lng(-23.32).radius(2.0).build();
        Geofence geofence = Geofence.builder().lat(20.001).lng(-23.32).build();
        when(modelMapper.map(geofenceRequestDto, Geofence.class)).thenReturn(geofence);
        when(geofenceService.saveGeofence(geofence)).thenReturn(null);

        ResponseEntity<?> response = geofenceController.createGeofence(geofenceRequestDto);
        assertEquals(400, response.getStatusCode().value(), "Geofence is not created if save fails");
    }

    @Test
    public void testCanCreateAdvertInGeofence() {
        AdvertRequestDto advertRequestDto = AdvertRequestDto
            .builder()
            .href("https://google.com")
            .lat(20.001)
            .lng(-23.32)
            .build();

        Advert advert = Advert.builder().href("https://google.com").build();
        Geofence geofence = Geofence.builder().lat(20.001).lng(-23.32).build();

        when(modelMapper.map(advertRequestDto, Advert.class)).thenReturn(advert);
        when(geofenceService.getGeofence(20.001, -23.32)).thenReturn(geofence);
        doNothing().when(geofenceService).addAdvertToGeofence(advert, geofence);

        ResponseEntity<?> response = geofenceController.createAdvert(advertRequestDto);
        assertEquals(200, response.getStatusCode().value(), "Advert can be created in Geofence");
    }

    @Test
    public void testCanGetGeofenceEntrance() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .radius(23.1)
            .build();
        final Set<Advert> adverts = new HashSet<>();
        adverts.add(Advert.builder().href("https://google.com").build());
        geofence.setAdverts(adverts);

        Geofence persistedGeofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .radius(23.1)
            .build();

        final Set<Advert> persistedAdverts = new HashSet<>();
        final Advert advert = Advert.builder().id(1L).href("https://google.com").build();
        persistedAdverts.add(advert);
        persistedGeofence.setAdverts(persistedAdverts);

        final AdvertResponseDto advertResponseDto = AdvertResponseDto.builder().href("https://google.com").build();

        when(geofenceService.getGeofence(-1.3, 20.001)).thenReturn(persistedGeofence);
        when(modelMapper.map(advert, AdvertResponseDto.class)).thenReturn(advertResponseDto);

        EntranceRequestDto entranceRequestDto = EntranceRequestDto.builder().lat(-1.3).lng(20.001).build();
        ResponseEntity<?> response = geofenceController.getEntrance(entranceRequestDto);
        assertEquals(200, response.getStatusCode().value(), "Entrance can be gotten from coordinates in Geofence");
    }
}
