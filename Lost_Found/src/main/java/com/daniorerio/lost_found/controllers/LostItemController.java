package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.interfaces.LostItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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

    @Operation(summary = "Retrieve a list of lost items", description = "Returns a list of lost items with pagination support.")
    @GetMapping
    public ResponseEntity<List<LostItem>> listItems() {
        List<LostItem> items = lostItemService.findAllItems();
        return ResponseEntity.ok(items);
    }

    @Operation(summary = "Retrieve a lost item by ID", description = "Returns the details of a lost item based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getItemById(@PathVariable Long id) {
        Optional<LostItem> lostItem = lostItemService.findById(id);
        return lostItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create a new lost item", description = "Creates a new lost item and returns it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<LostItem> createItem(@RequestBody LostItem lostItem) throws SQLException {
        lostItemService.addItem(lostItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(lostItem);
    }

    @Operation(summary = "Update a lost item", description = "Updates information about a lost item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully updated"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<LostItem> updateItem(@PathVariable long id, @RequestBody LostItem lostItem) throws SQLException {
        Optional<LostItem> existingItem = lostItemService.findById(id);
        if (existingItem.isPresent()) {
            lostItem.setId(id);
            lostItemService.updateItem(lostItem);
            return ResponseEntity.ok(lostItem);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a lost item", description = "Deletes a lost item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<LostItem> deleteItem(@PathVariable long id) {
        Optional<LostItem> existingItem = lostItemService.findById(id);
        if (existingItem.isPresent()) {
            LostItem itemToDelete = existingItem.get();
            lostItemService.deleteItem(id);
            return ResponseEntity.ok(itemToDelete);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Search lost items by keywords", description = "Returns a list of lost items matching the specified keywords.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "204", description = "No items found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<LostItem>> searchItems(@RequestParam("keywords") String keywords) throws SQLException {
        List<LostItem> foundItems = lostItemService.findByItemKeywords(keywords);
        if (foundItems.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(foundItems);
    }
}