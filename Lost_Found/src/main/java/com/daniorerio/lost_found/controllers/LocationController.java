package com.daniorerio.lost_found.controllers;

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
        return locationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Update a location", description = "Updates an existing location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable long id, @RequestBody Location location) {
        location.setId(id);
        Location updatedLocation = locationService.updateLocation(location);
        return ResponseEntity.ok(updatedLocation); // Возвращаем обновленную сущность
    }

    @Operation(summary = "Delete a location", description = "Deletes a location by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Location> deleteLocation(@PathVariable long id) {
        Optional<Location> location = locationService.findById(id);
        if (location.isPresent()) {
            locationService.deleteLocation(id);
            return ResponseEntity.ok(location.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get all locations", description = "Retrieves all locations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of locations", content = @Content(schema = @Schema(implementation = Location.class)))
    })
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.findAllLocations());
    }

    @Operation(summary = "Search locations by city", description = "Finds locations by the provided city name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found", content = @Content(schema = @Schema(implementation = Location.class))),
            @ApiResponse(responseCode = "404", description = "No locations found for the given city", content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<List<Location>> getLocationsByCity(@RequestParam String city) {
        return ResponseEntity.ok(locationService.findByCity(city));
    }
}