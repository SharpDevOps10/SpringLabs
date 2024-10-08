package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.repositories.LostItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostItemService {
    private LostItemRepository lostItemRepository;

    // DI with setter
    @Autowired
    public void setLostItemService(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    public void createItem(LostItem lostItem) {
        lostItemRepository.addItem(lostItem);
    }

    public void deleteItem(LostItem lostItem) {
        lostItemRepository.removeItem(lostItem);
    }

    public List<LostItem> searchItems(String itemName) {
        return lostItemRepository.findByItemName(itemName);
    }

    public List<LostItem> listAllItems() {
        return lostItemRepository.findAllItems();
    }

    public void updateItem(LostItem updatedItem) {
        List<LostItem> items = lostItemRepository.findByItemName(updatedItem.getItemName());
        if (!items.isEmpty()) {
            LostItem existingItem = items.get(0);
            existingItem.setContactInformation(updatedItem.getContactInformation());
        }
    }

    public List<LostItem> searchItemsByKeywords(String keywords) {
        return lostItemRepository.findByItemKeywords(keywords);
    }
}
