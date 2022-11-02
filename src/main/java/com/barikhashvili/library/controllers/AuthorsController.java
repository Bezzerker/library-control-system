package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.AuthorDAO;
import com.barikhashvili.library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorDAO authorDAO;

    @Autowired
    public AuthorsController(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    // Получение страницы со списком авторов
    @GetMapping()
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorDAO.getAllAuthors());
        return "/author/list";
    }

    @GetMapping("/new")
    public String showAuthorAddingForm(@ModelAttribute("author") Author author) {
        return "/author/form";
    }

    @PostMapping()
    public String addAuthorToDatabase(@ModelAttribute("author") Author author) {
        authorDAO.addAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/{id}")
    public String showAuthorInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.getAuthor(id));
        return "/author/info";
    }

    @GetMapping("/{id}/edit")
    public String showEditAuthorInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.getAuthor(id));
        return "/author/edit";
    }

    @PatchMapping("/{id}")
    public String editAuthorInfo(@ModelAttribute("author") Author author,
                                 @PathVariable("id") int id) {
        authorDAO.changeAuthorInfo(author);
        return "redirect:/authors";
    }

    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorDAO.deleteAuthor(id);
        return "redirect:/authors";
    }
}
