package com.daniorerio.lost_found.DAO;

import com.daniorerio.lost_found.entities.ContactInformation;

import java.util.List;
import java.util.Optional;

public interface ContactInformationDao {
    ContactInformation createContactInformation(ContactInformation contactInformation);

    Optional<ContactInformation> findById(Long id);

    ContactInformation updateContactInformation(ContactInformation contactInformation);

    void deleteContactInformation(Long id);

    List<ContactInformation> findByFirstName(String firstName);

    List<ContactInformation> findAll();
}