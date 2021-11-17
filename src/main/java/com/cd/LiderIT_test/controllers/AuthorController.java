package com.cd.LiderIT_test.controllers;

import com.cd.LiderIT_test.dto.AuthorDTO;
import com.cd.LiderIT_test.dto.AuthorToJson;
import com.cd.LiderIT_test.entity.Author;
import com.cd.LiderIT_test.exception.AuthorException;
import com.cd.LiderIT_test.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/author",
        consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
@ResponseBody
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<AuthorToJson> add(Author request) throws AuthorException {
        Author author = authorService.addAuthor(request);
        return ResponseEntity.ok(new AuthorToJson(new AuthorDTO(author)));
    }

    @RequestMapping(value = "/edit/{id}",method = RequestMethod.POST)
    public ResponseEntity<AuthorToJson> edit(@PathVariable("id") Integer authorId, Author author) throws AuthorException {
        Author authorController = authorService.editAuthor(authorId, author);
        return ResponseEntity.ok(new AuthorToJson(new AuthorDTO(authorController)));
    }
}
