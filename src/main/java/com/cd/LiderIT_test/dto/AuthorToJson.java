package com.cd.LiderIT_test.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorToJson {
    @JsonProperty(value = "author")
    private AuthorDTO authorDTO;

    public AuthorToJson(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }

    public AuthorDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(AuthorDTO authorDTO) {
        this.authorDTO = authorDTO;
    }
}
