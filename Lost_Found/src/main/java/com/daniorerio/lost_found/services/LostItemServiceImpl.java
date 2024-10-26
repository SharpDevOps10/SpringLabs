package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DTO.LostItemUpdateDTO;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.repositories.ContactInformationRepository;
import com.daniorerio.lost_found.repositories.LocationRepository;
import com.daniorerio.lost_found.repositories.LostItemRepository;
import com.daniorerio.lost_found.services.interfaces.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LostItemServiceImpl implements LostItemService {

    private final LostItemRepository lostItemRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public LostItemServiceImpl(LostItemRepository lostItemRepository, ContactInformationRepository contactInformationRepository, LocationRepository locationRepository) {
        this.lostItemRepository = lostItemRepository;
        this.contactInformationRepository = contactInformationRepository;
        this.locationRepository = locationRepository;
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
    public LostItem updateLostItem(Long id, LostItemUpdateDTO updateDTO) {
        LostItem existingItem = lostItemRepository.findById(id).orElse(null);

        if (existingItem == null) return null;

        if (updateDTO.getItemName() != null) {
            existingItem.setItemName(updateDTO.getItemName());
        }
        if (updateDTO.getItemDescription() != null) {
            existingItem.setItemDescription(updateDTO.getItemDescription());
        }
        if (updateDTO.getItemKeywords() != null) {
            existingItem.setItemKeywords(updateDTO.getItemKeywords());
        }
        if (updateDTO.getContactInformationId() != null) {
            contactInformationRepository.findById(updateDTO.getContactInformationId())
                    .ifPresent(existingItem::setContactInformation);
        }
        if (updateDTO.getLocationId() != null) {
            locationRepository.findById(updateDTO.getLocationId())
                    .ifPresent(existingItem::setLocation);
        }

        return lostItemRepository.save(existingItem);
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
    public List<LostItem> findLostItemsByItemKeywords(String keyword) {
        return lostItemRepository.findByItemKeywordsContaining(keyword);
    }
}