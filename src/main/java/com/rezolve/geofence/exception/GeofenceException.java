package com.rezolve.geofence.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public class GeofenceException extends RuntimeException {
    private static final long serialVersionUID = 90708132067981821L;

    private final String errorMessage;
    private final Status httpStatus;

}
