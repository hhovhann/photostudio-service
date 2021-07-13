package com.hhovhann.photostudioservice.controller;

import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.service.PhotographerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class PhotographerControllerTest {
    @Mock
    private PhotographerService photographerService;

    @InjectMocks
    private PhotographerController photographerController;

    @Test
    @DisplayName("Should Create Photographers")
    public void shouldSuccessfullyCreatesPhotographers() {
        // given
        PhotographerRequestDTO firstPhotographer = new PhotographerRequestDTO();
        firstPhotographer.setName("Hayk1");
        firstPhotographer.setSurname("Hovhannisyan1");
        firstPhotographer.setEmail("haik.hovhanisyan1@gmail.com");
        firstPhotographer.setName("+37412345678");

        PhotographerRequestDTO secondPhotographer = new PhotographerRequestDTO();
        secondPhotographer.setName("Hayk2");
        secondPhotographer.setSurname("Hovhannisyan2");
        secondPhotographer.setEmail("haik.hovhanisyan2@gmail.com");
        secondPhotographer.setName("+37487654321");
        List<PhotographerRequestDTO> photographerDtos = Arrays.asList(firstPhotographer, secondPhotographer);

        photographerController.create(photographerDtos);

        Mockito.verify(photographerService).create(photographerDtos);
    }
}