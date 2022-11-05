package com.barikhashvili.library.dao;

import com.barikhashvili.library.models.PublishingHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PublishingHouseDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PublishingHouseDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Получение всех издательств из БД
    public List<PublishingHouse> getAllPublishingHouses() {
        return jdbcTemplate.query("SELECT * FROM publishing_houses", new BeanPropertyRowMapper<>(PublishingHouse.class));
    }

    // Получение издательства по уникальному номер
    public PublishingHouse getPublishingHouse(int id) {
        return jdbcTemplate.query("SELECT * FROM publishing_houses WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(PublishingHouse.class))
                .stream().findAny().orElse(null);
    }

    // Добавление в БД издательства
    public void addPublishingHouse(PublishingHouse publishingHouse) {
        jdbcTemplate.update("INSERT INTO publishing_houses(name) values (?)", publishingHouse.getName());
    }

    // Изменение данных издательства по уникальному номер
    public void changePublishingHouseInfo(PublishingHouse modifiedPublishingHouse) {
        jdbcTemplate.update("UPDATE publishing_houses SET name=? WHERE id=?",
                modifiedPublishingHouse.getName(), modifiedPublishingHouse.getId());
    }

    // Удаление издательства
    public void deletePublishingHouse(int id) {
        jdbcTemplate.update("DELETE FROM publishing_houses WHERE id=?", id);
    }
}
