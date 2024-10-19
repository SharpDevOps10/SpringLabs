package com.daniorerio.lost_found.DAO;

import com.daniorerio.lost_found.entities.LostItem;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LostItemDao {
    void addItem(LostItem lostItem) throws SQLException;

    Optional<LostItem> findById(long id);

    List<LostItem> findAllItems();

    void updateItem(LostItem lostItem) throws SQLException;

    void deleteItem(long id);

    List<LostItem> findByItemKeywords(String keywords) throws SQLException;

    void deleteLostItemsByLocationId(long locationId);

    void deleteLostItemsByContactId(long contactId);
}
