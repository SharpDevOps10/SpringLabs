package com.daniorerio.lost_found.entities;

import java.util.List;

public class LostItem {
    private long id;

    private String itemName;
    private String itemDescription;
    private List<String> itemKeywords;
    private ContactInformation contactInformation;
    private Location location;

    public LostItem(long id, String itemName, String itemDescription, List<String> itemKeywords) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemKeywords = itemKeywords;
    }

    public long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public List<String> getItemKeywords() {
        return itemKeywords;
    }

    public void setItemKeywords(List<String> itemKeywords) {
        this.itemKeywords = itemKeywords;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setId(long id) {
        this.id = id;
    }
}