package com.happynarae.happyclub.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClubCreateForm(
        @NotBlank(message = "동호회 이름은 필수입니다.")
        String clubName,
        String description,
        @NotNull(message = "매니저 선택은 필수입니다.")
        Long managerId
) {}