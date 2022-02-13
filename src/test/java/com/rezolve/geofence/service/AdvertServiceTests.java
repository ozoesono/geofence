package com.rezolve.geofence.service;

import com.rezolve.geofence.model.Advert;
import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.repository.GeofenceRepository;
import com.rezolve.geofence.service.impl.GeofenceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdvertServiceTests {

    @Mock
    private GeofenceRepository geofenceRepository;
    @Mock
    private Validator validator;
    @InjectMocks
    private GeofenceServiceImpl geofenceService;

    @Test
    public void testCanCreateAdverts() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();
        final Set<Advert> adverts = new HashSet<>();
        adverts.add(Advert.builder().href("https://google.com").build());
        adverts.add(Advert.builder().href("https://oracle.com").build());
        adverts.add(Advert.builder().href("https://microsoft.com").build());
        geofence.setAdverts(adverts);

        Geofence persistedGeofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .build();

        final Set<Advert> persistedAdverts = new HashSet<>();
        persistedAdverts.add(Advert.builder().id(1L).href("https://google.com").build());
        persistedAdverts.add(Advert.builder().id(2L).href("https://oracle.com").build());
        persistedAdverts.add(Advert.builder().id(3L).href("https://microsoft.com").build());
        persistedGeofence.setAdverts(persistedAdverts);


        when(geofenceRepository.save(geofence)).thenReturn(persistedGeofence);
        when(validator.validate(geofence)).thenReturn(new HashSet<>());

        Geofence savedGeofence = geofenceService.saveGeofence(geofence);
        Set<Advert> savedAdverts = savedGeofence.getAdverts();
        assertEquals(3, savedAdverts.size());

        Collection<Advert> filteredAdverts = savedAdverts.stream()
            .filter(advert -> advert.getId() != null)
            .collect(Collectors.toList());
        assertEquals(3, filteredAdverts.size(), "Can save Adverts");
    }

    @Test
    public void testCanGetAdvertsInGeofence() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();
        final Set<Advert> adverts = new HashSet<>();
        adverts.add(Advert.builder().href("https://google.com").build());
        adverts.add(Advert.builder().href("https://oracle.com").build());
        adverts.add(Advert.builder().href("https://microsoft.com").build());
        geofence.setAdverts(adverts);

        Geofence persistedGeofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .build();

        final Set<Advert> persistedAdverts = new HashSet<>();
        persistedAdverts.add(Advert.builder().id(1L).href("https://google.com").build());
        persistedAdverts.add(Advert.builder().id(2L).href("https://oracle.com").build());
        persistedAdverts.add(Advert.builder().id(3L).href("https://microsoft.com").build());
        persistedGeofence.setAdverts(persistedAdverts);

        when(geofenceRepository.findByLatAndLng(-1.3, 20.001)).thenReturn(Optional.of(persistedGeofence));
        Set<Advert> geofenceAdverts = geofenceService.getAdverts(-1.3, 20.001);

        assertEquals(3, geofenceAdverts.size());

        Collection<Advert> filteredAdverts = geofenceAdverts.stream()
            .filter(advert -> advert.getId() != null)
            .collect(Collectors.toList());
        assertEquals(3, filteredAdverts.size(), "Can get Adverts from Geofence");

    }

    @Test
    public void testCanDeleteAdvertInGeofence() {
        Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .build();
        final Set<Advert> adverts = new HashSet<>();
        adverts.add(Advert.builder().href("https://google.com").build());
        adverts.add(Advert.builder().href("https://oracle.com").build());
        adverts.add(Advert.builder().href("https://microsoft.com").build());
        geofence.setAdverts(adverts);

        Geofence persistedGeofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .build();

        final Set<Advert> persistedAdverts = new HashSet<>();
        persistedAdverts.add(Advert.builder().id(1L).href("https://google.com").build());
        persistedAdverts.add(Advert.builder().id(2L).href("https://oracle.com").build());
        persistedAdverts.add(Advert.builder().id(3L).href("https://microsoft.com").build());
        persistedGeofence.setAdverts(persistedAdverts);

        Geofence geofenceWithoutSpecificAdvert = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .build();

        final Set<Advert> updatedAdvertMocks = new HashSet<>();
        updatedAdvertMocks.add(Advert.builder().id(2L).href("https://oracle.com").build());
        updatedAdvertMocks.add(Advert.builder().id(3L).href("https://microsoft.com").build());
        geofenceWithoutSpecificAdvert.setAdverts(updatedAdvertMocks);

        when(geofenceRepository.findByLatAndLng(-1.3, 20.001)).thenReturn(Optional.of(persistedGeofence));
        when(geofenceRepository.save(geofenceWithoutSpecificAdvert)).thenReturn(geofenceWithoutSpecificAdvert);

        geofenceService.deleteAdvert("https://google.com", -1.3, 20.001);
        Set<Advert> updatedAdverts = geofenceService.getAdverts(-1.3, 20.001);
        assertEquals(2, updatedAdverts.size());
        Stream<Advert> filteredAdvertSteam = updatedAdverts.stream()
            .filter(advert -> advert.getHref().equals("https://google.com"));
        assertEquals(0, filteredAdvertSteam.count());
        verify(geofenceRepository, times(1)).save(geofenceWithoutSpecificAdvert);
    }
}
