package com.rezolve.geofence.dto;

import com.rezolve.geofence.validator.AdvertUrlConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertDto {
    @NotNull
    @AdvertUrlConstraint
    private String href;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
}
