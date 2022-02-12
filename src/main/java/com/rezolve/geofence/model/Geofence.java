package com.rezolve.geofence.model;

import com.rezolve.geofence.util.validator.GeoFencePrecisionConstraint;
import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class Geofence {

    @Id
    @GeneratedValue
    Long id;
    @NotNull
    @GeoFencePrecisionConstraint
    private BigDecimal lat;
    @GeoFencePrecisionConstraint
    @NotNull
    private BigDecimal lng;
}
