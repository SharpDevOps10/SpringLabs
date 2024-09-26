package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.ContactInformation;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactInformationRepository {
    private final List<ContactInformation> contactInformationList = new ArrayList<>();

    public void addContact(ContactInformation contactInformation) {
        contactInformationList.add(contactInformation);
    }

    public List<ContactInformation> findAllContacts() {
        return contactInformationList;
    }

    public Optional<ContactInformation> findByPhoneNumber(String phoneNumber) {
        return contactInformationList.stream()
                .filter(info -> info.getPhoneNumber().equals(phoneNumber))
                .findFirst();
    }

    public void removeByPhoneNumber(String phoneNumber) {
        contactInformationList.removeIf(info -> info.getPhoneNumber().equals(phoneNumber));
    }
}
