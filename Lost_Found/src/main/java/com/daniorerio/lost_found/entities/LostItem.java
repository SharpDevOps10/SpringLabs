package com.daniorerio.lost_found.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lost_items")
@NamedQueries({
        @NamedQuery(
                name = "LostItem.findByItemDescription",
                query = "SELECT li FROM LostItem li WHERE li.itemDescription = :description"
        )
})
public class LostItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @ElementCollection
    @Column(name = "item_keywords")
    private List<String> itemKeywords;

    @ManyToOne
    @JoinColumn(name = "contact_information_id")
    private ContactInformation contactInformation;

    @ManyToOne
    @JoinColumn(name = "locations_id")
    private Location location;

    public LostItem(long id, String itemName, String itemDescription, List<String> itemKeywords) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemKeywords = itemKeywords;
    }

    public LostItem() {
    }

    public long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public List<String> getItemKeywords() {
        return itemKeywords;
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