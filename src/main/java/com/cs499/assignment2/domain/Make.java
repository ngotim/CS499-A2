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
 * A Make.
 */
@Entity
@Table(name = "make")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Make implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "make")
    private String make;

    @OneToMany(mappedBy = "make")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Model> models = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public Make make(String make) {
        this.make = make;
        return this;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Set<Model> getModels() {
        return models;
    }

    public Make models(Set<Model> models) {
        this.models = models;
        return this;
    }

    public Make addModel(Model model) {
        this.models.add(model);
        model.setMake(this);
        return this;
    }

    public Make removeModel(Model model) {
        this.models.remove(model);
        model.setMake(null);
        return this;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Make make = (Make) o;
        if (make.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, make.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Make{" +
            "id=" + id +
            ", make='" + make + "'" +
            '}';
    }
}
