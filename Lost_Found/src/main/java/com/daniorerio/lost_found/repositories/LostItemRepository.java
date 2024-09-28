package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.LostItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LostItemRepository {
    private final List<LostItem> lostItems = new ArrayList<>();

    public void addItem(LostItem lostItem) {
        lostItems.add(lostItem);
    }

    public void removeItem(LostItem lostItem) {
        lostItems.remove(lostItem);
    }

    public List<LostItem> findAllItems() {
        return lostItems;
    }

    public List<LostItem> findByItemName(String itemName) {
        return lostItems.stream()
                .filter(item -> item.getItemName().equals(itemName))
                .toList();
    }

    public List<LostItem> findByItemKeywords(String keywords) {
        List<String> keywordList = Arrays.stream(keywords.split(","))
                .map(String::trim)
                .toList();

        return lostItems.stream()
                .filter(item -> item.getItemKeywords() != null &&
                        keywordList.stream()
                                .allMatch(keyword -> item.getItemKeywords().stream()
                                        .anyMatch(itemKeyword -> itemKeyword.trim().equalsIgnoreCase(keyword)))
                )
                .collect(Collectors.toList());
    }
}
