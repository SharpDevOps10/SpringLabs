package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.repositories.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostItemService {
    private LostItemRepository lostItemRepository;

    // DI with setter
    @Autowired
    public void setLostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    public LostItem createItem(LostItem lostItem) {
        lostItemRepository.addItem(lostItem);
        return lostItem;
    }

    public void deleteItem(long id) {
        lostItemRepository.findById(id).ifPresent(lostItemRepository::removeItem);
    }

    public List<LostItem> searchItems(String itemName) {
        return lostItemRepository.findByItemName(itemName);
    }

    public Optional<LostItem> findItemById(long id) {
        return lostItemRepository.findById(id);
    }

    public Page<LostItem> listItemsWithPagination(Pageable pageable) {
        return lostItemRepository.findAllItemsPagination(pageable);
    }

    public void updateItem(LostItem updatedItem) {
        lostItemRepository.findById(updatedItem.getId()).ifPresent(existingItem -> {
            existingItem.setContactInformation(updatedItem.getContactInformation());
            existingItem.setLocation(updatedItem.getLocation());
            existingItem.setItemName(updatedItem.getItemName());
            existingItem.setItemDescription(updatedItem.getItemDescription());
            existingItem.setItemKeywords(updatedItem.getItemKeywords());
        });
    }

    public List<LostItem> searchItemsByKeywords(String keywords) {
        return lostItemRepository.findByItemKeywords(keywords);
    }
}
