package com.daniorerio.lost_found.repositories;

import com.daniorerio.lost_found.DAO.LocationDao;
import com.daniorerio.lost_found.entities.Location;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository implements LocationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Location> locationRowMapper = (rs, rowNum) -> new Location(
            rs.getLong("id"),
            rs.getString("city"),
            rs.getString("address"),
            rs.getString("zip_code")
    );

    @Override
    public Location addLocation(Location location) {
        String sql = "INSERT INTO locations (city, address, zip_code) VALUES (?, ?, ?) RETURNING id";
        Long generatedId = jdbcTemplate.queryForObject(sql, Long.class,
                location.getCity(),
                location.getAddress(),
                location.getZipCode()
        );
        if (generatedId != null) location.setId(generatedId);

        return location;
    }

    @Override
    public Optional<Location> findById(long id) {
        String sql = "SELECT * FROM locations WHERE id = ?";
        return jdbcTemplate.query(sql, locationRowMapper, id).stream().findFirst();
    }

    @Override
    public List<Location> findAll() {
        String sql = "SELECT * FROM locations";
        return jdbcTemplate.query(sql, locationRowMapper);
    }

    @Override
    public Location updateLocation(Location location) {
        Location existingLocation = findById(location.getId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found"));

        if (location.getCity() != null) existingLocation.setCity(location.getCity());
        if (location.getAddress() != null) existingLocation.setAddress(location.getAddress());
        if (location.getZipCode() != null) existingLocation.setZipCode(location.getZipCode());

        String sql = "UPDATE locations SET city = ?, address = ?, zip_code = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                existingLocation.getCity(),
                existingLocation.getAddress(),
                existingLocation.getZipCode(),
                existingLocation.getId()
        );
        return existingLocation;
    }

    @Override
    public void deleteLocation(long id) {
        String sql = "DELETE FROM locations WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Location> findByCity(String city) {
        String sql = "SELECT * FROM locations WHERE city = ?";
        return jdbcTemplate.query(sql, locationRowMapper, city);
    }

}
