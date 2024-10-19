package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DAO.ContactInformationDao;
import com.daniorerio.lost_found.DAO.LostItemDao;
import com.daniorerio.lost_found.entities.ContactInformation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daniorerio.lost_found.services.interfaces.ContactInformationService;

import java.util.List;
import java.util.Optional;

@Service
public class ContactInformationServiceImpl implements ContactInformationService {

    private final ContactInformationDao contactInformationDao;
    private final LostItemDao lostItemDao;

    @Autowired
    public ContactInformationServiceImpl(ContactInformationDao contactInformationDao, LostItemDao lostItemDao) {
        this.contactInformationDao = contactInformationDao;
        this.lostItemDao = lostItemDao;
    }

    @Override
    @Transactional
    public ContactInformation createContactInformation(ContactInformation contactInformation) {
        return contactInformationDao.createContactInformation(contactInformation);
    }

    @Override
    public Optional<ContactInformation> findById(Long id) {
        return contactInformationDao.findById(id);
    }

    @Override
    public ContactInformation updateContactInformation(ContactInformation contactInformation) {
        return contactInformationDao.updateContactInformation(contactInformation);
    }

    @Override
    @Transactional
    public void deleteContactInformation(Long id) {
        lostItemDao.deleteLostItemsByContactId(id);
        contactInformationDao.deleteContactInformation(id);
    }

    @Override
    public List<ContactInformation> findByFirstName(String firstName) {
        return contactInformationDao.findByFirstName(firstName);
    }

    @Override
    public List<ContactInformation> findAll() {
        return contactInformationDao.findAll();
    }
}