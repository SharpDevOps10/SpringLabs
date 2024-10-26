package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.DTO.LostItemUpdateDTO;
import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.interfaces.LostItemService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/lost-items")
public class LostItemController {

    private final LostItemService lostItemService;

    @Autowired
    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @Operation(summary = "Retrieve a list of lost items", description = "Returns a list of lost items.")
    @GetMapping
    public ResponseEntity<List<LostItem>> listItems() {
        List<LostItem> items = lostItemService.getAllLostItems();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Retrieve a lost item by ID", description = "Returns the details of a lost item based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getItemById(@PathVariable Long id) {
        LostItem lostItem = lostItemService.getLostItemById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
        return ResponseEntity.ok(lostItem);
    }

    @Operation(summary = "Create a new lost item", description = "Creates a new lost item and returns it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<LostItem> createItem(@RequestBody LostItem lostItem) {
        LostItem createdItem = lostItemService.createLostItem(lostItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @Operation(summary = "Update a lost item", description = "Updates information about a lost item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully updated"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<LostItem> partialUpdateItem(@PathVariable long id, @RequestBody LostItemUpdateDTO updateDTO) {
        Optional<LostItem> existingItemOpt = lostItemService.getLostItemById(id);

        if (existingItemOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        LostItem updatedItem = lostItemService.updateLostItem(id, updateDTO);
        return ResponseEntity.ok(updatedItem);
    }

    @Operation(summary = "Delete a lost item", description = "Deletes a lost item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable long id) {
        lostItemService.getLostItemById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lost item not found"));

        lostItemService.deleteLostItem(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Search lost items by keywords", description = "Returns a list of lost items matching the specified keywords.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "204", description = "No items found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<LostItem>> searchItems(@RequestParam("keywords") String keywords) {
        List<LostItem> foundItems = lostItemService.findLostItemsByItemKeywords(keywords);

        if (foundItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lost items found for the given keywords");
        }

        return ResponseEntity.ok(foundItems);
    }

    @Operation(summary = "Search lost items by name", description = "Returns a list of lost items matching the specified name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found successfully"),
            @ApiResponse(responseCode = "404", description = "No items found for the given name")
    })
    @GetMapping("/search/by-name")
    public ResponseEntity<List<LostItem>> findLostItemsByItemName(@RequestParam String itemName) {
        List<LostItem> lostItems = lostItemService.findLostItemsByItemName(itemName);
        if (lostItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lost items found for the given name");
        }
        return ResponseEntity.ok(lostItems);
    }

    @Operation(summary = "Search lost items by description", description = "Returns a list of lost items matching the specified description.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Items found successfully"),
            @ApiResponse(responseCode = "404", description = "No items found for the given description")
    })
    @GetMapping("/search/by-description")
    public ResponseEntity<List<LostItem>> findLostItemsByItemDescription(@RequestParam String description) {
        List<LostItem> lostItems = lostItemService.findLostItemsByItemDescription(description);
        if (lostItems.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No lost items found for the given description");
        }
        return ResponseEntity.ok(lostItems);
    }
}