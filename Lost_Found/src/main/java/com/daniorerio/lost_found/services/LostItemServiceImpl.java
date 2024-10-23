package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.repositories.LostItemRepository;
import com.daniorerio.lost_found.services.interfaces.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostItemServiceImpl implements LostItemService {

    private final LostItemRepository lostItemRepository;

    @Autowired
    public LostItemServiceImpl(LostItemRepository lostItemRepository) {
        this.lostItemRepository = lostItemRepository;
    }

    @Override
    public List<LostItem> getAllLostItems() {
        return (List<LostItem>) lostItemRepository.findAll();
    }

    @Override
    public Optional<LostItem> getLostItemById(Long id) {
        return lostItemRepository.findById(id);
    }

    @Override
    public LostItem createLostItem(LostItem lostItem) {
        return lostItemRepository.save(lostItem);
    }

    @Override
    public LostItem updateLostItem(Long id, LostItem updatedLostItem) {
        if (lostItemRepository.existsById(id)) {
            updatedLostItem.setId(id);
            return lostItemRepository.save(updatedLostItem);
        }
        return null;
    }

    @Override
    public void deleteLostItem(Long id) {
        lostItemRepository.deleteById(id);
    }

    @Override
    public List<LostItem> findLostItemsByItemName(String itemName) {
        return lostItemRepository.findByItemName(itemName);
    }

    @Override
    public List<LostItem> findLostItemsByItemDescription(String description) {
        return lostItemRepository.findByItemDescription(description);
    }

    @Override
    public List<LostItem> findLostItemsByContactInformationFirstName(String firstName) {
        return lostItemRepository.findByContactInformation_FirstName(firstName);
    }

    @Override
    public List<LostItem> findLostItemsByLocationCity(String city) {
        return lostItemRepository.findByLocation_City(city);
    }

    @Override
    public List<LostItem> findLostItemsByItemKeywords(String keyword) {
        return lostItemRepository.findByItemKeywordsContaining(keyword);
    }
}