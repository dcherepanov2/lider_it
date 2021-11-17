package com.cd.LiderIT_test.dto;

import com.cd.LiderIT_test.entity.Book;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "book")
public class BookDTO {

    private Long id;

    private Integer number;

    private String name;

    private boolean availability;

    private AuthorDTO author;

    public BookDTO(Book book) {
        this.id = book.getId();
        if (book.getNumber() != null)
            this.number = book.getNumber();
        this.name = book.getName();
        this.availability = book.isAvailability();
        if (book.getAuthor() != null)
            this.author = new AuthorDTO(book.getAuthor());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "book{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", availability=" + availability +
                ", author=" + author +
                '}';
    }
}
