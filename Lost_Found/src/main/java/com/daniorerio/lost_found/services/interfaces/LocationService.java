package com.daniorerio.lost_found.services.interfaces;

import com.daniorerio.lost_found.entities.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    Location createLocation(Location location);

    Optional<Location> findById(long id);

    Location updateLocation(Location location);

    void deleteLocation(long id);

    List<Location> findAllLocations();

    List<Location> findByCity(String city);
}