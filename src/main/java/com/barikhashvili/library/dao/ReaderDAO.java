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
import java.util.ArrayList;
import java.util.List;

@Component
public class ReaderDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> getAllReaders() {
        return jdbcTemplate.query("SELECT * FROM readers", new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader getReader(int id) {
        return jdbcTemplate.query("SELECT * FROM readers WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Reader.class))
                .stream().findAny().orElse(null);
    }

    public void addReader(Reader reader) {
        jdbcTemplate.update("INSERT INTO readers(first_name, last_name, patronymic,  male, residential_address, email, phone) values (?,?,?,?,?,?,?)",
                reader.getFirstName(), reader.getLastName(), reader.getPatronymic(), reader.getMale(),
                reader.getResidentialAddress(), reader.getEmail(), reader.getPhone());
    }

    public void changeReaderInfo(Reader modifiedReader) {
        jdbcTemplate.update("UPDATE readers SET first_name=?, last_name=?, patronymic=?,  male=?, residential_address=?, email=?, phone=? WHERE id=?",
                modifiedReader.getFirstName(), modifiedReader.getLastName(), modifiedReader.getPatronymic(), modifiedReader.getMale(),
                modifiedReader.getResidentialAddress(), modifiedReader.getEmail(), modifiedReader.getPhone(), modifiedReader.getId());
    }

    public void deleteReader(int id) {
        jdbcTemplate.update("DELETE FROM readers WHERE id=?", id);
    }
}


