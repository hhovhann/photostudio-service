package com.hhovhann.photostudioservice.repository;

import com.hhovhann.photostudioservice.domain.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotographerRepository extends JpaRepository<Photographer, Long> {
}
