package com.barikhashvili.library.models;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class Author {
    // Идентификтор автора по базе данных
    private int id;
    // Имя автора
    private String firstName;
    // Фамилия автора
    private String surname;
    // Отчество автора
    private String patronymic;
    // Дата рождения автора
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth( Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getInfo() {
        return surname + ' ' + firstName.charAt(0) + '.' + patronymic.charAt(0) + '.';
    }
}
