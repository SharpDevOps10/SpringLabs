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

    public List<LostItem> searchItems(String keyword) {
        return lostItemRepository.findByKeyword(keyword);
    }

    public List<LostItem> listAllItems() {
        return lostItemRepository.findAllItems();
    }
}
