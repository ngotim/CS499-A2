package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.Model;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Model entity.
 */
@SuppressWarnings("unused")
public interface ModelRepository extends JpaRepository<Model,Long> {

}
