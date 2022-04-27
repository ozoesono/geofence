package com.zao.geofence.service.impl;

import com.zao.geofence.exception.GeofenceException;
import com.zao.geofence.model.Advert;
import com.zao.geofence.model.Geofence;
import com.zao.geofence.repository.GeofenceRepository;
import com.zao.geofence.service.GeofenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

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
            throw new GeofenceException("Error saving Geofence", Status.BAD_REQUEST);
        }
        return geofenceRepository.save(geofence);
    }

    @Override
    @Transactional
    public Geofence getGeofence(Long id) {
        log.info("Getting Geofence by id: [{}]", id);
        Optional<Geofence> geofence = geofenceRepository.findById(id);
        if (!geofence.isPresent()) {
            throw new GeofenceException("Geofence does not exist", Status.BAD_REQUEST);
        }
        return geofence.get();
    }

    @Override
    public Geofence getGeofence(Double lat, Double lng) {
        log.info("Getting Geofence by lat: [{}] and lng: [{}]", lat, lng);
        Optional<Geofence> geofence = geofenceRepository.findByLatAndLng(lat, lng);

        if (!geofence.isPresent()) {
            throw new GeofenceException("Geofence does not exist", Status.BAD_REQUEST);
        }
        return geofence.get();
    }

    @Override
    @Transactional
    public void deleteGeoFence(Long id) {
        log.info("Deleting Geofence by id: [{}]", id);
        Optional<Geofence> geofence = geofenceRepository.findById(id);
        if (!geofence.isPresent()) {
            throw new GeofenceException("Geofence does not exist", Status.BAD_REQUEST);
        }
        geofenceRepository.delete(geofence.get());
    }

    @Override
    @Transactional
    public Set<Advert> getAdverts(Double lat, Double lng) {
        log.info("Getting adverts give Geofence coordinates - lat: [{}] and lng: [{}]", lat, lng);
        Optional<Geofence> geofence = geofenceRepository.findByLatAndLng(lat, lng);
        if (!geofence.isPresent()) {
            throw new GeofenceException("Geofence does with specified coordinates not exist", Status.BAD_REQUEST);
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
            throw new GeofenceException("Geofence does with specified coordinates not exist", Status.BAD_REQUEST);
        }
        Geofence geofenceToUpdate = geofence.get();
        Set<Advert> filteredAdverts = geofenceToUpdate.getAdverts().stream()
            .filter(advert -> !advert.getHref().equals(href)).collect(Collectors.toSet());
        geofenceToUpdate.setAdverts(filteredAdverts);
        geofenceRepository.save(geofenceToUpdate);
    }

    @Override
    @Transactional
    public void addAdvertToGeofence(Advert advert, Geofence geofence) {
        advert.setGeofence(geofence);
        Set<Advert> updatedAdverts = geofence.getAdverts();
        updatedAdverts.add(advert);
        geofence.setAdverts(updatedAdverts);
        geofenceRepository.save(geofence);
    }
}
