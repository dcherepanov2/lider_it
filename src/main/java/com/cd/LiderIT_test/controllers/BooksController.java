package com.cd.LiderIT_test.controllers;

import com.cd.LiderIT_test.dto.BookDTO;
import com.cd.LiderIT_test.dto.BookList;
import com.cd.LiderIT_test.dto.BookToJson;
import com.cd.LiderIT_test.entity.Book;
import com.cd.LiderIT_test.exception.BookException;
import com.cd.LiderIT_test.services.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BooksController {

    private final BooksService booksService;

    @Autowired
    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping("/allBook")
    public ResponseEntity<BookList> getAllBooks(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "availability", required = false) Boolean availability,
            @RequestParam(name = "number", required = false) Integer number)
    {
        List<Book> books;
        if (name == null && availability == null && number == null)
            books = booksService.getBookRepository();
        else {
            books = booksService.filterAndGet(name, number, availability);
            return ResponseEntity.ok(booksService.getAllBooks(books));
        }
        return ResponseEntity.ok(booksService.getAllBooks(books));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<BookToJson> addBook(Book book) throws BookException {
        Book book1 = booksService.addBook(book);
        return ResponseEntity.ok(new BookToJson(new BookDTO(book1)));
    }

    @RequestMapping(value = "/availabilityFalse/{bookId}", method = RequestMethod.POST)
    public ResponseEntity<BookToJson> deleteBook(@PathVariable("bookId") Integer id) throws BookException {
        Book book = booksService.bookDeleteNumber(id);
        return ResponseEntity.ok(new BookToJson(new BookDTO(book)));
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<BookToJson> editBook(Book book) throws BookException {
        book = booksService.editBook(book);
        return ResponseEntity.ok(new BookToJson(new BookDTO(book)));
    }
}

