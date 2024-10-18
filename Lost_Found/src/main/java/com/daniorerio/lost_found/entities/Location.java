package com.daniorerio.lost_found.entities;

public class Location {
    private Long id;

    private String city;
    private String address;
    private String zipCode;

    public Location(long id, String city, String address, String zipCode) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.zipCode = zipCode;
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
