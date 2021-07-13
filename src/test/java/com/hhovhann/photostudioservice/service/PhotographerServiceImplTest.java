package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PhotographerServiceImplTest {

    @Autowired
    private PhotographerService photographerService;

    @MockBean
    private PhotographerRepository photographerRepository;

    @Test
    @DisplayName("Should Create Photographers")
    void shouldSuccessFullyCreateThePhotographers() {
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

        PhotographerEntity firstPhotographerEntity = new PhotographerEntity();
        ContactData firstPhotographerEntityContactDate = new ContactData();
        firstPhotographerEntityContactDate.setName("Hayk1");
        firstPhotographerEntityContactDate.setSurname("Hovhannisyan1");
        firstPhotographerEntityContactDate.setEmail("haik.hovhanisyan1@gmail.com");
        firstPhotographerEntityContactDate.setName("+37412345678");
        firstPhotographerEntity.setId(1L);
        firstPhotographerEntity.setContactData(firstPhotographerEntityContactDate);

        PhotographerEntity secondPhotographerEntity = new PhotographerEntity();
        ContactData secondPhotographerEntityContactDate = new ContactData();
        secondPhotographerEntityContactDate.setName("Hayk1");
        secondPhotographerEntityContactDate.setSurname("Hovhannisyan1");
        secondPhotographerEntityContactDate.setEmail("haik.hovhanisyan1@gmail.com");
        secondPhotographerEntityContactDate.setName("+37412345678");
        secondPhotographerEntity.setId(2L);
        secondPhotographerEntity.setContactData(secondPhotographerEntityContactDate);
        List<PhotographerEntity> photographerEntities = Arrays.asList(firstPhotographerEntity, secondPhotographerEntity);

        Mockito.when(photographerRepository.findAll()).thenReturn(photographerEntities);

        // when
        photographerService.create(photographerDtos);

        // then
        assertEquals(2, photographerRepository.findAll().size());
    }
}