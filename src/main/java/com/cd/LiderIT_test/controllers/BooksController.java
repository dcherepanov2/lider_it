package com.cd.LiderIT_test.controllers;

import com.cd.LiderIT_test.entity.Book;
import com.cd.LiderIT_test.exception.BookException;
import com.cd.LiderIT_test.services.BooksService;
import com.cd.LiderIT_test.templatesResponse.HttpResponseCorrectly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final HttpResponseCorrectly httpResponseCorrectly;

    @Autowired
    public BooksController(BooksService booksService, HttpResponseCorrectly httpResponseCorrectly) {
        this.booksService = booksService;
        this.httpResponseCorrectly = httpResponseCorrectly;
    }

    @GetMapping("/allBook")
    public @ResponseBody
    Map<String, Map<String, HashMap<String, HashMap<String, String>>>>
    getAllBooks(@RequestParam(name = "name", required = false) String name
            , @RequestParam(name = "availability", required = false) Boolean availability
            , @RequestParam(name = "number", required = false) Integer number)
    {
        List<Book> books;
        if (name == null && availability == null && number == null)
            books = booksService.getBookRepository();
        else {
            books = booksService.filterAndGet(name, number, availability);
            return booksService.getAllBooks(books);
        }
        return booksService.getAllBooks(books);
    }

    @PostMapping("/add")
    public @ResponseBody
    Map<String, String> addBook(@RequestBody String book) throws BookException {
        Book book1 = booksService.addBook(book);
        return httpResponseCorrectly.createResponseBook(book1);
    }

    @PostMapping("/availabilityFalse/{bookId}")
    public @ResponseBody
    Map<String, String> deleteBook(@PathVariable("bookId") Integer id) throws BookException {//TODO: обработка ошибки
        Book book = booksService.bookDeleteNumber(id);
        return httpResponseCorrectly.createResponseBook(book);
    }

    @PostMapping("/edit/{bookId}")
    public @ResponseBody
    Map<String, String> editBook(@PathVariable("bookId") Integer bookId, @RequestBody String request) throws BookException {
        Book book = booksService.editBook(bookId, request);
        return httpResponseCorrectly.createResponseBook(book);
    }
}

