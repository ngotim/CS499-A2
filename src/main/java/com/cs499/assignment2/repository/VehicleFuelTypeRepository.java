package com.cs499.assignment2.repository;

import com.cs499.assignment2.domain.VehicleFuelType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VehicleFuelType entity.
 */
@SuppressWarnings("unused")
public interface VehicleFuelTypeRepository extends JpaRepository<VehicleFuelType,Long> {

}
