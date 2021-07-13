package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import com.hhovhann.photostudioservice.validatiors.DataValidator;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PhotographerServiceImpl implements PhotographerService {
    private final PhotographerRepository photographerRepository;

    public PhotographerServiceImpl(PhotographerRepository photographerRepository, OrderRepository orderRepository, DataValidator dataValidator) {
        this.photographerRepository = photographerRepository;
    }

    @Override
    public void create(List<PhotographerRequestDTO> photographerRequestDTOs) {
        Set<PhotographerEntity> photographerEntities = new HashSet<>();
        photographerRequestDTOs.forEach(photographerRequestDTO -> {
            PhotographerEntity photographer = new PhotographerEntity();
            ContactData firstContactData = new ContactData();
            firstContactData.setName(photographerRequestDTO.getName());
            firstContactData.setSurname(photographerRequestDTO.getSurname());
            firstContactData.setEmail(photographerRequestDTO.getEmail());
            firstContactData.setCellNumber(photographerRequestDTO.getEmail());
            photographer.setContactData(firstContactData);

            photographerEntities.add(photographer);
        });

        photographerRepository.saveAll(photographerEntities);
    }
}
