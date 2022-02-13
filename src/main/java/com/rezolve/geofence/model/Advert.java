package com.rezolve.geofence.model;

import com.rezolve.geofence.validator.AdvertUrlConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advert {
    @Id
    @GeneratedValue
    private Long id;
    @Pattern(regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    @AdvertUrlConstraint
    private String href;
    @ManyToOne
    @JoinColumn(name="geofence_id", nullable=false)
    private Geofence geofence;
}
