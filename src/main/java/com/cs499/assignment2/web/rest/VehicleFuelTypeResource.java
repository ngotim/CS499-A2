package com.cs499.assignment2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.assignment2.domain.VehicleFuelType;

import com.cs499.assignment2.repository.VehicleFuelTypeRepository;
import com.cs499.assignment2.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VehicleFuelType.
 */
@RestController
@RequestMapping("/api")
public class VehicleFuelTypeResource {

    private final Logger log = LoggerFactory.getLogger(VehicleFuelTypeResource.class);

    private static final String ENTITY_NAME = "vehicleFuelType";
        
    private final VehicleFuelTypeRepository vehicleFuelTypeRepository;

    public VehicleFuelTypeResource(VehicleFuelTypeRepository vehicleFuelTypeRepository) {
        this.vehicleFuelTypeRepository = vehicleFuelTypeRepository;
    }

    /**
     * POST  /vehicle-fuel-types : Create a new vehicleFuelType.
     *
     * @param vehicleFuelType the vehicleFuelType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleFuelType, or with status 400 (Bad Request) if the vehicleFuelType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-fuel-types")
    @Timed
    public ResponseEntity<VehicleFuelType> createVehicleFuelType(@RequestBody VehicleFuelType vehicleFuelType) throws URISyntaxException {
        log.debug("REST request to save VehicleFuelType : {}", vehicleFuelType);
        if (vehicleFuelType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vehicleFuelType cannot already have an ID")).body(null);
        }
        VehicleFuelType result = vehicleFuelTypeRepository.save(vehicleFuelType);
        return ResponseEntity.created(new URI("/api/vehicle-fuel-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-fuel-types : Updates an existing vehicleFuelType.
     *
     * @param vehicleFuelType the vehicleFuelType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleFuelType,
     * or with status 400 (Bad Request) if the vehicleFuelType is not valid,
     * or with status 500 (Internal Server Error) if the vehicleFuelType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-fuel-types")
    @Timed
    public ResponseEntity<VehicleFuelType> updateVehicleFuelType(@RequestBody VehicleFuelType vehicleFuelType) throws URISyntaxException {
        log.debug("REST request to update VehicleFuelType : {}", vehicleFuelType);
        if (vehicleFuelType.getId() == null) {
            return createVehicleFuelType(vehicleFuelType);
        }
        VehicleFuelType result = vehicleFuelTypeRepository.save(vehicleFuelType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleFuelType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-fuel-types : get all the vehicleFuelTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleFuelTypes in body
     */
    @GetMapping("/vehicle-fuel-types")
    @Timed
    public List<VehicleFuelType> getAllVehicleFuelTypes() {
        log.debug("REST request to get all VehicleFuelTypes");
        List<VehicleFuelType> vehicleFuelTypes = vehicleFuelTypeRepository.findAll();
        return vehicleFuelTypes;
    }

    /**
     * GET  /vehicle-fuel-types/:id : get the "id" vehicleFuelType.
     *
     * @param id the id of the vehicleFuelType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleFuelType, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-fuel-types/{id}")
    @Timed
    public ResponseEntity<VehicleFuelType> getVehicleFuelType(@PathVariable Long id) {
        log.debug("REST request to get VehicleFuelType : {}", id);
        VehicleFuelType vehicleFuelType = vehicleFuelTypeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleFuelType));
    }

    /**
     * DELETE  /vehicle-fuel-types/:id : delete the "id" vehicleFuelType.
     *
     * @param id the id of the vehicleFuelType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-fuel-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleFuelType(@PathVariable Long id) {
        log.debug("REST request to delete VehicleFuelType : {}", id);
        vehicleFuelTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
