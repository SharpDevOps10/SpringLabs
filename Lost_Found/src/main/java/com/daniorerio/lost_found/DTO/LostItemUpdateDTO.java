package com.daniorerio.lost_found.DTO;

import java.util.List;

public class LostItemUpdateDTO {
    private String itemName;
    private String itemDescription;
    private List<String> itemKeywords;
    private Long contactInformationId;
    private Long locationId;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public List<String> getItemKeywords() {
        return itemKeywords;
    }

    public void setItemKeywords(List<String> itemKeywords) {
        this.itemKeywords = itemKeywords;
    }

    public Long getContactInformationId() {
        return contactInformationId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setContactInformationId(Long contactInformationId) {
        this.contactInformationId = contactInformationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}