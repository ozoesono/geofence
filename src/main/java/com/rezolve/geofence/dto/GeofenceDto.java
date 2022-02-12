package com.rezolve.geofence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceDto {
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private Double radius;
}
