package com.cd.LiderIT_test.templatesResponse;

import com.cd.LiderIT_test.entity.Author;
import com.cd.LiderIT_test.entity.Book;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HttpResponseCorrectly {

    public Map<String,String> createResponseAuthor(Author author){
        return new HashMap<String,String>(){{
            put("id", String.valueOf(author.getId()));
            put("name",author.getName());
            put("surname",author.getSurname());
            put("patronymic",author.getPatronymic());
            put("birthday",author.getBirthday().toString());
            put("biography", author.getBiography());
        }};
    }

    public Map<String,String> createResponseBook(Book book){
        return new HashMap<String,String>(){{
            put("id", String.valueOf(book.getId()));
            put("name",book.getName());
            put("availability", String.valueOf(book.isAvailability()));
            if(book.getNumber()!=null)
                put("number", String.valueOf(book.getNumber()));
            else
                put("number", null);
            if(book.getAuthor()!=null)
                put("author_id",String.valueOf(book.getAuthor().getId()));
            else
                put("author_id", null);
        }};
    }
}
