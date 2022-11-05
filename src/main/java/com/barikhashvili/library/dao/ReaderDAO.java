package com.barikhashvili.library.dao;

import com.barikhashvili.library.models.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ReaderDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReaderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Получение списка всех читателей из БД
    public List<Reader> getAllReaders() {
        return jdbcTemplate.query("SELECT id, first_name, last_name, patronymic, date_of_birth,  gender, residential_address, email, phone, " +
                        "(SELECT COUNT(*) FROM borrowed_books WHERE reader_id=id) FROM readers",
                new BeanPropertyRowMapper<>(Reader.class));
    }

    // Получение читателя из БД по уникальному номеру
    public Reader getReader(int id) {
        return jdbcTemplate.query("SELECT id, first_name, last_name, patronymic, date_of_birth,  gender, residential_address, email, phone, " +
                                "(SELECT COUNT(*) FROM borrowed_books WHERE reader_id=id) FROM readers WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Reader.class))
                .stream().findAny().orElse(null);
    }

    // Добавление читателя в БД
    public void addReader(Reader reader) {
        jdbcTemplate.update("INSERT INTO readers(first_name, last_name, patronymic, date_of_birth,  gender, residential_address, email, phone) values (?,?,?,?,?,?,?,?)",
                reader.getFirstName(), reader.getLastName(), reader.getPatronymic(), reader.getDateOfBirth(), reader.getGender(),
                reader.getResidentialAddress(), reader.getEmail(), reader.getPhone());
    }

    // Изменение данных читателя в БД по уникальному номеру
    public void changeReaderInfo(Reader modifiedReader) {
        jdbcTemplate.update("UPDATE readers SET first_name=?, last_name=?, patronymic=?, date_of_birth=?,  gender=?, residential_address=?, email=?, phone=? WHERE id=?",
                modifiedReader.getFirstName(), modifiedReader.getLastName(), modifiedReader.getPatronymic(), modifiedReader.getDateOfBirth(), modifiedReader.getGender(),
                modifiedReader.getResidentialAddress(), modifiedReader.getEmail(), modifiedReader.getPhone(), modifiedReader.getId());
    }

    // Удаление читателя из БД
    public void deleteReader(int id) {
        jdbcTemplate.update("DELETE FROM readers WHERE id=?", id);
    }

    // Для BooksController - Возврат читателя по указанному уникальному номеру книги
    public Reader getReaderByBook(int bookId) {
        return jdbcTemplate.query("SELECT id, first_name, last_name, patronymic, date_of_birth,  gender, residential_address, email, phone " +
                "FROM readers LEFT JOIN borrowed_books ON readers.id=borrowed_books.reader_id WHERE book_id=?",
                new Object[] {bookId}, new BeanPropertyRowMapper<>(Reader.class)).stream().findAny().orElse(new Reader());
    }
}


