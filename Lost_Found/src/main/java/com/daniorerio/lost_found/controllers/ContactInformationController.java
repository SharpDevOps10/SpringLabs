package com.daniorerio.lost_found.controllers;

import com.daniorerio.lost_found.DTO.UpdateContactInformationDto;
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
        ContactInformation contact = contactInformationService.getContactInformationById(id)
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
    public ResponseEntity<ContactInformation> updateContact(@PathVariable Long id, @RequestBody UpdateContactInformationDto contactInformation) {
        Optional<ContactInformation> updatedContact = contactInformationService.updateContactInformation(id, contactInformation);

        if (updatedContact.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found");

        return ResponseEntity.ok(updatedContact.get());
    }

    @Operation(summary = "Delete a contact",
            description = "Deletes a contact by its ID and returns the deleted contact.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Contact not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ContactInformation> deleteContact(@PathVariable Long id) {
        ContactInformation contact = contactInformationService.getContactInformationById(id)
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
        return ResponseEntity.ok(contactInformationService.getAllContactInformation());
    }

    @Operation(summary = "Get contacts by first name",
            description = "Retrieves contacts that match the specified first name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of contacts found"),
            @ApiResponse(responseCode = "404", description = "No contacts found with the given first name")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ContactInformation>> getContactsByFirstName(@RequestParam String firstName) {
        List<ContactInformation> contacts = contactInformationService.findContactInformationByFirstName(firstName);

        if (contacts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contacts found with the given first name");
        }

        return ResponseEntity.ok(contacts);
    }

    @GetMapping("/email/{emailPart}")
    public ResponseEntity<List<ContactInformation>> getContactInformationByEmailPart(@PathVariable String emailPart) {
        List<ContactInformation> contactInformation = contactInformationService.findContactInformationByEmailPart(emailPart);

        if (contactInformation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contacts found with the given email");
        }

        return ResponseEntity.ok(contactInformation);
    }

    @GetMapping("/last-name/{lastName}")
    public ResponseEntity<List<ContactInformation>> getContactInformationByLastName(@PathVariable String lastName) {
        List<ContactInformation> contactInformation = contactInformationService.findContactInformationByLastName(lastName);

        if (contactInformation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No contacts found with the given last name");
        }

        return ResponseEntity.ok(contactInformation);
    }
}