package com.zao.geofence.repository;

import com.zao.geofence.model.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeofenceRepository extends JpaRepository<Geofence, Long> {
    Optional<Geofence> findByLatAndLng(Double lat, Double lng);
}
