package com.rezolve.geofence.model;

import com.rezolve.geofence.validator.GeofencePrecisionConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @NotNull
    private Double radius;
    @OneToMany(mappedBy = "geofence", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Advert> adverts;
}
