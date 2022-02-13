package com.rezolve.geofence.service;

import com.rezolve.geofence.model.Advert;
import com.rezolve.geofence.model.Geofence;

import java.util.Set;

public interface GeofenceService {
    Geofence saveGeofence(Geofence geofence);

    Geofence getGeofence(Long id);

    Geofence getGeofence(Double lat, Double lng);

    void deleteGeoFence(Long id);

    Set<Advert> getAdverts(Double lat, Double lng);

    void deleteAdvert(String href, Double lat, Double lng);

    void addAdvertToGeofence(Advert advert, Geofence geofence);
}
