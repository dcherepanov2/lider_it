package com.cd.LiderIT_test.services;

import com.cd.LiderIT_test.entity.Author;
import com.cd.LiderIT_test.exception.AuthorException;
import com.cd.LiderIT_test.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author addAuthor(Author author) throws AuthorException {
        Author authorCheck = null;
        if(author.getName()!=null&&author.getPatronymic()!=null&&author.getSurname()!=null)
            authorCheck = authorRepository.isExist(author.getName(), author.getSurname(), author.getPatronymic());
        if (authorCheck == null) {
            try {
                authorRepository.save(author);
                return author;
            } catch (Exception e) {
                throw new AuthorException("One or more required parameters passed in the request are null." +
                        " Please check the correctness of the request.");
            }
        }
        throw new AuthorException("Such an author already exists");
    }

    public Author editAuthor(Integer authorId, Author author) throws AuthorException {
        Author authorCheck = authorRepository.findById(authorId);
        if (authorCheck != null) {
            if (author.getName() != null)
                authorCheck.setName(author.getName());
            if (author.getSurname() != null)
                authorCheck.setSurname(author.getSurname());
            if (author.getBiography() != null)
                authorCheck.setBiography(author.getBiography());
            if (author.getPatronymic() != null)
                authorCheck.setPatronymic(author.getPatronymic());
            if (author.getBirthday() != null) {
                java.sql.Date date = author.getBirthday();//указал имя пакета перед классом, так как частенько джава тянула класс Date из util пакета, а не из sql как должно быть
                authorCheck.setBirthday(date);
            }
            authorRepository.save(authorCheck);
            return authorCheck;
        }
        throw new AuthorException("The author with the specified id was not found");
    }
}
