package com.happynarae.happyclub.web;

import java.time.LocalDateTime;

public record ClubListItemDto(
        Long clubId,
        String clubName,
        String managerName,
        LocalDateTime createdDate
) {}
