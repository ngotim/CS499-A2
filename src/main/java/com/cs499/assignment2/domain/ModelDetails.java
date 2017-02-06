package com.cs499.assignment2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ModelDetails.
 */
@Entity
@Table(name = "model_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModelDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body_style")
    private String bodyStyle;

    @Column(name = "color")
    private String color;

    @Column(name = "mpg")
    private Double mpg;

    @Column(name = "curr_miles")
    private String currMiles;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private Model model;

    @ManyToOne
    private VehicleFuelType fuelType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public ModelDetails bodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
        return this;
    }

    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    public String getColor() {
        return color;
    }

    public ModelDetails color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getMpg() {
        return mpg;
    }

    public ModelDetails mpg(Double mpg) {
        this.mpg = mpg;
        return this;
    }

    public void setMpg(Double mpg) {
        this.mpg = mpg;
    }

    public String getCurrMiles() {
        return currMiles;
    }

    public ModelDetails currMiles(String currMiles) {
        this.currMiles = currMiles;
        return this;
    }

    public void setCurrMiles(String currMiles) {
        this.currMiles = currMiles;
    }

    public Double getPrice() {
        return price;
    }

    public ModelDetails price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Model getModel() {
        return model;
    }

    public ModelDetails model(Model model) {
        this.model = model;
        return this;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public VehicleFuelType getFuelType() {
        return fuelType;
    }

    public ModelDetails fuelType(VehicleFuelType vehicleFuelType) {
        this.fuelType = vehicleFuelType;
        return this;
    }

    public void setFuelType(VehicleFuelType vehicleFuelType) {
        this.fuelType = vehicleFuelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModelDetails modelDetails = (ModelDetails) o;
        if (modelDetails.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, modelDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ModelDetails{" +
            "id=" + id +
            ", bodyStyle='" + bodyStyle + "'" +
            ", color='" + color + "'" +
            ", mpg='" + mpg + "'" +
            ", currMiles='" + currMiles + "'" +
            ", price='" + price + "'" +
            '}';
    }
}
