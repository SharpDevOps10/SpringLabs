package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.DTO.UpdateLocationDto;
import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.services.interfaces.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Create a new location", description = "Creates a new location entity and returns the created entity with the generated ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created", content = @Content(schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        Location createdLocation = locationService.createLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocation);
    }

    @Operation(summary = "Get location by ID", description = "Retrieves a location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found", content = @Content(schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable long id) {
        Location location = locationService.getLocationById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Update a location", description = "Updates an existing location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable long id, @RequestBody UpdateLocationDto updateLocationDto) {
        Optional<Location> updatedLocationOpt = locationService.updateLocation(id, updateLocationDto);

        if (updatedLocationOpt.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");

        return ResponseEntity.ok(updatedLocationOpt.get());
    }

    @Operation(summary = "Delete a location", description = "Deletes a location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable long id) {
        Location location = locationService.getLocationById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));

        locationService.deleteLocation(id);

        return ResponseEntity.ok(location);
    }

    @Operation(summary = "Get all locations", description = "Retrieves all locations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of locations retrieved successfully", content = @Content(schema = @Schema(implementation = Location.class)))
    })
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @Operation(summary = "Search locations by city", description = "Finds locations by the provided city name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found", content = @Content(schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "No locations found for the given city", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<Location>> getLocationsByCity(@RequestParam String city) {
        List<Location> locations = locationService.findLocationsByCity(city);

        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No locations found for the given city");
        }

        return ResponseEntity.ok(locations);
    }


    @Operation(summary = "Get locations by zip code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found locations"),
            @ApiResponse(responseCode = "404", description = "Locations not found")
    })
    @GetMapping("/zip-code/{zipCode}")
    public ResponseEntity<List<Location>> getLocationsByZipCode(@PathVariable String zipCode) {
        List<Location> locations = locationService.findLocationsByZipCode(zipCode);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locations not found for zip code: " + zipCode);
        }
        return ResponseEntity.ok(locations);
    }

    @Operation(summary = "Get locations by address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found locations"),
            @ApiResponse(responseCode = "404", description = "Locations not found")
    })
    @GetMapping("/address/{address}")
    public ResponseEntity<List<Location>> getLocationsByAddress(@PathVariable String address) {
        List<Location> locations = locationService.findLocationsByAddress(address);
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Locations not found for address: " + address);
        }
        return ResponseEntity.ok(locations);
    }
}