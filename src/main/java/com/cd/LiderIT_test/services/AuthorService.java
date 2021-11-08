package com.cd.LiderIT_test.services;

import com.cd.LiderIT_test.entity.Author;
import com.cd.LiderIT_test.exception.AuthorException;
import com.cd.LiderIT_test.parsers.Parser;
import com.cd.LiderIT_test.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;


@Service
public class AuthorService {//TODO: посмотреть аннотацию query и как ее использовать без обработки try catch

    private final AuthorRepository authorRepository;
    private final Parser parser;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, Parser parser) {
        this.authorRepository = authorRepository;
        this.parser = parser;
    }

    public Author addAuthor(String authorParam) throws AuthorException {
        Map<String, String> authorRequest = parser.validateAuthor(authorParam);
        Author author = new Author();
        author.setName(authorRequest.get("name"));
        author.setPatronymic(authorRequest.get("patronymic"));
        author.setSurname(authorRequest.get("surname"));
        author.setBirthday(Date.valueOf(authorRequest.get("birthday")));
        author.setBiography(authorRequest.get("biography"));
        Author authorCheck = null;
        try {
            authorCheck = authorRepository.isExist(author.getName(), author.getSurname(), author.getPatronymic());
        } catch (Exception e) {
            //отловка исключения null,но так как null должен возвращаться catch блок пустой
        }
        if(authorCheck == null){
            try {
                authorRepository.save(author);
            }catch (Exception e){
                throw new AuthorException("One or more required parameters passed in the request are null." +
                        " Please check the correctness of the request.");
            }
            return author;
        }
        throw new AuthorException("Such an author already exists");
    }

    public Author editAuthor(Integer authorId, String request) throws AuthorException {
        Author author = authorRepository.findById(authorId);
        if (author != null) {
            try {
                Map<String, String> authorRequest = parser.validateAuthor(request);
                author.setName(authorRequest.get("name"));
                author.setSurname(authorRequest.get("surname"));
                author.setBiography(authorRequest.get("biography"));
                author.setPatronymic(authorRequest.get("patronymic"));
                java.sql.Date date = java.sql.Date.valueOf(authorRequest.get("birthday"));//указал имя пакета перед файлом, так как частенько джава тянула класс Date из util пакета, а не из sql как должно быть
                author.setBirthday(date);
                authorRepository.save(author);
                return author;
            } catch (Exception e) {
                throw new AuthorException("One or more required parameters passed in the request are null. Please check the correctness of the request.");
            }

        }
        throw new AuthorException("The author with the specified id was not found");
    }

}
