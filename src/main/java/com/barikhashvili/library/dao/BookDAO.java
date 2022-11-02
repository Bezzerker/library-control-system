package com.barikhashvili.library.dao;

import com.barikhashvili.library.models.Book;
import com.barikhashvili.library.models.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO books(isbn,title,author_id,publishing_house_id,publishing_year,pages) values (?,?,?,?,?,?)",
                book.getISBN(), book.getTitle(), book.getAuthorID(), book.getPublishingHouseID(), book.getPublishingYear(), book.getPages());
    }

    public void changeBookInfo(Book modifiedBook) {
        jdbcTemplate.update("UPDATE books SET isbn=?, title=?, author_id=?, publishing_house_id=?, publishing_year=?, pages=? WHERE id=?",
                modifiedBook.getISBN(), modifiedBook.getTitle(), modifiedBook.getAuthorID(),
                modifiedBook.getPublishingHouseID(), modifiedBook.getPublishingYear(), modifiedBook.getPages(), modifiedBook.getId());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
    }

    public Integer findReaderId(int bookId) {
        Integer readerId = jdbcTemplate.query("SELECT reader_id FROM borrowed_books WHERE book_id=?",
                        new Object[]{bookId}, (rs, rowNum) -> rs.getInt("reader_id"))
                        .stream().findAny().orElse(null);
        return readerId;
    }

    public void freeBook(int bookId) {
        jdbcTemplate.update("DELETE FROM borrowed_books WHERE book_id=?",bookId);
    }

    public List<Book> getReadBooks(int readerId) {
        return jdbcTemplate.query("SELECT id, isbn, title, author_id, publishing_house_id, publishing_year, pages " +
                "FROM books WHERE books.id=borrowed_books.book_id AND borrowed_books.reader_id=?",
                new Object[]{readerId}, new BeanPropertyRowMapper<>(Book.class));
    }
}
