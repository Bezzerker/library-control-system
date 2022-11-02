package com.barikhashvili.library.dao;

import com.barikhashvili.library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class AuthorDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Author> getAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM authors", new BeanPropertyRowMapper<>(Author.class));
    }

    public Author getAuthor(int id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Author.class))
                .stream().findAny().orElse(null);
    }

    public void addAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO authors(first_name, surname, patronymic) VALUES (?,?,?)",
                author.getFirstName(), author.getSurname(), author.getPatronymic());
    }

    public void changeAuthorInfo(Author modifiedAuthor) {
        jdbcTemplate.update("UPDATE authors SET first_name=?, surname=?, patronymic=? WHERE id=?",
                modifiedAuthor.getFirstName(), modifiedAuthor.getSurname(),
                modifiedAuthor.getPatronymic(), modifiedAuthor.getId());
    }

    public void deleteAuthor(int id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id=?", id);
    }
}