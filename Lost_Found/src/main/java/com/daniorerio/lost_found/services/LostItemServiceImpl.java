package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DAO.ContactInformationDao;
import com.daniorerio.lost_found.DAO.LocationDao;
import com.daniorerio.lost_found.DAO.LostItemDao;
import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.interfaces.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class LostItemServiceImpl implements LostItemService {

    private final LostItemDao lostItemDao;

    @Autowired
    public LostItemServiceImpl(LostItemDao lostItemDao) {
        this.lostItemDao = lostItemDao;
    }

    @Override
    @Transactional // Транзакційний метод
    public void addItem(LostItem lostItem) throws SQLException {
        lostItemDao.addItem(lostItem);
    }

    @Override
    public Optional<LostItem> findById(long id) {
        return lostItemDao.findById(id);
    }

    @Override
    public void updateItem(LostItem lostItem) throws SQLException {
        lostItemDao.updateItem(lostItem);
    }

    @Override
    public void deleteItem(long id) {
        lostItemDao.deleteItem(id);
    }

    @Override
    public List<LostItem> findByItemKeywords(String keywords) throws SQLException {
        return lostItemDao.findByItemKeywords(keywords);
    }

    @Override
    public List<LostItem> findAllItems() {
        return lostItemDao.findAllItems();
    }
}