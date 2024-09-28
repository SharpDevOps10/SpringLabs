package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.entities.Location;
import com.daniorerio.lost_found.services.LocationService;
import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;
    private final LostItemService lostItemService;

    @Autowired
    public LocationController(LocationService locationService, LostItemService lostItemService) {
        this.locationService = locationService;
        this.lostItemService = lostItemService;
    }

    @GetMapping("/update/{itemName}")
    public String showUpdateLocationForm(@PathVariable("itemName") String itemName, Model model) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) {
            model.addAttribute("lostItem", items.getFirst());
            model.addAttribute("location", new Location(null, null, null));
            return "lost_items/update_location";
        }
        return "redirect:/lost-items";
    }

    @PostMapping("/update")
    public String updateLocation(@ModelAttribute Location location, @RequestParam("itemName") String itemName) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) {
            LostItem lostItem = items.getFirst();
            lostItem.setLocation(location);
            lostItemService.updateItem(lostItem);
            locationService.addLocation(location);
        }
        return "redirect:/lost-items";
    }
}
