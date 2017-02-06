package com.cs499.assignment2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cs499.assignment2.domain.Make;

import com.cs499.assignment2.repository.MakeRepository;
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
 * REST controller for managing Make.
 */
@RestController
@RequestMapping("/api")
public class MakeResource {

    private final Logger log = LoggerFactory.getLogger(MakeResource.class);

    private static final String ENTITY_NAME = "make";
        
    private final MakeRepository makeRepository;

    public MakeResource(MakeRepository makeRepository) {
        this.makeRepository = makeRepository;
    }

    /**
     * POST  /makes : Create a new make.
     *
     * @param make the make to create
     * @return the ResponseEntity with status 201 (Created) and with body the new make, or with status 400 (Bad Request) if the make has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/makes")
    @Timed
    public ResponseEntity<Make> createMake(@RequestBody Make make) throws URISyntaxException {
        log.debug("REST request to save Make : {}", make);
        if (make.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new make cannot already have an ID")).body(null);
        }
        Make result = makeRepository.save(make);
        return ResponseEntity.created(new URI("/api/makes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /makes : Updates an existing make.
     *
     * @param make the make to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated make,
     * or with status 400 (Bad Request) if the make is not valid,
     * or with status 500 (Internal Server Error) if the make couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/makes")
    @Timed
    public ResponseEntity<Make> updateMake(@RequestBody Make make) throws URISyntaxException {
        log.debug("REST request to update Make : {}", make);
        if (make.getId() == null) {
            return createMake(make);
        }
        Make result = makeRepository.save(make);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, make.getId().toString()))
            .body(result);
    }

    /**
     * GET  /makes : get all the makes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of makes in body
     */
    @GetMapping("/makes")
    @Timed
    public List<Make> getAllMakes() {
        log.debug("REST request to get all Makes");
        List<Make> makes = makeRepository.findAll();
        return makes;
    }

    /**
     * GET  /makes/:id : get the "id" make.
     *
     * @param id the id of the make to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the make, or with status 404 (Not Found)
     */
    @GetMapping("/makes/{id}")
    @Timed
    public ResponseEntity<Make> getMake(@PathVariable Long id) {
        log.debug("REST request to get Make : {}", id);
        Make make = makeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(make));
    }

    /**
     * DELETE  /makes/:id : delete the "id" make.
     *
     * @param id the id of the make to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/makes/{id}")
    @Timed
    public ResponseEntity<Void> deleteMake(@PathVariable Long id) {
        log.debug("REST request to delete Make : {}", id);
        makeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
