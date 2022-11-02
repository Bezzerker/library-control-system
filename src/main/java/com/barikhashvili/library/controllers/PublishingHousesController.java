package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.PublishingHouseDAO;
import com.barikhashvili.library.models.Author;
import com.barikhashvili.library.models.PublishingHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("publishing-houses")
public class PublishingHousesController {
    private final PublishingHouseDAO publishingHouseDAO;

    @Autowired
    public PublishingHousesController(PublishingHouseDAO publishingHouseDAO) {
        this.publishingHouseDAO = publishingHouseDAO;
    }

    // Получение страницы со списком авторов
    @GetMapping()
    public String showPublishingHouses(Model model) {
        model.addAttribute("houses", publishingHouseDAO.getAllPublishingHouses());
        return "/publishing-house/list";
    }

    @GetMapping("/new")
    public String showPublishingHouseAddingForm(@ModelAttribute("house") PublishingHouse publishingHouse) {
        return "/publishing-house/form";
    }

    @PostMapping()
    public String addPublishingHouseToDatabase(@ModelAttribute("house") PublishingHouse publishingHouse) {
        publishingHouseDAO.addPublishingHouse(publishingHouse);
        return "redirect:/publishing-houses";
    }

    @GetMapping("/{id}")
    public String showPublishingHouseInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("house", publishingHouseDAO.getPublishingHouse(id));
        return "/publishing-house/info";
    }

    @GetMapping("/{id}/edit")
    public String showEditPublishingHouseInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("house", publishingHouseDAO.getPublishingHouse(id));
        return "/publishing-house/edit";
    }

    @PatchMapping("/{id}")
    public String editPublishingHouseInfo(@ModelAttribute("house") PublishingHouse publishingHouse,
                                 @PathVariable("id") int id) {
        publishingHouseDAO.changePublishingHouseInfo(publishingHouse);
        return "redirect:/publishing-houses";
    }

    @DeleteMapping("/{id}")
    public String deletePublishingHouse(@PathVariable int id) {
        publishingHouseDAO.deletePublishingHouse(id);
        return "redirect:/publishing-houses";
    }
}
