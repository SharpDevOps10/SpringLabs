package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.entities.ContactInformation;
import com.daniorerio.lost_found.services.interfaces.ContactInformationService;
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
@RequestMapping("/contacts")
public class ContactInformationController {

    private final ContactInformationService contactInformationService;

    @Autowired
    public ContactInformationController(ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    @Operation(summary = "Create a new contact",
            description = "Creates a new contact with the provided information and returns the created contact.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Contact created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ContactInformation> createContact(@RequestBody ContactInformation contactInformation) {
        ContactInformation createdContact = contactInformationService.createContactInformation(contactInformation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContact);
    }

    @Operation(summary = "Get contact by ID",
            description = "Retrieves a contact by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact found"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContactInformation> getContactById(@PathVariable Long id) {
        ContactInformation contact = contactInformationService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "Update a contact",
            description = "Updates an existing contact with the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact updated successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ContactInformation> updateContact(@PathVariable Long id, @RequestBody ContactInformation contactInformation) {
        contactInformation.setId(id);
        ContactInformation updatedInfo = contactInformationService.updateContactInformation(contactInformation);

        if (updatedInfo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found");

        return ResponseEntity.ok(updatedInfo);
    }

    @Operation(summary = "Delete a contact",
            description = "Deletes a contact by its ID and returns the deleted contact.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ContactInformation> deleteContact(@PathVariable Long id) {
        ContactInformation contact = contactInformationService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contactInformationService.deleteContactInformation(id);

        return ResponseEntity.ok(contact);
    }

    @Operation(summary = "Get all contacts",
            description = "Retrieves a list of all contacts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of contacts retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ContactInformation>> getAllContacts() {
        return ResponseEntity.ok(contactInformationService.findAll());
    }

    @Operation(summary = "Get contacts by first name",
            description = "Retrieves contacts that match the specified first name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of contacts found"),
            @ApiResponse(responseCode = "404", description = "No contacts found with the given first name")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ContactInformation>> getContactsByFirstName(@RequestParam String firstName) {
        List<ContactInformation> contacts = contactInformationService.findByFirstName(firstName);

        if (contacts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contacts found with the given first name");
        }

        return ResponseEntity.ok(contacts);
    }
}