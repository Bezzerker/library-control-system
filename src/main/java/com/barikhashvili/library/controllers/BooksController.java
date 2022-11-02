package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.AuthorDAO;
import com.barikhashvili.library.dao.BookDAO;
import com.barikhashvili.library.dao.PublishingHouseDAO;
import com.barikhashvili.library.models.Author;
import com.barikhashvili.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;
    private final PublishingHouseDAO publishingHouseDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, AuthorDAO authorDAO, PublishingHouseDAO publishingHouseDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.publishingHouseDAO = publishingHouseDAO;
    }

    // Получение страницы со списком книг
    @GetMapping()
    public String showBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "/book/list";
    }

    // Добавление новой книги
    @GetMapping("/new")
    public String showBookAddingForm(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("authors", authorDAO.getAllAuthors());
        model.addAttribute("publishers", publishingHouseDAO.getAllPublishingHouses());
        return "/book/form";
    }

    @PostMapping()
    public String addBookToDatabase(@ModelAttribute("book") Book book) {
        bookDAO.addBook(book);
        System.out.println(book.getAuthor() + " " + book.getPublishingHouseID());
        return "redirect:/books";
    }

    // Просмотр данных о книге
    @GetMapping("/{id}")
    public String showBookInfoPage(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBook(id);
        int authorId = book.getAuthorID();
        System.out.println(authorId);
        Author author = authorDAO.getAuthor(authorId);
        book.setAuthor(author.getSurname() + " " + author.getFirstName() + " " + author.getPatronymic());
        model.addAttribute("book", book);
        return "/book/info";
    }

    // Изменение данных о книге
    @GetMapping("/{id}/edit")
    public String showEditBookInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        model.addAttribute("authors", authorDAO.getAllAuthors());
        model.addAttribute("publishers", publishingHouseDAO.getAllPublishingHouses());

        return "/book/edit";
    }

    @PatchMapping("/{id}")
    public String editBookInfo(@ModelAttribute("book") Book book,
                                 @PathVariable("id") int id) {
        bookDAO.changeBookInfo(book);
        return "redirect:/books";
    }

    // Удаление книги
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
