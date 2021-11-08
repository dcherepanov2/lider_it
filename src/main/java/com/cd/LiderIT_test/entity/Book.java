package com.cd.LiderIT_test.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;

    public Book() {

    }

    @Column(name = "number")
    private Integer number;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "availability")
    @NotNull
    private boolean availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")//загуглил - существуют книги без автора, по этой причине не стал ставить нотнул аннотацию
    private Author author;

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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
