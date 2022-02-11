package com.rezolve.geofence.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class Geofence {
    @NotNull
    private BigDecimal lat;
    @NotNull
    private BigDecimal lng;
}
