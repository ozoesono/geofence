package com.rezolve.geofence.web;

import com.rezolve.geofence.dto.AdvertDto;
import com.rezolve.geofence.dto.EntranceDto;
import com.rezolve.geofence.dto.GeofenceDto;
import com.rezolve.geofence.model.Advert;
import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.service.GeofenceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Set;

import static com.rezolve.geofence.util.Constant.*;

@RestController
@RequestMapping(BASE_GEOFENCE_API_URL)
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceController {
    private GeofenceService geofenceService;
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createGeofence(GeofenceDto geofenceDto) {

        log.info("Creating Geofence with details: [{}]", geofenceDto);
        Geofence geofence = modelMapper.map(geofenceDto, Geofence.class);
        Geofence savedGeofence = geofenceService.saveGeofence(geofence);

        if (savedGeofence == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create(BASE_GEOFENCE_API_URL + "/" + savedGeofence.getId())).build();
    }

    @PostMapping(ADVERT_URL)
    public ResponseEntity<?> createAdvert(AdvertDto advertDto) {

        log.info("Creating Advert with details: [{}]", advertDto);
        Advert advert = modelMapper.map(advertDto, Advert.class);
        Geofence geofence = geofenceService.getGeofence(advertDto.getLat(), advertDto.getLng());
        geofenceService.addAdvertToGeofence(advert, geofence);
        return ResponseEntity.ok().build();
    }

    @GetMapping(ENTRANCE_URL)
    public ResponseEntity<?> getEntrance(EntranceDto entranceDto) {
        log.info("Getting Entrance (Adverts) with details: [{}]", entranceDto);
        Set<Advert> adverts = geofenceService.getAdverts(entranceDto.getLat(), entranceDto.getLng());
        return ResponseEntity.ok(adverts);
    }
}
