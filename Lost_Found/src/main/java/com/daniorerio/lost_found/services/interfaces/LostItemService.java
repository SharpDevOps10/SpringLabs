package com.daniorerio.lost_found.services.interfaces;

import com.daniorerio.lost_found.entities.LostItem;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface LostItemService {
    void addItem(LostItem lostItem) throws SQLException;

    Optional<LostItem> findById(long id);

    void updateItem(LostItem lostItem) throws SQLException;

    void deleteItem(long id);

    List<LostItem> findByItemKeywords(String keywords) throws SQLException;

    List<LostItem> findAllItems();
}