package com.daniorerio.lost_found.services.interfaces;

import com.daniorerio.lost_found.entities.ContactInformation;

import java.util.List;
import java.util.Optional;

public interface ContactInformationService {
    ContactInformation createContactInformation(ContactInformation contactInformation);

    Optional<ContactInformation> findById(Long id);

    ContactInformation updateContactInformation(ContactInformation contactInformation);

    void deleteContactInformation(Long id);

    List<ContactInformation> findByFirstName(String firstName);

    List<ContactInformation> findAll();
}