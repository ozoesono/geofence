package com.rezolve.geofence.model;

import com.rezolve.geofence.util.validator.GeoFencePrecisionConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Geofence {

    @Id
    @GeneratedValue
    Long id;
    @NotNull
    @GeoFencePrecisionConstraint
    private Double lat;
    @GeoFencePrecisionConstraint
    @NotNull
    private Double lng;
}
