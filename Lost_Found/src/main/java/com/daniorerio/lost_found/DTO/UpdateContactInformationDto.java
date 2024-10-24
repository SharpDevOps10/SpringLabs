package com.daniorerio.lost_found.DTO;

import java.util.Optional;

public record UpdateContactInformationDto(
        Optional<String> firstName,
        Optional<String> lastName,
        Optional<String> email,
        Optional<String> phoneNumber
) {
}