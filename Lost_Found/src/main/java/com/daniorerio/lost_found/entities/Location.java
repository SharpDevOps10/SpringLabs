package com.daniorerio.lost_found.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
@NamedQuery(name = "Location.findByAddress", query = "SELECT l FROM Location l WHERE l.address = :address")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "zip_code")
    private String zipCode;

    public Location(long id, String city, String address, String zipCode) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
    }

    public Location() {
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setId(long id) {
        this.id = id;
    }
}
