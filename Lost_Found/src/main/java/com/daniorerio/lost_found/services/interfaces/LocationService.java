package com.daniorerio.lost_found.services.interfaces;

import com.daniorerio.lost_found.DTO.UpdateLocationDto;
import com.daniorerio.lost_found.entities.Location;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<Location> getAllLocations();

    Optional<Location> getLocationById(Long id);

    Location createLocation(Location location);

    Optional<Location> updateLocation(Long id, UpdateLocationDto updateLocationDto);

    void deleteLocation(Long id);

    List<Location> findLocationsByCity(String city);

    List<Location> findLocationsByZipCode(String zipCode);

    List<Location> findLocationsByAddress(String address);
}