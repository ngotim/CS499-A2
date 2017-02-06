package com.cs499.assignment2.web.rest;

import com.cs499.assignment2.Assignment2App;

import com.cs499.assignment2.domain.ModelDetails;
import com.cs499.assignment2.repository.ModelDetailsRepository;

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
 * Test class for the ModelDetailsResource REST controller.
 *
 * @see ModelDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Assignment2App.class)
public class ModelDetailsResourceIntTest {

    private static final String DEFAULT_BODY_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_BODY_STYLE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Double DEFAULT_MPG = 1D;
    private static final Double UPDATED_MPG = 2D;

    private static final String DEFAULT_CURR_MILES = "AAAAAAAAAA";
    private static final String UPDATED_CURR_MILES = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private ModelDetailsRepository modelDetailsRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private EntityManager em;

    private MockMvc restModelDetailsMockMvc;

    private ModelDetails modelDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ModelDetailsResource modelDetailsResource = new ModelDetailsResource(modelDetailsRepository);
        this.restModelDetailsMockMvc = MockMvcBuilders.standaloneSetup(modelDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModelDetails createEntity(EntityManager em) {
        ModelDetails modelDetails = new ModelDetails()
                .bodyStyle(DEFAULT_BODY_STYLE)
                .color(DEFAULT_COLOR)
                .mpg(DEFAULT_MPG)
                .currMiles(DEFAULT_CURR_MILES)
                .price(DEFAULT_PRICE);
        return modelDetails;
    }

    @Before
    public void initTest() {
        modelDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createModelDetails() throws Exception {
        int databaseSizeBeforeCreate = modelDetailsRepository.findAll().size();

        // Create the ModelDetails

        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isCreated());

        // Validate the ModelDetails in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ModelDetails testModelDetails = modelDetailsList.get(modelDetailsList.size() - 1);
        assertThat(testModelDetails.getBodyStyle()).isEqualTo(DEFAULT_BODY_STYLE);
        assertThat(testModelDetails.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testModelDetails.getMpg()).isEqualTo(DEFAULT_MPG);
        assertThat(testModelDetails.getCurrMiles()).isEqualTo(DEFAULT_CURR_MILES);
        assertThat(testModelDetails.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createModelDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modelDetailsRepository.findAll().size();

        // Create the ModelDetails with an existing ID
        ModelDetails existingModelDetails = new ModelDetails();
        existingModelDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelDetailsMockMvc.perform(post("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingModelDetails)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);

        // Get all the modelDetailsList
        restModelDetailsMockMvc.perform(get("/api/model-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].bodyStyle").value(hasItem(DEFAULT_BODY_STYLE.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].mpg").value(hasItem(DEFAULT_MPG.doubleValue())))
            .andExpect(jsonPath("$.[*].currMiles").value(hasItem(DEFAULT_CURR_MILES.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);

        // Get the modelDetails
        restModelDetailsMockMvc.perform(get("/api/model-details/{id}", modelDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modelDetails.getId().intValue()))
            .andExpect(jsonPath("$.bodyStyle").value(DEFAULT_BODY_STYLE.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.mpg").value(DEFAULT_MPG.doubleValue()))
            .andExpect(jsonPath("$.currMiles").value(DEFAULT_CURR_MILES.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingModelDetails() throws Exception {
        // Get the modelDetails
        restModelDetailsMockMvc.perform(get("/api/model-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);
        int databaseSizeBeforeUpdate = modelDetailsRepository.findAll().size();

        // Update the modelDetails
        ModelDetails updatedModelDetails = modelDetailsRepository.findOne(modelDetails.getId());
        updatedModelDetails
                .bodyStyle(UPDATED_BODY_STYLE)
                .color(UPDATED_COLOR)
                .mpg(UPDATED_MPG)
                .currMiles(UPDATED_CURR_MILES)
                .price(UPDATED_PRICE);

        restModelDetailsMockMvc.perform(put("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModelDetails)))
            .andExpect(status().isOk());

        // Validate the ModelDetails in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeUpdate);
        ModelDetails testModelDetails = modelDetailsList.get(modelDetailsList.size() - 1);
        assertThat(testModelDetails.getBodyStyle()).isEqualTo(UPDATED_BODY_STYLE);
        assertThat(testModelDetails.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testModelDetails.getMpg()).isEqualTo(UPDATED_MPG);
        assertThat(testModelDetails.getCurrMiles()).isEqualTo(UPDATED_CURR_MILES);
        assertThat(testModelDetails.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingModelDetails() throws Exception {
        int databaseSizeBeforeUpdate = modelDetailsRepository.findAll().size();

        // Create the ModelDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restModelDetailsMockMvc.perform(put("/api/model-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modelDetails)))
            .andExpect(status().isCreated());

        // Validate the ModelDetails in the database
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteModelDetails() throws Exception {
        // Initialize the database
        modelDetailsRepository.saveAndFlush(modelDetails);
        int databaseSizeBeforeDelete = modelDetailsRepository.findAll().size();

        // Get the modelDetails
        restModelDetailsMockMvc.perform(delete("/api/model-details/{id}", modelDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ModelDetails> modelDetailsList = modelDetailsRepository.findAll();
        assertThat(modelDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModelDetails.class);
    }
}
