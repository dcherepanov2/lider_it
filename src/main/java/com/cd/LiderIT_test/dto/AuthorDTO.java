package com.cd.LiderIT_test.dto;

import com.cd.LiderIT_test.entity.Author;

public class AuthorDTO {

    private Long id;

    private String name;

    private String patronymic;

    private String surname;

    private String birthday;


    private String biography;

    public AuthorDTO(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.patronymic = author.getPatronymic();
        this.surname = author.getSurname();
        this.birthday = author.getBirthday().toString();
        if (author.getBiography() != null)
            this.biography = author.getBiography();
    }

    public AuthorDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", biography='" + biography + '\'' +
                '}';
    }
}
