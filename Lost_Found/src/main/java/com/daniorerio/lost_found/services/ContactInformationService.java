package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.entities.ContactInformation;
import com.daniorerio.lost_found.repositories.ContactInformationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactInformationService {
    private final ContactInformationRepository contactInformationRepository;

    // DI with constructor
    public ContactInformationService(ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    public void createContactInformation(ContactInformation contactInformation) {
        contactInformationRepository.addContact(contactInformation);
    }

    public List<ContactInformation> getAllContactInformation() {
        return contactInformationRepository.findAllContacts();
    }

    public Optional<ContactInformation> getContactByPhoneNumber(String phoneNumber) {
        return contactInformationRepository.findByPhoneNumber(phoneNumber);
    }

    public void deleteContactByPhoneNumber(String phoneNumber) {
        contactInformationRepository.removeByPhoneNumber(phoneNumber);
    }
}
