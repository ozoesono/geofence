package com.zao.geofence.model;

import com.zao.geofence.validator.GeofencePrecisionConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Geofence implements Serializable {

    private static final long serialVersionUID = 6826048284963822970L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    // Overriding this due to known issue with Lombok implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Geofence)) return false;
        Geofence geofence = (Geofence) o;
        return Objects.equals(getId(), geofence.getId());
    }

    // Overriding this due to known issue with Lombok implementation
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
