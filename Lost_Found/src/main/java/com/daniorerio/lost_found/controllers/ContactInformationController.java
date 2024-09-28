package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.entities.ContactInformation;
import com.daniorerio.lost_found.entities.LostItem;
import com.daniorerio.lost_found.services.ContactInformationService;
import com.daniorerio.lost_found.services.LostItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/contact-information")
public class ContactInformationController {

    private final LostItemService lostItemService;
    private final ContactInformationService contactInformationService;

    @Autowired
    public ContactInformationController(LostItemService lostItemService, ContactInformationService contactInformationService) {
        this.lostItemService = lostItemService;
        this.contactInformationService = contactInformationService;
    }

    @GetMapping("/update/{itemName}")
    public String showUpdateContactForm(@PathVariable("itemName") String itemName, Model model) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) {
            model.addAttribute("lostItem", items.getFirst());
            model.addAttribute("contactInformation", new ContactInformation(null, null, null, null));
            return "lost_items/update_contact";
        }
        return "redirect:/lost-items";
    }

    @PostMapping("/update")
    public String updateContactInformation(@ModelAttribute ContactInformation contactInformation, @RequestParam("itemName") String itemName) {
        List<LostItem> items = lostItemService.searchItems(itemName);
        if (!items.isEmpty()) {
            LostItem lostItem = items.getFirst();
            lostItem.setContactInformation(contactInformation);
            lostItemService.updateItem(lostItem);
            contactInformationService.createContactInformation(contactInformation);
        }
        return "redirect:/locations/update/" + itemName;
    }

    @GetMapping("/delete/{phoneNumber}")
    public String deleteContactInformation(@PathVariable("phoneNumber") String phoneNumber) {
        contactInformationService.deleteContactInformationByPhoneNumber(phoneNumber);
        return "redirect:/lost-items";
    }
}
