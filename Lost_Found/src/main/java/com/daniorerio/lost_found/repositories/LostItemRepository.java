package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.LostItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostItemRepository extends CrudRepository<LostItem, Long> {

    @Query("SELECT li FROM LostItem li WHERE li.itemName = :itemName")
    List<LostItem> findByItemName(String itemName);

    // 5.1.2 Пошук за допомогою @NamedQuery (визначений у @Entity)
    List<LostItem> findByItemDescription(String description);

    // 5.2 Автоматично згенеровані методи на основі імені
    List<LostItem> findByContactInformation_FirstName(String firstName);

    List<LostItem> findByLocation_City(String city);

    // Пошук за ключовими словами
    List<LostItem> findByItemKeywordsContaining(String keyword);
}
