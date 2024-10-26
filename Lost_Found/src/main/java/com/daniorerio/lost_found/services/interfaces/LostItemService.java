package com.daniorerio.lost_found.services.interfaces;

import com.daniorerio.lost_found.DTO.LostItemUpdateDTO;
import com.daniorerio.lost_found.entities.LostItem;

import java.util.List;
import java.util.Optional;

public interface LostItemService {
    List<LostItem> getAllLostItems();

    Optional<LostItem> getLostItemById(Long id);

    LostItem createLostItem(LostItem lostItem);

    LostItem updateLostItem(Long id, LostItemUpdateDTO updateDTO);

    void deleteLostItem(Long id);

    List<LostItem> findLostItemsByItemName(String itemName);

    List<LostItem> findLostItemsByItemDescription(String description);

    List<LostItem> findLostItemsByItemKeywords(String keyword);
}