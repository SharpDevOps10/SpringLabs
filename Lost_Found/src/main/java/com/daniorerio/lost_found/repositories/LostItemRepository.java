package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.LostItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<LostItem> findAllItemsPagination(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), lostItems.size());

        List<LostItem> itemsPage = lostItems.subList(start, end);
        return new PageImpl<>(itemsPage, pageable, lostItems.size());
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
