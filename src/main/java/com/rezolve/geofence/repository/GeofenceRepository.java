package com.rezolve.geofence.repository;

import com.rezolve.geofence.model.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeofenceRepository extends JpaRepository<Geofence, Long> {
}
