package com.zao.geofence.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class AdvertUrlValidator implements ConstraintValidator<AdvertUrlConstraint, String> {

    @Override
    public void initialize(AdvertUrlConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String destinationUrl, ConstraintValidatorContext constraintValidatorContext) {
        URL url;
        try {
            url = new URL(destinationUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            if (status == 200) {
                return true;
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
}
