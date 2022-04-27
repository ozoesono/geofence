package com.zao.geofence.integration;

import com.zao.geofence.configuration.AppConfiguration;
import com.zao.geofence.dto.AdvertRequestDto;
import com.zao.geofence.dto.GeofenceRequestDto;
import com.zao.geofence.model.Advert;
import com.zao.geofence.model.Geofence;
import com.zao.geofence.service.GeofenceService;
import com.zao.geofence.web.GeofenceController;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GeofenceController.class)
@Import(AppConfiguration.class)
public class GeofenceControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private GeofenceService geofenceService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testCanCreateGeofence() throws Exception {

        final Geofence geofence = Geofence.builder()
            .lng(20.001)
            .lat(-1.3)
            .radius(23.1)
            .build();

        final Geofence persistedGeofence = Geofence.builder()
            .id(1L)
            .lng(20.001)
            .lat(-1.3)
            .radius(23.1)
            .build();

        final GeofenceRequestDto geofenceRequestDto = GeofenceRequestDto.builder().lng(20.001).lat(-1.3).radius(23.1).build();

        when(geofenceService.saveGeofence(geofence)).thenReturn(persistedGeofence);
        when(modelMapper.map(geofenceRequestDto, Geofence.class)).thenReturn(geofence);

        String geofenceBody = "{\"lng\":20.001, \"lat\":-1.3, \"radius\":23.1}";
        this.mockMvc.perform(
                post("/api/v1/geofence")
                    .contentType("application/json")
                    .content(geofenceBody))
            .andExpect(status().isCreated())
            .andExpect(content().string(""))
            .andReturn();
    }

    @Test
    public void testCanCreateAdvertInGeofence() throws Exception {
        AdvertRequestDto advertRequestDto = AdvertRequestDto
            .builder()
            .href("https://google.com")
            .lat(-2.0)
            .lng(2.0)
            .build();

        Advert advert = Advert.builder().href("https://google.com").build();
        Geofence geofence = Geofence.builder().lat(-2.0).lng(2.0).radius(12.1).build();

        when(modelMapper.map(advertRequestDto, Advert.class)).thenReturn(advert);
        when(geofenceService.getGeofence(-2.0, 2.0)).thenReturn(geofence);
        doNothing().when(geofenceService).addAdvertToGeofence(advert, geofence);

        String advertBody = "{\"lng\":2.0, \"lat\":-2.0, \"href\":\"https://google.com\"}";
        this.mockMvc.perform(
                post("/api/v1/geofence/adverts")
                    .contentType("application/json")
                    .content(advertBody))
            .andExpect(status().isOk())
            .andExpect(content().string(""))
            .andReturn();
    }
}
