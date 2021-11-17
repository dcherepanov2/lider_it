package com.cd.LiderIT_test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookToJson {
    @JsonProperty("book")
    private BookDTO bookDTO;

    public BookToJson(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }
}
