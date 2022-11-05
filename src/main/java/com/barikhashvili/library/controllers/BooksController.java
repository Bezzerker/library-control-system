package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.AuthorDAO;
import com.barikhashvili.library.dao.BookDAO;
import com.barikhashvili.library.dao.PublishingHouseDAO;
import com.barikhashvili.library.dao.ReaderDAO;
import com.barikhashvili.library.models.Book;
import com.barikhashvili.library.models.Reader;
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
    private final ReaderDAO readerDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, AuthorDAO authorDAO, PublishingHouseDAO publishingHouseDAO, ReaderDAO readerDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.publishingHouseDAO = publishingHouseDAO;
        this.readerDAO = readerDAO;
    }

    // Открывается страница со списком всех книг
    @GetMapping()
    public String showBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "/book/list";
    }

    // Открывается страница добавления новой книги
    @GetMapping("/new")
    public String showBookAddingForm(@ModelAttribute("book") Book book, Model model) {
        model.addAttribute("authors", authorDAO.getAllAuthors());
        model.addAttribute("publishers", publishingHouseDAO.getAllPublishingHouses());
        return "/book/form";
    }

    // Обрабатывается запрос на добавление новой книги
    @PostMapping()
    public String addBookToDatabase(@ModelAttribute("book") Book book) {
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    // Открывается страница с данными о книге
    @GetMapping("/{id}")
    public String showBookInfoPage(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.getBook(id);
        // Инициализация прошедших дней с момента выдачи читателю
        book.setElapsedDays(bookDAO.getDaysElapsed(id));
        // Модели для получения информации о книге
        model.addAttribute("book", book);
        model.addAttribute("author", authorDAO.getAuthor(book.getAuthorId()));
        model.addAttribute("house", publishingHouseDAO.getPublishingHouse(book.getPublishingHouseId()));
        // Модели для проведения выдачи книги определенному читателю
        model.addAttribute("reader", readerDAO.getReaderByBook(id));
        model.addAttribute("readers", readerDAO.getAllReaders());
        return "/book/info";
    }

    // Открывается страница для изменения данных о книге
    @GetMapping("/{id}/edit")
    public String showEditBookInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        model.addAttribute("authors", authorDAO.getAllAuthors());
        model.addAttribute("publishers", publishingHouseDAO.getAllPublishingHouses());

        return "/book/edit";
    }

    // Обрабатывается запрос на изменение данных о книге
    @PatchMapping("/{id}")
    public String editBookInfo(@ModelAttribute("book") Book book,
                                 @PathVariable("id") int id) {
        bookDAO.changeBookInfo(book);
        return "redirect:/books";
    }

    // Обрабатывается запрос на удаление книги
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    // Обрабатывается запрос на возвращение книги в библиотеку
    @DeleteMapping("/return/{id}")
    public String returnBook(@PathVariable int id) {
        bookDAO.returnBookToLibrary(id);
        return "redirect:/books/" + id;
    }

    // Запрос на добавление книги в таблицу выданных книг
    @PostMapping("/give/{id}")
    public String giveToReader(@ModelAttribute("reader") Reader reader, @PathVariable int id) {
        bookDAO.giveBookToReader(id, reader.getId());
        return "redirect:/books/" + id;
    }
}
