package com.ksawio.fitnessapi.repositories;

import com.ksawio.fitnessapi.dto.BodyPartDto;
import com.ksawio.fitnessapi.entities.BodyPart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BodyPartRepository extends JpaRepository<BodyPart, Long> {
    @Query("SELECT new com.ksawio.fitnessapi.dto.BodyPartDto(b) FROM BodyPart b WHERE b.name = :name")
    Page<BodyPartDto> findByName(@Param("name")String name, Pageable pageable);
}
