package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.LostItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LostItemRepository {
    private final List<LostItem> lostItems = new ArrayList<>();

    public void addItem(LostItem lostItem) {
        lostItems.add(lostItem);
    }

    public void removeItem(LostItem lostItem) {
        lostItems.remove(lostItem);
    }

    public List<LostItem> findByKeyword(String keyword) {
        return lostItems.stream()
                .filter(item -> item.getItemKeywords().contains(keyword))
                .toList();
    }

    public List<LostItem> findAllItems() {
        return lostItems;
    }
}
