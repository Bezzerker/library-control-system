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

    public List<PublishingHouse> getAllPublishingHouses() {
        return jdbcTemplate.query("SELECT * FROM publishing_houses", new BeanPropertyRowMapper<>(PublishingHouse.class));
    }

    public PublishingHouse getPublishingHouse(int id) {
        return jdbcTemplate.query("SELECT * FROM publishing_houses WHERE id=?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(PublishingHouse.class))
                .stream().findAny().orElse(null);
    }

    public void addPublishingHouse(PublishingHouse publishingHouse) {
        jdbcTemplate.update("INSERT INTO publishing_houses(name) values (?)", publishingHouse.getName());
    }

    public void changePublishingHouseInfo(PublishingHouse modifiedPublishingHouse) {
        jdbcTemplate.update("UPDATE publishing_houses SET name=? WHERE id=?",
                modifiedPublishingHouse.getName(), modifiedPublishingHouse.getId());
    }

    public void deletePublishingHouse(int id) {
        jdbcTemplate.update("DELETE FROM publishing_houses WHERE id=?", id);
    }
}
