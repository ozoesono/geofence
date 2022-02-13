package com.rezolve.geofence.service.impl;

import com.rezolve.geofence.model.Advert;
import com.rezolve.geofence.model.Geofence;
import com.rezolve.geofence.repository.GeofenceRepository;
import com.rezolve.geofence.service.GeofenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Slf4j
@Service
public class GeofenceServiceImpl implements GeofenceService {

    private GeofenceRepository geofenceRepository;
    private Validator validator;

    @Override
    @Transactional
    public Geofence saveGeofence(Geofence geofence) {
        log.info("Saving Geofence: [{}]", geofence);
        if (!validator.validate(geofence).isEmpty()) {
            log.error("Error saving geofence due to validation errors");
            throw new RuntimeException("Error saving Geofence");
        }
        return geofenceRepository.save(geofence);
    }

    @Override
    @Transactional
    public Geofence getGeofence(Long id) {
        log.info("Getting Geofence by id: [{}]", id);
        Optional<Geofence> geofence = geofenceRepository.findById(id);
        if (!geofence.isPresent()) {
            throw new RuntimeException("Geofence does not exist");
        }
        return geofence.get();
    }

    @Override
    @Transactional
    public void deleteGeoFence(Long id) {
        log.info("Deleting Geofence by id: [{}]", id);
        Optional<Geofence> geofence = geofenceRepository.findById(id);
        if (!geofence.isPresent()) {
            throw new RuntimeException("Geofence does not exist");
        }
        geofenceRepository.delete(geofence.get());
    }

    @Override
    @Transactional
    public Set<Advert> getAdverts(Double lat, Double lng) {
        log.info("Getting adverts give Geofence coordinates - lat: [{}] and lng: [{}]", lat, lng);
        Optional<Geofence> geofence = geofenceRepository.findByLatAndLng(lat, lng);
        if (!geofence.isPresent()) {
            throw new RuntimeException("Geofence does with specified coordinates not exist");
        }
        return geofence.get().getAdverts();
    }

    @Override
    @Transactional
    public void deleteAdvert(String href, Double lat, Double lng) {
        log.info("Deleting Advert with href: [{}] from Geofence with coordinates - lat: [{}] and lng: [{}]",
            href, lat, lng);
        Optional<Geofence> geofence = geofenceRepository.findByLatAndLng(lat, lng);
        if (!geofence.isPresent()) {
            throw new RuntimeException("Geofence does with specified coordinates not exist");
        }
        Geofence geofenceToUpdate = geofence.get();
        Set<Advert> filteredAdverts = geofenceToUpdate.getAdverts().stream()
            .filter(advert -> !advert.getHref().equals(href)).collect(Collectors.toSet());
        geofenceToUpdate.setAdverts(filteredAdverts);
        geofenceRepository.save(geofenceToUpdate);
    }
}
