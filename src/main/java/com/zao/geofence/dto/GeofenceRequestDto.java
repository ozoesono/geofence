package com.zao.geofence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceRequestDto {
    @NotNull
    private Double lat;
    @NotNull
    private Double lng;
    @NotNull
    private Double radius;

    // Overriding this due to known issue with Lombok implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeofenceRequestDto)) return false;
        GeofenceRequestDto geofenceRequestDto = (GeofenceRequestDto) o;
        return Objects.equals(getLat() + "" + getLng() + "" + getRadius(),
            geofenceRequestDto.getLat() + "" + getLng() + "" + getRadius());
    }

    // Overriding this due to known issue with Lombok implementation
    @Override
    public int hashCode() {
        return Objects.hash(getLat() + "" + getLat() + "" + radius);
    }
}
