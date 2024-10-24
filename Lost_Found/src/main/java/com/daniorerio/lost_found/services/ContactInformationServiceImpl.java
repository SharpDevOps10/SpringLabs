package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DTO.UpdateContactInformationDto;
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
    public Optional<ContactInformation> updateContactInformation(Long id, UpdateContactInformationDto updateContactInformationDto) {
        Optional<ContactInformation> existingContactOpt = contactInformationRepository.findById(id);

        if (existingContactOpt.isPresent()) {
            ContactInformation existingContact = existingContactOpt.get();

            updateContactInformationDto.firstName().ifPresent(existingContact::setFirstName);
            updateContactInformationDto.lastName().ifPresent(existingContact::setLastName);
            updateContactInformationDto.email().ifPresent(existingContact::setEmail);
            updateContactInformationDto.phoneNumber().ifPresent(existingContact::setPhoneNumber);

            contactInformationRepository.save(existingContact);
            return Optional.of(existingContact);
        }

        return Optional.empty();
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

    @Override
    public List<ContactInformation> findContactInformationByEmailPart(String emailPart) {
        return contactInformationRepository.findByEmailContaining(emailPart);
    }
}