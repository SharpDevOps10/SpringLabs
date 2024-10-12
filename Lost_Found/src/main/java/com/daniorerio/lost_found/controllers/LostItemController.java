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
    @PatchMapping("/update")
    public ResponseEntity<LostItem> updateItem(@RequestBody LostItem lostItem) {
        List<LostItem> existingItems = lostItemService.searchItems(lostItem.getItemName());
        if (!existingItems.isEmpty()) {
            LostItem existingItem = existingItems.getFirst();
            existingItem.setItemDescription(lostItem.getItemDescription());
            existingItem.setItemKeywords(lostItem.getItemKeywords());
            lostItemService.updateItem(existingItem);
            return ResponseEntity.ok(existingItem);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a lost item",
            description = "Deletes a lost item by its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/delete/{itemName}")
    public ResponseEntity<LostItem> deleteItem(@PathVariable("itemName") String itemName) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) {
            LostItem deletedItem = items.getFirst();
            lostItemService.deleteItem(deletedItem);
            return ResponseEntity.ok(deletedItem);
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