package com.rezolve.geofence.dto;

import com.rezolve.geofence.validator.AdvertUrlConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequestDto {
    @NotNull
    @AdvertUrlConstraint
    private String href;
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
}
