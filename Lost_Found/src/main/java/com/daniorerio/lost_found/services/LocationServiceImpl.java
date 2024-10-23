package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DTO.UpdateLocationDto;
import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.mapper.LocationMapper;
import com.daniorerio.lost_found.repositories.LocationRepository;
import com.daniorerio.lost_found.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public List<Location> getAllLocations() {
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> updateLocation(Long id, UpdateLocationDto updateLocationDto) {
        Optional<Location> existingLocationOpt = locationRepository.findById(id);

        existingLocationOpt.ifPresent(existingLocation -> {
            locationMapper.updateLocationFromDto(updateLocationDto, existingLocation);
            locationRepository.save(existingLocation);
        });

        return existingLocationOpt;
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public List<Location> findLocationsByCity(String city) {
        return locationRepository.findByCity(city);
    }

    @Override
    public List<Location> findLocationsByZipCode(String zipCode) {
        return locationRepository.findByZipCode(zipCode);
    }
}
