package com.cs499.assignment2.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VehicleFuelType.
 */
@Entity
@Table(name = "vehicle_fuel_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class VehicleFuelType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fuel_desc")
    private String fuelDesc;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuelDesc() {
        return fuelDesc;
    }

    public VehicleFuelType fuelDesc(String fuelDesc) {
        this.fuelDesc = fuelDesc;
        return this;
    }

    public void setFuelDesc(String fuelDesc) {
        this.fuelDesc = fuelDesc;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public VehicleFuelType postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public VehicleFuelType city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public VehicleFuelType stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VehicleFuelType vehicleFuelType = (VehicleFuelType) o;
        if (vehicleFuelType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vehicleFuelType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VehicleFuelType{" +
            "id=" + id +
            ", fuelDesc='" + fuelDesc + "'" +
            ", postalCode='" + postalCode + "'" +
            ", city='" + city + "'" +
            ", stateProvince='" + stateProvince + "'" +
            '}';
    }
}
