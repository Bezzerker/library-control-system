package com.barikhashvili.library.dao;

import com.barikhashvili.library.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AuthorDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Author> authorMapper = (rs, rowNum) -> {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setFirstName(rs.getString("first_name"));
        author.setSurname(rs.getString("surname"));
        author.setPatronymic(rs.getString("patronymic"));
        author.setDateOfBirth(rs.getDate("date_of_birth"));
        return author;
    };

    @Autowired
    public AuthorDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Получение списка всех авторов из БД
    public List<Author> getAllAuthors() {
        return jdbcTemplate.query("SELECT * FROM authors", authorMapper);
    }

    // Получение автора по его уникальному номеру
    public Author getAuthor(int id) {
        return jdbcTemplate.query("SELECT * FROM authors WHERE id=?",
                new Object[]{id}, authorMapper)
                .stream().findAny().orElse(null);
    }

    // Добавление нового автора в БД
    public void addAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO authors(first_name, surname, patronymic, date_of_birth) VALUES (?,?,?,?)",
                author.getFirstName(), author.getSurname(), author.getPatronymic(), author.getDateOfBirth());
    }

    // Изменение данных автора по уникальному номеру
    public void changeAuthorInfo(Author modifiedAuthor) {
        jdbcTemplate.update("UPDATE authors SET first_name=?, surname=?, patronymic=?, date_of_birth=? WHERE id=?",
                modifiedAuthor.getFirstName(), modifiedAuthor.getSurname(),
                modifiedAuthor.getPatronymic(), modifiedAuthor.getDateOfBirth(), modifiedAuthor.getId());
    }

    // Удаление автора из БД
    public void deleteAuthor(int id) {
        jdbcTemplate.update("DELETE FROM authors WHERE id=?", id);
    }
}