package com.cd.LiderIT_test.controllers;

import com.cd.LiderIT_test.entity.Author;
import com.cd.LiderIT_test.exception.AuthorException;
import com.cd.LiderIT_test.services.AuthorService;
import com.cd.LiderIT_test.templatesResponse.HttpResponseCorrectly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/author")
public class AuthorController { // TODO: в мануале к использованию написать что дата рождения должна быть в формате Год-Месяц-День

    private final AuthorService authorService;
    private final HttpResponseCorrectly httpResponseCorrectly;

    @Autowired
    public AuthorController(AuthorService authorService, HttpResponseCorrectly httpResponseCorrectly) {
        this.authorService = authorService;
        this.httpResponseCorrectly = httpResponseCorrectly;
    }

    @PostMapping("/add")
    public @ResponseBody
    Map<String, String> add(@RequestBody String request) throws AuthorException { // добавить нового автора
        Author author = authorService.addAuthor(request);
        return httpResponseCorrectly.createResponseAuthor(author);
    }

    @PostMapping("/edit/{id}")//изменение автора по его id
    public @ResponseBody
    Map<String, String> edit(@PathVariable("id") Integer authorId, @RequestBody String request) throws AuthorException {
        Author author = authorService.editAuthor(authorId,request);
        return httpResponseCorrectly.createResponseAuthor(author);
    }
}
