package com.zao.geofence.web;

import com.rezolve.geofence.dto.*;
import com.zao.geofence.dto.*;
import com.zao.geofence.model.Advert;
import com.zao.geofence.model.Geofence;
import com.zao.geofence.service.GeofenceService;
import com.zao.geofence.util.Constant;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(Constant.BASE_GEOFENCE_API_URL)
@Slf4j
@AllArgsConstructor
public class GeofenceController {
    private GeofenceService geofenceService;
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createGeofence(@Valid @RequestBody GeofenceRequestDto geofenceRequestDto) {

        log.info("Creating Geofence with details: [{}]", geofenceRequestDto);
        Geofence geofence = modelMapper.map(geofenceRequestDto, Geofence.class);
        Geofence savedGeofence = geofenceService.saveGeofence(geofence);

        if (savedGeofence == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create(Constant.BASE_GEOFENCE_API_URL + "/" + savedGeofence.getId())).build();
    }

    @PostMapping(Constant.ADVERT_URL)
    public ResponseEntity<?> createAdvert(@Valid @RequestBody AdvertRequestDto advertRequestDto) {

        log.info("Creating Advert with details: [{}]", advertRequestDto);
        Advert advert = modelMapper.map(advertRequestDto, Advert.class);
        Geofence geofence = geofenceService.getGeofence(advertRequestDto.getLat(), advertRequestDto.getLng());
        if (geofence == null) {
            return ResponseEntity.badRequest().build();
        }
        geofenceService.addAdvertToGeofence(advert, geofence);
        return ResponseEntity.ok().build();
    }

    @PostMapping(Constant.ENTRANCE_URL)
    public ResponseEntity<?> getEntrance(@Valid @RequestBody EntranceRequestDto entranceRequestDto) {
        log.info("Getting Entrance (Adverts) with details: [{}]", entranceRequestDto);
        final Geofence geofence = geofenceService.getGeofence(entranceRequestDto.getLat(), entranceRequestDto.getLng());
        if (geofence == null) {
            return ResponseEntity.badRequest().build();
        }
        final Set<AdvertResponseDto> advertResponseDtos = new HashSet<>();
        geofence.getAdverts().forEach(advert -> advertResponseDtos.add(modelMapper.map(advert, AdvertResponseDto.class)));
        EntranceResponseDto entranceResponseDto = EntranceResponseDto.builder()
            .distance(geofence.getRadius())
            .adverts(advertResponseDtos)
            .build();

        return ResponseEntity.ok(entranceResponseDto);
    }
}
