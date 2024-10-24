package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    // @Query
    @Query("SELECT l FROM Location l WHERE l.city = :city")
    List<Location> findByCity(String city);

    // NamedQuery
    List<Location> findByAddress(String address);

    // Auto
    List<Location> findByZipCode(String zipCode);
}
