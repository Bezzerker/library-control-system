package com.barikhashvili.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    // Октрывается главная страница
    @GetMapping()
    public String showMainPage() {
        return "/main/index";
    }
}
