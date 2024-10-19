package com.daniorerio.lost_found.services;

import com.daniorerio.lost_found.DAO.LocationDao;
import com.daniorerio.lost_found.DAO.LostItemDao;
import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.services.interfaces.LocationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationDao locationDao;
    private final LostItemDao lostItemDao;


    @Autowired
    public LocationServiceImpl(LocationDao locationDao, LostItemDao lostItemDao) {
        this.locationDao = locationDao;
        this.lostItemDao = lostItemDao;
    }

    @Override
    @Transactional
    public Location createLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public Optional<Location> findById(long id) {
        return locationDao.findById(id);
    }

    @Override
    public Location updateLocation(Location location) {
        return locationDao.updateLocation(location);
    }

    @Override
    @Transactional
    public void deleteLocation(long id) {
        lostItemDao.deleteLostItemsByLocationId(id);
        locationDao.deleteLocation(id);
    }

    @Override
    public List<Location> findAllLocations() {
        return locationDao.findAll();
    }

    @Override
    public List<Location> findByCity(String city) {
        return locationDao.findByCity(city);
    }
}
