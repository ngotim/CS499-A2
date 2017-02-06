package com.cs499.assignment2.web.rest;

import com.cs499.assignment2.Assignment2App;

import com.cs499.assignment2.domain.VehicleFuelType;
import com.cs499.assignment2.repository.VehicleFuelTypeRepository;

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
 * Test class for the VehicleFuelTypeResource REST controller.
 *
 * @see VehicleFuelTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class VehicleFuelTypeResourceIntTest {

    private static final String DEFAULT_FUEL_DESC = "AAAAAAAAAA";
    private static final String UPDATED_FUEL_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private VehicleFuelTypeRepository vehicleFuelTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleFuelTypeMockMvc;

    private VehicleFuelType vehicleFuelType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            VehicleFuelTypeResource vehicleFuelTypeResource = new VehicleFuelTypeResource(vehicleFuelTypeRepository);
        this.restVehicleFuelTypeMockMvc = MockMvcBuilders.standaloneSetup(vehicleFuelTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VehicleFuelType createEntity(EntityManager em) {
        VehicleFuelType vehicleFuelType = new VehicleFuelType()
                .fuelDesc(DEFAULT_FUEL_DESC)
                .postalCode(DEFAULT_POSTAL_CODE)
                .city(DEFAULT_CITY)
                .stateProvince(DEFAULT_STATE_PROVINCE);
        return vehicleFuelType;
    }

    @Before
    public void initTest() {
        vehicleFuelType = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleFuelType() throws Exception {
        int databaseSizeBeforeCreate = vehicleFuelTypeRepository.findAll().size();

        // Create the VehicleFuelType

        restVehicleFuelTypeMockMvc.perform(post("/api/vehicle-fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleFuelType)))
            .andExpect(status().isCreated());

        // Validate the VehicleFuelType in the database
        List<VehicleFuelType> vehicleFuelTypeList = vehicleFuelTypeRepository.findAll();
        assertThat(vehicleFuelTypeList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleFuelType testVehicleFuelType = vehicleFuelTypeList.get(vehicleFuelTypeList.size() - 1);
        assertThat(testVehicleFuelType.getFuelDesc()).isEqualTo(DEFAULT_FUEL_DESC);
        assertThat(testVehicleFuelType.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testVehicleFuelType.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testVehicleFuelType.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void createVehicleFuelTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleFuelTypeRepository.findAll().size();

        // Create the VehicleFuelType with an existing ID
        VehicleFuelType existingVehicleFuelType = new VehicleFuelType();
        existingVehicleFuelType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleFuelTypeMockMvc.perform(post("/api/vehicle-fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingVehicleFuelType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VehicleFuelType> vehicleFuelTypeList = vehicleFuelTypeRepository.findAll();
        assertThat(vehicleFuelTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleFuelTypes() throws Exception {
        // Initialize the database
        vehicleFuelTypeRepository.saveAndFlush(vehicleFuelType);

        // Get all the vehicleFuelTypeList
        restVehicleFuelTypeMockMvc.perform(get("/api/vehicle-fuel-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleFuelType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuelDesc").value(hasItem(DEFAULT_FUEL_DESC.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())));
    }

    @Test
    @Transactional
    public void getVehicleFuelType() throws Exception {
        // Initialize the database
        vehicleFuelTypeRepository.saveAndFlush(vehicleFuelType);

        // Get the vehicleFuelType
        restVehicleFuelTypeMockMvc.perform(get("/api/vehicle-fuel-types/{id}", vehicleFuelType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleFuelType.getId().intValue()))
            .andExpect(jsonPath("$.fuelDesc").value(DEFAULT_FUEL_DESC.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicleFuelType() throws Exception {
        // Get the vehicleFuelType
        restVehicleFuelTypeMockMvc.perform(get("/api/vehicle-fuel-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleFuelType() throws Exception {
        // Initialize the database
        vehicleFuelTypeRepository.saveAndFlush(vehicleFuelType);
        int databaseSizeBeforeUpdate = vehicleFuelTypeRepository.findAll().size();

        // Update the vehicleFuelType
        VehicleFuelType updatedVehicleFuelType = vehicleFuelTypeRepository.findOne(vehicleFuelType.getId());
        updatedVehicleFuelType
                .fuelDesc(UPDATED_FUEL_DESC)
                .postalCode(UPDATED_POSTAL_CODE)
                .city(UPDATED_CITY)
                .stateProvince(UPDATED_STATE_PROVINCE);

        restVehicleFuelTypeMockMvc.perform(put("/api/vehicle-fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicleFuelType)))
            .andExpect(status().isOk());

        // Validate the VehicleFuelType in the database
        List<VehicleFuelType> vehicleFuelTypeList = vehicleFuelTypeRepository.findAll();
        assertThat(vehicleFuelTypeList).hasSize(databaseSizeBeforeUpdate);
        VehicleFuelType testVehicleFuelType = vehicleFuelTypeList.get(vehicleFuelTypeList.size() - 1);
        assertThat(testVehicleFuelType.getFuelDesc()).isEqualTo(UPDATED_FUEL_DESC);
        assertThat(testVehicleFuelType.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testVehicleFuelType.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testVehicleFuelType.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleFuelType() throws Exception {
        int databaseSizeBeforeUpdate = vehicleFuelTypeRepository.findAll().size();

        // Create the VehicleFuelType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleFuelTypeMockMvc.perform(put("/api/vehicle-fuel-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleFuelType)))
            .andExpect(status().isCreated());

        // Validate the VehicleFuelType in the database
        List<VehicleFuelType> vehicleFuelTypeList = vehicleFuelTypeRepository.findAll();
        assertThat(vehicleFuelTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicleFuelType() throws Exception {
        // Initialize the database
        vehicleFuelTypeRepository.saveAndFlush(vehicleFuelType);
        int databaseSizeBeforeDelete = vehicleFuelTypeRepository.findAll().size();

        // Get the vehicleFuelType
        restVehicleFuelTypeMockMvc.perform(delete("/api/vehicle-fuel-types/{id}", vehicleFuelType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleFuelType> vehicleFuelTypeList = vehicleFuelTypeRepository.findAll();
        assertThat(vehicleFuelTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleFuelType.class);
    }
}
