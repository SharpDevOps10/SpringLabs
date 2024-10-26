package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.LostItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostItemRepository extends CrudRepository<LostItem, Long> {

    //Query
    @Query("SELECT li FROM LostItem li WHERE li.itemName = :itemName")
    List<LostItem> findByItemName(String itemName);

    // @NamedQuery
    List<LostItem> findByItemDescription(String description);

    // Auto
    List<LostItem> findByItemKeywordsContaining(String keyword);

    void deleteByLocationId(Long locationId);
}