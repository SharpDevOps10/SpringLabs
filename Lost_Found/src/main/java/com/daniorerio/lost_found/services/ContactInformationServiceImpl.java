package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.entities.ContactInformation;
import com.daniorerio.lost_found.repositories.ContactInformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daniorerio.lost_found.services.interfaces.ContactInformationService;

import java.util.List;
import java.util.Optional;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {

    private final ContactInformationRepository contactInformationRepository;

    @Autowired
    public ContactInformationServiceImpl(ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    @Override
    public List<ContactInformation> getAllContactInformation() {
        return (List<ContactInformation>) contactInformationRepository.findAll();
    }

    @Override
    public Optional<ContactInformation> getContactInformationById(Long id) {
        return contactInformationRepository.findById(id);
    }

    @Override
    public ContactInformation createContactInformation(ContactInformation contactInformation) {
        return contactInformationRepository.save(contactInformation);
    }

    @Override
    public ContactInformation updateContactInformation(Long id, ContactInformation updatedContactInformation) {
        if (contactInformationRepository.existsById(id)) {
            updatedContactInformation.setId(id);
            return contactInformationRepository.save(updatedContactInformation);
        }
        return null;
    }

    @Override
    public void deleteContactInformation(Long id) {
        contactInformationRepository.deleteById(id);
    }

    @Override
    public List<ContactInformation> findContactInformationByFirstName(String firstName) {
        return contactInformationRepository.findByFirstName(firstName);
    }

    @Override
    public List<ContactInformation> findContactInformationByLastName(String lastName) {
        return contactInformationRepository.findByLastName(lastName);
    }
}