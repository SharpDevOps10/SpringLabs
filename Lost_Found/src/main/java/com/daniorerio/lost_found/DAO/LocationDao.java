package com.daniorerio.lost_found.DAO;

import com.daniorerio.lost_found.entities.Location;

import java.util.List;
import java.util.Optional;

public interface LocationDao {
    Location addLocation(Location location);

    Optional<Location> findById(long id);

    List<Location> findAll();

    Location updateLocation(Location location);

    void deleteLocation(long id);

    List<Location> findByCity(String city);
}