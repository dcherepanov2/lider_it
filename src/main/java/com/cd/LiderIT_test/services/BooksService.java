package com.cd.LiderIT_test.services;

import com.cd.LiderIT_test.entity.Author;
import com.cd.LiderIT_test.entity.Book;
import com.cd.LiderIT_test.exception.BookException;
import com.cd.LiderIT_test.parsers.Parser;
import com.cd.LiderIT_test.repo.AuthorRepository;
import com.cd.LiderIT_test.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class BooksService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final Parser parser;

    @Autowired
    public BooksService(BookRepository bookRepository, AuthorRepository authorRepository, Parser parser) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.parser = parser;
    }

    public Book bookDeleteNumber(Integer id) throws BookException {
        Book book = bookRepository.findById(id);
        if (book != null) {
            bookRepository.updateAvailability(id);
            return book;
        }
        throw new BookException("Books not found exception");
    }

    public Book addBook(String bookRequest) throws BookException {
        if (bookRequest != null) {
            Map<String, String> bookKeyValues = parser.bookValidate(bookRequest);
            Book book1 = new Book();
            if (bookKeyValues.get("name") == null)
                throw new BookException("The required parameter name was not passed in the request");
            try {
                if (bookKeyValues.get("name") != null && bookKeyValues.get("author_id") != null)
                    book1 = bookRepository.findBookByNameAndAuthor(
                            bookKeyValues.get("name"), Long.valueOf(bookKeyValues.get("author_id"))
                    );
            } catch (NullPointerException e) {
                if (book1.getId() != null) {
                    throw new BookException("Such a book already exists.");
                }
            }
            try {
                book1 = new Book();
                if (bookKeyValues.get("number") != null)
                    book1.setNumber(Integer.valueOf(bookKeyValues.get("number")));//
                if (bookKeyValues.get("author_id") != null) {
                    Author author = authorRepository.findById(Integer.valueOf(bookKeyValues.get("author_id")));
                    book1.setAuthor(author);
                }

                book1.setAvailability(Boolean.parseBoolean(bookKeyValues.get("availability")));
                book1.setName(bookKeyValues.get("name"));
                bookRepository.save(book1);
                return book1;
            } catch (Exception e) {
                throw new BookException("One or more required parameters passed in the request are null. Please check the correctness of the request.");
            }
        }
        throw new BookException("Request equals null. Please check your param in request");
    }

    public Book editBook(Integer bookId, String request) throws BookException {
        Book book = bookRepository.findById(bookId);
        Map<String, String> bookRequest = parser.bookValidate(request);
        try {
            Author author;
            if (book.getId() != null) {
                if (bookRequest.get("name") != null)
                    book.setName(bookRequest.get("name"));
                if (bookRequest.get("author_id") != null) {
                    author = authorRepository.findById(Integer.valueOf(bookRequest.get("author_id")));
                    book.setAuthor(author);
                }
                if (bookRequest.get("availability") != null)
                    book.setAvailability(Boolean.parseBoolean(bookRequest.get("availability")));
                if (bookRequest.get("number") != null)
                    book.setNumber(Integer.valueOf(bookRequest.get("number")));
                try {
                    bookRepository.save(book);
                    return book;
                } catch (Exception e) {
                    throw new BookException("One or more required parameters passed in the request are null. Please check the correctness of the request.");
                }
            }
        } catch (Exception e) {
            throw new BookException("The book with the specified id does not exist");
        }
        throw new BookException("The book with the specified id does not exist");
    }

    public Map<String, Map<String, HashMap<String, HashMap<String, String>>>> getAllBooks(List<Book> allBooksParam) {
        Map<String, Map<String, HashMap<String, HashMap<String, String>>>> allBooksData = new HashMap<>();
        Map<String, HashMap<String, HashMap<String, String>>> allBooks = new HashMap<>();//создание всех книг

        for (Book book : allBooksParam) {
            allBooks.put("book" + book.getId(), new HashMap<String, HashMap<String, String>>() {{

                HashMap<String, String> bookOne = new HashMap<>();//перебор всех книг и запись их в хешмапу
                bookOne.put("id", String.valueOf(book.getId()));
                bookOne.put("name", book.getName());
                bookOne.put("availability", String.valueOf(book.isAvailability()));
                bookOne.put("number", String.valueOf(book.getNumber()));

                put("bookData", bookOne);

                Author author = book.getAuthor();
                HashMap<String, String> authorBook = new HashMap<>();
                if (author != null) {
                    authorBook.put("id", String.valueOf(author.getId()));
                    authorBook.put("name", author.getName());
                    authorBook.put("surname", author.getSurname());
                    authorBook.put("patronymic", author.getPatronymic());
                    authorBook.put("biography", author.getBiography());
                    authorBook.put("birthday", author.getBirthday().toString());

                }
                put("authorBook", authorBook);
            }});
        }
        allBooksData.put("books", allBooks);
        return allBooksData;
    }

    public List<Book> getBookRepository() {
        return bookRepository.findAll();
    }

    public List<Book> filterAndGet(String name, Integer number, Boolean availability) {
        if (name == null)
            name = "null";
        if (number == null)
            number = -1;
        if (availability == null) {
            List<Book> books = bookRepository.filterByParam(name, number, true);
            List<Book> books1 = bookRepository.filterByParam(name, number, false);
            return Stream.concat(books.stream(), books1.stream())
                    .collect(Collectors.toList());
        }
        return bookRepository.filterByParam(name, number, availability);
    }
}
