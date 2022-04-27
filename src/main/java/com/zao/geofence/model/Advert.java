package com.zao.geofence.model;

import com.zao.geofence.validator.AdvertUrlConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advert implements Serializable {
    private static final long serialVersionUID = 1264959283549923859L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Pattern(regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    @AdvertUrlConstraint
    private String href;
    @ManyToOne
    @JoinColumn(name = "geofence_id", nullable = false)
    private Geofence geofence;

    // Overriding this due to known issue with Lombok implementation
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advert)) return false;
        Advert advert = (Advert) o;
        return Objects.equals(getId(), advert.getId());
    }

    // Overriding this due to known issue with Lombok implementation
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
