package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lost-items")
public class LostItemController {

    private final LostItemService lostItemService;

    @Autowired
    public LostItemController(LostItemService lostItemService) {
        this.lostItemService = lostItemService;
    }

    @GetMapping
    public String listItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LostItem> itemsPage = lostItemService.listItemsWithPagination(pageable);

        model.addAttribute("items", itemsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", itemsPage.getTotalPages());

        return "lost_items/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("lostItem", new LostItem(null, null, null));
        return "lost_items/create";
    }

    @PostMapping
    public String createItem(@ModelAttribute LostItem lostItem) {
        lostItemService.createItem(lostItem);
        return "redirect:/lost-items";
    }

    @GetMapping("/edit/{itemName}")
    public String showEditForm(@PathVariable("itemName") String itemName, Model model) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) {
            model.addAttribute("lostItem", items.getFirst());
            return "lost_items/edit";
        }
        return "redirect:/lost-items";
    }

    @PatchMapping("/update")
    public String updateItem(@ModelAttribute LostItem lostItem) {
        List<LostItem> existingItems = lostItemService.searchItems(lostItem.getItemName());
        if (!existingItems.isEmpty()) {
            LostItem existingItem = existingItems.getFirst();
            existingItem.setItemDescription(lostItem.getItemDescription());
            existingItem.setItemKeywords(lostItem.getItemKeywords());
            lostItemService.updateItem(existingItem);
        }
        return "redirect:/lost-items";
    }

    @GetMapping("/delete/{itemName}")
    public String deleteItem(@PathVariable("itemName") String itemName) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) lostItemService.deleteItem(items.getFirst());
        return "redirect:/lost-items";
    }

    @GetMapping("/search")
    public String searchItems(@RequestParam("keywords") String keywords, Model model) {
        List<LostItem> foundItems = lostItemService.searchItemsByKeywords(keywords);
        model.addAttribute("items", foundItems);
        return "lost_items/list";
    }
}
