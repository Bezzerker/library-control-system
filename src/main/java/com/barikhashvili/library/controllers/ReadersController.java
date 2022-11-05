package com.barikhashvili.library.controllers;

import com.barikhashvili.library.dao.ReaderDAO;
import com.barikhashvili.library.dao.BookDAO;
import com.barikhashvili.library.models.Book;
import com.barikhashvili.library.models.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/readers")
public class ReadersController {
    private final ReaderDAO readerDAO;
    private final BookDAO bookDAO;

    @Autowired
    public ReadersController(ReaderDAO readerDAO, BookDAO bookDAO) {
        this.readerDAO = readerDAO;
        this.bookDAO = bookDAO;
    }

    // Открывается страница со списком всех читателей
    @GetMapping()
    public String showReaders(Model model) {
        model.addAttribute("readers", readerDAO.getAllReaders());
        return "/reader/list";
    }

    // Открывается страница добавления нового читателя
    @GetMapping("/new")
    public String showReaderAddingForm(@ModelAttribute("reader") Reader reader) {
        return "/reader/form";
    }

    // Обрабатывается запрос на добавление нового читателя
    @PostMapping()
    public String addReaderToDatabase(@ModelAttribute("reader") Reader reader) {
        readerDAO.addReader(reader);
        return "redirect:/readers";
    }

    // Открывается страница просмотра данных о читателе
    @GetMapping("/{id}")
    public String showReaderInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerDAO.getReader(id));
        model.addAttribute("books", bookDAO.getReadableBooks(id));
        model.addAttribute("freeBooks", bookDAO.getFreeBooks());
        model.addAttribute("book", new Book());
        return "/reader/info";
    }

    // Открывается страница изменения данных читателя
    @GetMapping("/{id}/edit")
    public String showEditReaderInfoPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readerDAO.getReader(id));
        return "/reader/edit";
    }

    // Обрабатывается запрос на изменение данных читателя
    @PatchMapping("/{id}")
    public String editReaderInfo(@ModelAttribute("reader") Reader reader,
                                 @PathVariable("id") int id) {
        readerDAO.changeReaderInfo(reader);
        return "redirect:/readers";
    }

    // Обрабатывается запрос на удаление читателя
    @DeleteMapping("/{id}")
    public String deleteReader(@PathVariable int id) {
        readerDAO.deleteReader(id);
        return "redirect:/readers";
    }

    // Обрабатывается запрос на взятие читателем книги из библиотеки (добавление в таблицу взятых книг)
    @PostMapping("/{readerId}/give")
    public String deleteBookByReader(@ModelAttribute("book") Book book,
                                     @PathVariable("readerId") int readerId) {
        bookDAO.giveBookToReader(book.getId(), readerId);
        return "redirect:/readers/" + readerId;
    }

    // Обрабатывается запрос на возвращение книги в библиотеку (удаление из таблицы взятых книг)
    @DeleteMapping("/{readerId}/return/{bookId}")
    public String addBookByReader(@PathVariable("readerId") int readerId,
                                  @PathVariable("bookId") int bookId) {
        bookDAO.returnBookToLibrary(bookId);
        return "redirect:/readers/" + readerId;
    }

    // Обрабатывается запрос на возвращение ВСЕХ книг в библиотеку
    @DeleteMapping("/{readerId}/returnAll")
    public String addBookByReader(@PathVariable("readerId") int readerId) {
        bookDAO.returnAllBooks(readerId);
        return "redirect:/readers/" + readerId;
    }
}
