package com.cs499.assignment2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Model.
 */
@Entity
@Table(name = "model")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_name")
    private String modelName;

    @ManyToOne
    private Make make;

    @OneToMany(mappedBy = "model")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ModelDetails> details = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public Model modelName(String modelName) {
        this.modelName = modelName;
        return this;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Make getMake() {
        return make;
    }

    public Model make(Make make) {
        this.make = make;
        return this;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Set<ModelDetails> getDetails() {
        return details;
    }

    public Model details(Set<ModelDetails> modelDetails) {
        this.details = modelDetails;
        return this;
    }

    public Model addDetails(ModelDetails modelDetails) {
        this.details.add(modelDetails);
        modelDetails.setModel(this);
        return this;
    }

    public Model removeDetails(ModelDetails modelDetails) {
        this.details.remove(modelDetails);
        modelDetails.setModel(null);
        return this;
    }

    public void setDetails(Set<ModelDetails> modelDetails) {
        this.details = modelDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Model model = (Model) o;
        if (model.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Model{" +
            "id=" + id +
            ", modelName='" + modelName + "'" +
            '}';
    }
}
