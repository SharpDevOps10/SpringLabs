package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.LostItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Retrieve a list of lost items",
            description = "Returns a list of lost items with pagination support.")
    @GetMapping
    public ResponseEntity<Page<LostItem>> listItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LostItem> itemsPage = lostItemService.listItemsWithPagination(pageable);
        return ResponseEntity.ok(itemsPage);
    }

    @Operation(summary = "Retrieve a lost item by ID",
            description = "Returns the details of a lost item based on its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item found successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LostItem> getItemById(@PathVariable Long id) {
        Optional<LostItem> lostItem = lostItemService.findItemById(id);
        return lostItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create a new lost item",
            description = "Creates a new lost item and returns it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PostMapping
    public ResponseEntity<LostItem> createItem(@RequestBody LostItem lostItem) {
        LostItem createdItem = lostItemService.createItem(lostItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @Operation(summary = "Update a lost item",
            description = "Updates information about a lost item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully updated"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @PatchMapping("/update/{id}")
    public ResponseEntity<LostItem> updateItem(@PathVariable long id, @RequestBody LostItem lostItem) {
        Optional<LostItem> existingItem = lostItemService.findItemById(id);
        if (existingItem.isPresent()) {
            LostItem updatedItem = existingItem.get();
            updatedItem.setItemDescription(lostItem.getItemDescription());
            updatedItem.setItemName(lostItem.getItemName());
            updatedItem.setItemKeywords(lostItem.getItemKeywords());
            updatedItem.setLocation(lostItem.getLocation());
            updatedItem.setContactInformation(lostItem.getContactInformation());
            lostItemService.updateItem(updatedItem);
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a lost item",
            description = "Deletes a lost item by its id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Optional<LostItem>> deleteItem(@PathVariable long id) {
        Optional<LostItem> existingItem = lostItemService.findItemById(id);
        if (existingItem.isPresent()) {
            lostItemService.deleteItem(id);
            return ResponseEntity.ok(existingItem);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Search lost items by keywords",
            description = "Returns a list of lost items matching the specified keywords.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Search completed successfully"),
            @ApiResponse(responseCode = "204", description = "No items found")
    })
    @GetMapping("/search")
    public ResponseEntity<List<LostItem>> searchItems(@RequestParam("keywords") String keywords) {
        List<LostItem> foundItems = lostItemService.searchItemsByKeywords(keywords);
        if (foundItems.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(foundItems);
    }
}