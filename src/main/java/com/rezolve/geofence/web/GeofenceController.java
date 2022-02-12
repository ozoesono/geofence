package com.rezolve.geofence.web;

import com.rezolve.geofence.dto.GeofenceDto;
import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.service.GeofenceService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;

import static com.rezolve.geofence.util.Constant.BASE_GEOFENCE_API_URL;

@Controller(BASE_GEOFENCE_API_URL)
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceController {
    private GeofenceService geofenceService;
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> createGeofence(GeofenceDto geofenceDto) {

        log.info("Creating Geofence with: [{}]", geofenceDto);
        Geofence geofence = modelMapper.map(geofenceDto, Geofence.class);
        Geofence savedGeofence = geofenceService.saveGeofence(geofence);

        if (savedGeofence == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(URI.create(BASE_GEOFENCE_API_URL + "/" + savedGeofence.getId())).build();
    }
}
