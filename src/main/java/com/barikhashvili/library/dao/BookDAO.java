package com.barikhashvili.library.dao;

import com.barikhashvili.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Получение списка всех авторов
    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    // Получение кники по указанному уникальному номеру
    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    // Добавление книги в БД
    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO books(isbn,title,author_id,publishing_house_id,publishing_year,pages) values (?,?,?,?,?,?)",
                book.getISBN(), book.getTitle(), book.getAuthorId(), book.getPublishingHouseId(), book.getPublishingYear(), book.getPages());
    }

    // Изменение информации о книге по уникальному номеру
    public void changeBookInfo(Book modifiedBook) {
        jdbcTemplate.update("UPDATE books SET isbn=?, title=?, author_id=?, publishing_house_id=?, publishing_year=?, pages=? WHERE id=?",
                modifiedBook.getISBN(), modifiedBook.getTitle(), modifiedBook.getAuthorId(),
                modifiedBook.getPublishingHouseId(), modifiedBook.getPublishingYear(), modifiedBook.getPages(), modifiedBook.getId());
    }

    // Удаление книги из БД
    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }

    // Получение количества дней с момента выдачи книги
    public Integer getDaysElapsed(int bookId) {
        return jdbcTemplate.query("SELECT DATE_PART('day',now()-issue_date) FROM borrowed_books WHERE book_id=?",
                new Object[] {bookId}, (rs, rowNum) -> rs.getInt("date_part")).stream().findAny().orElse(null);
    }

    // Удаление книг из БД взятых книг
    public void returnBookToLibrary(int bookId) {
        jdbcTemplate.update("DELETE FROM borrowed_books WHERE book_id=?",bookId);
    }

    // Для ReadersController - возврат книг, взятых читателем
    public List<Book> getReadableBooks(int readerId) {
        return jdbcTemplate.query("SELECT id, isbn, title, author_id, publishing_house_id, publishing_year, pages " +
                "FROM books, borrowed_books WHERE id=book_id AND reader_id=?",
                new Object[]{readerId}, new BeanPropertyRowMapper<>(Book.class));
    }

    // Для ReadersController - возврат всех свободных книг (которые никто не брал)
    public List<Book> getFreeBooks() {
        return jdbcTemplate.query("SELECT * FROM books WHERE id NOT IN (SELECT book_id FROM borrowed_books)",
                new BeanPropertyRowMapper<>(Book.class));
    }

    // Для ReadersController - добавление книги в таблицу выданных книг
    public void giveBookToReader(int bookId, int readerId) {
        jdbcTemplate.update("INSERT INTO borrowed_books VALUES(?,?,now())", readerId, bookId);
    }

    // Для ReadersController - удаление всех выданных книг по уникальному номеру читателя
    public void returnAllBooks(int readerId) {
        jdbcTemplate.update("DELETE FROM borrowed_books WHERE reader_id=?", readerId);
    }

    // Для AuthorsController - возврат всех книг, написанных определенным автором
    public List<Book> getBooksByAuthor(int authorId) {
        return jdbcTemplate.query("SELECT * FROM books WHERE author_id=?",
                new Object[] {authorId}, new BeanPropertyRowMapper<>(Book.class));
    }

    // Для PublishingHouseController - возврат всех книг, выпущенных под определенным издательством
    public List<Book> getBooksByPublishingHouse(int publishingHouseId) {
        return jdbcTemplate.query("SELECT * FROM books WHERE publishing_house_id=?",
                new Object[] {publishingHouseId}, new BeanPropertyRowMapper<>(Book.class));
    }
}
