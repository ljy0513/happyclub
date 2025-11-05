package com.happynarae.happyclub.web;

import java.time.LocalDateTime;

public record ClubDetailDto(
        Long clubId,
        String clubName,
        String managerName,
        String description,
        boolean active,
        LocalDateTime createdDate
) {}