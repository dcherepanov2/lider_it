package com.cd.LiderIT_test.repo;

import com.cd.LiderIT_test.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AuthorRepository extends JpaRepository<Author,Long> {//TODO: обработать все исключения в случае если запрос равен нул

    @Query(
            value = "select * from authors WHERE name = :name AND surname = :surname AND patronymic=:patronymic"
            , nativeQuery = true)
    Author isExist(@Param("name") String name, @Param("surname") String surname, @Param("patronymic") String patronymic);

    @Query(
            value = "select * from authors WHERE id = :id"
            , nativeQuery = true)
    Author findById(Integer id);

}
