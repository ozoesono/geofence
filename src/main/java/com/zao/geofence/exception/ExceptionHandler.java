package com.zao.geofence.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

@Slf4j
@ControllerAdvice
public class ExceptionHandler implements ProblemHandling, SecurityAdviceTrait {
    private static final String MESSAGE_KEY = "message";

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(GeofenceException ex, NativeWebRequest request) {
        log.error("An error occurred with Error message: [{}]", ex.getErrorMessage());
        Problem problem = Problem.builder()
            .withStatus(ex.getHttpStatus())
            .withTitle(ex.getHttpStatus().getReasonPhrase())
            .with(MESSAGE_KEY, ex.getErrorMessage())
            .build();
        return create(ex, problem, request);
    }
}
