package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.ModelDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModelDetails entity.
 */
@SuppressWarnings("unused")
public interface ModelDetailsRepository extends JpaRepository<ModelDetails,Long> {

}
