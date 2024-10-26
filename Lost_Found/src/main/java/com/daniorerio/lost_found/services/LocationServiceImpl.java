package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DTO.UpdateLocationDto;
import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.mapper.LocationMapper;
import com.daniorerio.lost_found.repositories.LocationRepository;
import com.daniorerio.lost_found.repositories.LostItemRepository;
import com.daniorerio.lost_found.services.interfaces.LocationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LostItemRepository lostItemRepository;


    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, LostItemRepository lostItemRepository) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.lostItemRepository = lostItemRepository;
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

    @Transactional
    @Override
    public void deleteLocation(Long id) {
        lostItemRepository.deleteByLocationId(id);
        locationRepository.deleteById(id);
    }

    @Override
    public List<Location> findLocationsByCity(String city) {
        return locationRepository.findByCity(city);
    }

    @Override
    public List<Location> findLocationsByAddress(String address) {
        return locationRepository.findByAddress(address);
    }

    @Override
    public List<Location> findLocationsByZipCode(String zipCode) {
        return locationRepository.findByZipCode(zipCode);
    }
}
