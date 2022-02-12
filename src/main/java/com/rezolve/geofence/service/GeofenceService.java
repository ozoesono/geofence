package com.rezolve.geofence.service;

import com.rezolve.geofence.model.Geofence;

public interface GeofenceService {
    Geofence saveGeofence(Geofence geofence);

    Geofence getGeofence(Long id);

    void deleteGeoFence(Long id);
}
