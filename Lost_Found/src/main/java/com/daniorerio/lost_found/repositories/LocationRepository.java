package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.entities.Location;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationRepository {
    private final List<Location> locations = new ArrayList<>();

    public void addLocation(Location location) {
        locations.add(location);
    }

    public List<Location> findAllLocations() {
        return locations;
    }

    public Location findByCity(String city) {
        return locations.stream()
                .filter(location -> location.getCity().equalsIgnoreCase(city))
                .findFirst()
                .orElse(null);
    }
}
