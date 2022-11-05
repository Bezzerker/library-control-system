package com.barikhashvili.library.models;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

public class Reader {
    // Идентификатор читателя по БД
    private int id;
    // Имя читателя
    private String firstName;
    // Фамилия читателя
    private String lastName;
    // Отчество читателя
    private String patronymic;
    // День рождения читателя
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    // Пол читателя
    private String gender;
    // Адрес проживания читателя
    private String residentialAddress;
    // Электронный адрес читателя
    private String email;
    // Номер телефона читателя
    private String phone;
    // Количество взятых в библиотеке книг
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isEmpty() {
        return firstName == null;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getInfo() {
        return lastName + " " + firstName.charAt(0) + '.' + patronymic.charAt(0) + '.';
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMale() {
        return gender.equals("Мужчина");
    }
}
