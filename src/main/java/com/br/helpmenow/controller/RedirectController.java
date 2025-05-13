package com.br.helpmenow.controller;

import com.br.helpmenow.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping("/redirect")
    public String redirectAfterLogin(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String role = userDetails.getUser().getType().name();

        return switch (role) {
            case "ADMIN" -> "redirect:/admin/index";
            case "CLIENT" -> "redirect:/client/index";
            default -> "redirect:/login?error";
        };
    }
}
