package com.daniorerio.lost_found.entities;

import java.util.List;

public class LostItem {
    private String itemName;
    private String itemDescription;
    private List<String> itemKeywords;
    private ContactInformation contactInformation;

    public LostItem(String itemName, String itemDescription, List<String> itemKeywords, ContactInformation contactInformation) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemKeywords = itemKeywords;
        this.contactInformation = contactInformation;
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

    public void setItemKeywords(List<String> itemKeywords) {
        this.itemKeywords = itemKeywords;
    }

    public List<String> getItemKeywords() {
        return itemKeywords;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }
}
