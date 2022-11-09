package com.hhovhann.photostudioservice.service;

import com.hhovhann.photostudioservice.domain.data.ContactData;
import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import com.hhovhann.photostudioservice.dto.PhotographerRequestDTO;
import com.hhovhann.photostudioservice.dto.PhotographerResponseDTO;
import com.hhovhann.photostudioservice.exception.PhotographerNotFoundException;
import com.hhovhann.photostudioservice.mapper.PhotographerMapper;
import com.hhovhann.photostudioservice.repository.PhotographerRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = PRIVATE)
public class PhotographerServiceImpl implements PhotographerService {
  PhotographerRepository photographerRepository;
  PhotographerMapper photographerMapper;

  @Override
  public List<Long> create(List<PhotographerRequestDTO> photographerRequestDTOs) {
    Set<PhotographerEntity> photographerEntities = new HashSet<>();
    photographerRequestDTOs.forEach(
        photographerRequestDTO -> {
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
    return photographerEntities.stream().map(PhotographerEntity::getId).toList();
  }

  @Override
  public PhotographerResponseDTO update(
      Long photographerId, PhotographerRequestDTO photographerRequestDTO) {
    PhotographerEntity photographerEntity = photographerMapper.toEntity(photographerRequestDTO);
    PhotographerEntity updatedEntity = photographerRepository.save(photographerEntity);

    return photographerMapper.toResponseDTO(updatedEntity);
  }

  @Override
  public void cancel(List<Long> photographerIds) {
    List<PhotographerEntity> photographerEntities = new ArrayList<>();
    for (Long photographerId : photographerIds) {
      PhotographerEntity photographerEntity =
          photographerRepository
              .findById(photographerId)
              .orElseThrow(
                  () ->
                      new PhotographerNotFoundException("No photographer found with specified Id"));
      photographerEntities.add(photographerEntity);
    }

    photographerRepository.deleteAll(photographerEntities);
  }
}
