package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.OrderEntity;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.exception.PhotographerNotFoundException;
import com.hhovhann.photostudioservice.repository.OrderRepository;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhotographerServiceImpl implements PhotographerService {
    private final OrderRepository orderRepository;
    private final PhotographerRepository photographerRepository;

    public PhotographerServiceImpl(PhotographerRepository photographerRepository, OrderRepository orderRepository) {
        this.photographerRepository = photographerRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Long>  create(List<PhotographerRequestDTO> photographerRequestDTOs) {
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
        return photographerEntities.stream().map(PhotographerEntity::getId).collect(Collectors.toList());
    }

    @Override
    public void cancel(List<Long> photographerIds) {
        List<PhotographerEntity> photographerEntities = new ArrayList<>();
        for (Long photographerId : photographerIds) {
            PhotographerEntity photographerEntity = photographerRepository.findById(photographerId).orElseThrow(() -> new PhotographerNotFoundException("No photographer found with specified Id"));
            photographerEntities.add(photographerEntity);
        }
        System.out.println("Order entity before" + orderRepository.findById(1L).get().getPhotographers());
        photographerRepository.deleteAll(photographerEntities);
        System.out.println("Order entity before" + orderRepository.findById(1L));
    }
}
