package com.barikhashvili.library.models;

public class Book {
    // Идентификатор книги
    private int id;
    // Уникальный код ISBN книги
    private String ISBN;
    // Название книги
    private String title;
    // Идентификатор автора по БД
    private int authorId;
    // Идентификатор издательства по БД
    private int publishingHouseId;
    // Год издания книги
    private Integer publishingYear;
    // Количество страниц в книге
    private Integer pages;
    // Прошедшие дни с момента выдачи книги
    private Integer elapsedDays;

    public Integer getElapsedDays() {
        return elapsedDays;
    }

    public void setElapsedDays(Integer elapsedDays) {
        this.elapsedDays = elapsedDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getPublishingHouseId() {
        return publishingHouseId;
    }

    public void setPublishingHouseId(int publishingHouseId) {
        this.publishingHouseId = publishingHouseId;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
