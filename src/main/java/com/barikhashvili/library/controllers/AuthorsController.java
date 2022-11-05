package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.AuthorDAO;
import com.barikhashvili.library.dao.BookDAO;
import com.barikhashvili.library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;

    @Autowired
    public AuthorsController(AuthorDAO authorDAO, BookDAO bookDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
    }

    // Открывается страница со списком всех авторов
    @GetMapping()
    public String showAuthors(Model model) {
        model.addAttribute("authors", authorDAO.getAllAuthors());
        return "/author/list";
    }

    // Открывается страница добавления нового автора
    @GetMapping("/new")
    public String showAuthorAddingForm(@ModelAttribute("author") Author author) {
        return "/author/form";
    }

    // Обрабатывается запрос на добавление нового автора
    @PostMapping()
    public String addAuthorToDatabase(@ModelAttribute("author") Author author) {
        authorDAO.addAuthor(author);
        return "redirect:/authors";
    }

    // Открывается страница просмотра данных об авторе
    @GetMapping("/{id}")
    public String showAuthorInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.getAuthor(id));
        model.addAttribute("books", bookDAO.getBooksByAuthor(id));
        return "/author/info";
    }

    // Открывается страница изменения данных об авторе
    @GetMapping("/{id}/edit")
    public String showEditAuthorInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("author", authorDAO.getAuthor(id));
        return "/author/edit";
    }

    // Обрабатывается запрос на изменение данных об авторе
    @PatchMapping("/{id}")
    public String editAuthorInfo(@ModelAttribute("author") Author author,
                                 @PathVariable("id") int id) {
        authorDAO.changeAuthorInfo(author);
        return "redirect:/authors";
    }

    // Обрабатывается запрос на удаление автора
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorDAO.deleteAuthor(id);
        return "redirect:/authors";
    }

    // Обрабатывается запрос на удаление написанной автором книги
    @DeleteMapping("/{authorId}/book/{bookId}")
    public String deleteBookByAuthor(@PathVariable String authorId, @PathVariable int bookId) {
        bookDAO.deleteBook(bookId);
        return "redirect:/authors/" + authorId;
    }
}
