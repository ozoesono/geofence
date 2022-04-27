package com.zao.geofence.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvertTests extends BaseModelTests {

    @Test
    public void testCanCreateValidAdvert() {
        Advert advert = Advert.builder()
            .href("https://google.com")
            .build();
        assertEquals("https://google.com", advert.getHref(), "Href value is set when creating valid Advert");
        assertTrue(isValid(advert), "Can create a valid Advert");
    }

    @Test
    public void testAdvertWithoutValidUrlIsInvalid() {
        Advert advert = Advert.builder()
            .href("https//google.com")
            .build();
        assertFalse(isValid(advert), "Advert with incorrect url format is invalid");
    }

    @Test
    public void testAdvertWithValidUrlFormatButDoesNotOpenIsInvalid() {
        Advert advert = Advert.builder()
            .href("https://webaddresformatcorrect.com")
            .build();
        assertFalse(isValid(advert), "Advert with address that does not exist is invalid");
    }
}
