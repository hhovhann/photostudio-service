package com.hhovhann.photostudioservice.repository;

import com.hhovhann.photostudioservice.domain.entity.PhotographerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotographerRepository extends JpaRepository<PhotographerEntity, Long> {
}
