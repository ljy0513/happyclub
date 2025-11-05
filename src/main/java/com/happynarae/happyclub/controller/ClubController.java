package com.happynarae.happyclub.controller;

import com.happynarae.happyclub.service.ClubService;
import com.happynarae.happyclub.web.ClubCreateForm;
import com.happynarae.happyclub.web.ClubDetailDto;
import com.happynarae.happyclub.web.ClubListItemDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/clubs")
public class ClubController {
    private final ClubService clubService;

    @GetMapping
    public String list(Model model) {
        List<ClubListItemDto> clubs = clubService.getClubList();
        model.addAttribute("clubs", clubs);
        return "club/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new ClubCreateForm("", "", null));
        return "club/new";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") ClubCreateForm form,
                         BindingResult errors) {
        if (errors.hasErrors()) {
            return "club/new";
        }
        Long id = clubService.createClub(form);
        return "redirect:/clubs/" + id;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        ClubDetailDto dto = clubService.getClubDetail(id);
        model.addAttribute("club", dto);
        return "club/detail";
    }
}
