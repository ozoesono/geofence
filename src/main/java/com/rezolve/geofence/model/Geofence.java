package com.rezolve.geofence.model;

import com.rezolve.geofence.util.validator.GeoFencePrecisionConstraint;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class Geofence {
    @NotNull
    @GeoFencePrecisionConstraint
    private BigDecimal lat;
    @GeoFencePrecisionConstraint
    @NotNull
    private BigDecimal lng;
}
