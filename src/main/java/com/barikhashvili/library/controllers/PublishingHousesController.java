package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.BookDAO;
import com.barikhashvili.library.dao.PublishingHouseDAO;
import com.barikhashvili.library.models.PublishingHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("publishing-houses")
public class PublishingHousesController {
    private final PublishingHouseDAO publishingHouseDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PublishingHousesController(PublishingHouseDAO publishingHouseDAO, BookDAO bookDAO) {
        this.publishingHouseDAO = publishingHouseDAO;
        this.bookDAO = bookDAO;
    }

    // Открывается страница со списком издательств
    @GetMapping()
    public String showPublishingHouses(Model model) {
        model.addAttribute("houses", publishingHouseDAO.getAllPublishingHouses());
        return "/publishing-house/list";
    }

    // Открывается страница для просмотра данных об издательстве
    @GetMapping("{id}")
    public String showPublishingHouseInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("house", publishingHouseDAO.getPublishingHouse(id));
        model.addAttribute("books", bookDAO.getBooksByPublishingHouse(id));
        return "/publishing-house/info";
    }

    // Обрабатывается запрос на удаление книги по выбранному издателю
    @DeleteMapping("{publishingHouseId}/book/{bookId}")
    public String deleteBookByPublishingHouse(@PathVariable("publishingHouseId") String publishingHouseId,
                                              @PathVariable("bookId") int bookId) {
        bookDAO.deleteBook(bookId);
        return "redirect:/publishing-houses/" + publishingHouseId;
    }

    // Открывается страница для добавления нового издательства
    @GetMapping("/new")
    public String showPublishingHouseAddingForm(@ModelAttribute("house") PublishingHouse publishingHouse) {
        return "/publishing-house/form";
    }

    // Обрабатывается запрос на добавление нового издательства
    @PostMapping()
    public String addPublishingHouseToDatabase(@ModelAttribute("house") PublishingHouse publishingHouse) {
        publishingHouseDAO.addPublishingHouse(publishingHouse);
        return "redirect:/publishing-houses";
    }

    // Открывается страница для изменения данных об издательстве
    @GetMapping("/{id}/edit")
    public String showEditPublishingHouseInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("house", publishingHouseDAO.getPublishingHouse(id));
        return "/publishing-house/edit";
    }

    // Обрабатывается запрос на изменение данных об издательстве
    @PatchMapping("/{id}")
    public String editPublishingHouseInfo(@ModelAttribute("house") PublishingHouse publishingHouse,
                                 @PathVariable("id") int id) {
        publishingHouseDAO.changePublishingHouseInfo(publishingHouse);
        return "redirect:/publishing-houses";
    }

    // Обрабатывается запрос на удаление издательства
    @DeleteMapping("/{id}")
    public String deletePublishingHouse(@PathVariable int id) {
        publishingHouseDAO.deletePublishingHouse(id);
        return "redirect:/publishing-houses";
    }
}
