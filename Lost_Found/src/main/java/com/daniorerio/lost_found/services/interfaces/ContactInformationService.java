package com.daniorerio.lost_found.services.interfaces;

import com.daniorerio.lost_found.entities.ContactInformation;

import java.util.List;
import java.util.Optional;

public interface ContactInformationService {
    List<ContactInformation> getAllContactInformation();

    Optional<ContactInformation> getContactInformationById(Long id);

    ContactInformation createContactInformation(ContactInformation contactInformation);

    ContactInformation updateContactInformation(Long id, ContactInformation updatedContactInformation);

    void deleteContactInformation(Long id);

    List<ContactInformation> findContactInformationByFirstName(String firstName);

    List<ContactInformation> findContactInformationByLastName(String lastName);
}