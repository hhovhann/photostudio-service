package com.hhovhann.photostudioservice.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DBRider
@RunWith(SpringRunner.class)
@SpringBootTest
class PhotographerRepositoryTest {

    @Autowired
    private PhotographerRepository photographerRepository;
    @Test
    @DataSet(cleanBefore = true, transactional = true)
    @ExpectedDataSet("photographer/photographer.yml")
    public void shouldCreatePhotographer() {
        assertThat(photographerRepository).isNotNull();
        assertThat(photographerRepository.count()).isEqualTo(0);

        PhotographerEntity photographerEntity = new PhotographerEntity();
        ContactData contactData = new ContactData();
        contactData.setName("First Photographer Name");
        contactData.setSurname("First Photographer Surname");
        contactData.setEmail("first_photographer@gmail.com");
        contactData.setCellNumber("1231241312");
        photographerEntity.setContactData(contactData);
        photographerRepository.save(photographerEntity);

        assertThat(photographerRepository.count()).isEqualTo(1); //assertion is made by @ExpectedDataset
    }

    @Test
    @DataSet(value = {"photographer/photographers.yml"}, cleanBefore = true)
    public void shouldDeletePhotographer() {
        assertThat(photographerRepository).isNotNull();
        assertThat(photographerRepository.count()).isEqualTo(3);
        photographerRepository.findById(2L).ifPresent(photographerRepository::delete);
    }
}