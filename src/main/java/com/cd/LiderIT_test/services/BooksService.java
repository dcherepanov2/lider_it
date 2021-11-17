package com.cd.LiderIT_test.services;

import com.cd.LiderIT_test.dto.BookDTO;
import com.cd.LiderIT_test.dto.BookList;
import com.cd.LiderIT_test.dto.BookToJson;
import com.cd.LiderIT_test.entity.Book;
import com.cd.LiderIT_test.exception.BookException;
import com.cd.LiderIT_test.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class BooksService {

    private final BookRepository bookRepository;

    @Autowired
    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book bookDeleteNumber(Integer id) throws BookException {
        Book book = bookRepository.findById(id);
        if (book != null) {
            book.setAvailability(false);
            bookRepository.save(book);
            return bookRepository.findById(id);
        }
        throw new BookException("Books not found with this id");
    }

    public Book addBook(Book bookRequest) throws BookException {
        Book bookCheck = null;
        if (bookRequest.getAuthor() != null && bookRequest.getName() != null) {
            bookCheck = bookRepository.findBookByNameAndAuthor(bookRequest.getName(), bookRequest.getAuthor().getId());
        }
        if (bookCheck != null) {
            throw new BookException("Such a book already exists.");
        }
        try {
            bookRepository.save(bookRequest);
        } catch (DataIntegrityViolationException e) {
            throw new BookException("One or more required parameters are not specified in the request." +
                    "Please check your request.");
        }
        return bookRequest;
    }

    public Book editBook(Book bookRequest) throws BookException {
        Book book = bookRepository.findById(Integer.valueOf(String.valueOf(bookRequest.getId())));
        if (book != null) {
            if (bookRequest.getName() != null)
                book.setName(bookRequest.getName());
            try {
                book.setAvailability(bookRequest.isAvailability());
            } catch (NullPointerException e) {

            }
            if (bookRequest.getAuthor() != null)
                book.setAuthor(bookRequest.getAuthor());
            if (bookRequest.getNumber() != null)
                book.setNumber(bookRequest.getNumber());
            bookRepository.save(book);
            return book;
        }
        throw new BookException("The book with the specified id does not exist");
    }

    public BookList getAllBooks(List<Book> allBooksParam) {
        BookList bookList = new BookList();
        List<BookToJson> allBook = new ArrayList<>();
        for (Book book : allBooksParam) {
            allBook.add(new BookToJson(new BookDTO(book)));
        }
        bookList.setListBook(allBook);
        return bookList;
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
