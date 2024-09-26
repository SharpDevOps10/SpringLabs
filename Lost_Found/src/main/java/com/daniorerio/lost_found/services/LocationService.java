package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    // DI with field
    @Autowired
    private LocationRepository locationRepository;

    public void addLocation(Location location) {
        locationRepository.addLocation(location);
    }

    public List<Location> getAllLocations() {
        return locationRepository.findAllLocations();
    }

    public Location getLocationByCity(String city) {
        return locationRepository.findByCity(city);
    }
}
