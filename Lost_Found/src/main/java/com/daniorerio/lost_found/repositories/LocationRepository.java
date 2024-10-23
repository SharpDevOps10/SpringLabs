package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    // 5.1.1 Пошук за допомогою @Query (JPQL)
    @Query("SELECT l FROM Location l WHERE l.city = :city")
    List<Location> findByCity(String city);

    // 5.1.2 Пошук за допомогою @NamedQuery (визначений у @Entity)
    List<Location> findByAddress(String address);

    // 5.2 Автоматично згенеровані методи на основі імені
    List<Location> findByCityContaining(String cityFragment);

    List<Location> findByZipCode(String zipCode);
}
