package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.Make;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Make entity.
 */
@SuppressWarnings("unused")
public interface MakeRepository extends JpaRepository<Make,Long> {

}
