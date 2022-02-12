package com.rezolve.geofence.model;

import com.rezolve.geofence.util.validator.GeofencePrecisionConstraint;
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
    @GeofencePrecisionConstraint
    private Double lat;
    @GeofencePrecisionConstraint
    @NotNull
    private Double lng;
}
