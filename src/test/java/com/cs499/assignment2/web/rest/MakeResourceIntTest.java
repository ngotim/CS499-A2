package com.cs499.assignment2.web.rest;

import com.cs499.assignment2.Assignment2App;

import com.cs499.assignment2.domain.Make;
import com.cs499.assignment2.repository.MakeRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MakeResource REST controller.
 *
 * @see MakeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class MakeResourceIntTest {

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    @Autowired
    private MakeRepository makeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restMakeMockMvc;

    private Make make;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            MakeResource makeResource = new MakeResource(makeRepository);
        this.restMakeMockMvc = MockMvcBuilders.standaloneSetup(makeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Make createEntity(EntityManager em) {
        Make make = new Make()
                .make(DEFAULT_MAKE);
        return make;
    }

    @Before
    public void initTest() {
        make = createEntity(em);
    }

    @Test
    @Transactional
    public void createMake() throws Exception {
        int databaseSizeBeforeCreate = makeRepository.findAll().size();

        // Create the Make

        restMakeMockMvc.perform(post("/api/makes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(make)))
            .andExpect(status().isCreated());

        // Validate the Make in the database
        List<Make> makeList = makeRepository.findAll();
        assertThat(makeList).hasSize(databaseSizeBeforeCreate + 1);
        Make testMake = makeList.get(makeList.size() - 1);
        assertThat(testMake.getMake()).isEqualTo(DEFAULT_MAKE);
    }

    @Test
    @Transactional
    public void createMakeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = makeRepository.findAll().size();

        // Create the Make with an existing ID
        Make existingMake = new Make();
        existingMake.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMakeMockMvc.perform(post("/api/makes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingMake)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Make> makeList = makeRepository.findAll();
        assertThat(makeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMakes() throws Exception {
        // Initialize the database
        makeRepository.saveAndFlush(make);

        // Get all the makeList
        restMakeMockMvc.perform(get("/api/makes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(make.getId().intValue())))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE.toString())));
    }

    @Test
    @Transactional
    public void getMake() throws Exception {
        // Initialize the database
        makeRepository.saveAndFlush(make);

        // Get the make
        restMakeMockMvc.perform(get("/api/makes/{id}", make.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(make.getId().intValue()))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMake() throws Exception {
        // Get the make
        restMakeMockMvc.perform(get("/api/makes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMake() throws Exception {
        // Initialize the database
        makeRepository.saveAndFlush(make);
        int databaseSizeBeforeUpdate = makeRepository.findAll().size();

        // Update the make
        Make updatedMake = makeRepository.findOne(make.getId());
        updatedMake
                .make(UPDATED_MAKE);

        restMakeMockMvc.perform(put("/api/makes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMake)))
            .andExpect(status().isOk());

        // Validate the Make in the database
        List<Make> makeList = makeRepository.findAll();
        assertThat(makeList).hasSize(databaseSizeBeforeUpdate);
        Make testMake = makeList.get(makeList.size() - 1);
        assertThat(testMake.getMake()).isEqualTo(UPDATED_MAKE);
    }

    @Test
    @Transactional
    public void updateNonExistingMake() throws Exception {
        int databaseSizeBeforeUpdate = makeRepository.findAll().size();

        // Create the Make

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMakeMockMvc.perform(put("/api/makes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(make)))
            .andExpect(status().isCreated());

        // Validate the Make in the database
        List<Make> makeList = makeRepository.findAll();
        assertThat(makeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMake() throws Exception {
        // Initialize the database
        makeRepository.saveAndFlush(make);
        int databaseSizeBeforeDelete = makeRepository.findAll().size();

        // Get the make
        restMakeMockMvc.perform(delete("/api/makes/{id}", make.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Make> makeList = makeRepository.findAll();
        assertThat(makeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Make.class);
    }
}
