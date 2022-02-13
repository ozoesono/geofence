package com.rezolve.geofence.model;

import com.rezolve.geofence.validator.GeofencePrecisionConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

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
    @OneToMany(mappedBy = "geofence", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Advert> adverts;
}
