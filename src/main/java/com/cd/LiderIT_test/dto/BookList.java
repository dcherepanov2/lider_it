package com.cd.LiderIT_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class BookList {
    @JsonProperty("books")
    private List<BookToJson> listBook;


    public List<BookToJson> getListBook() {
        return listBook;
    }

    public void setListBook(List<BookToJson> listBook) {
        this.listBook = listBook;
    }

}
