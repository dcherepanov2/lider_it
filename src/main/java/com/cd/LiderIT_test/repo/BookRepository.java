package com.cd.LiderIT_test.repo;

import com.cd.LiderIT_test.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> { //TODO: проверить где @Query выкидывает exception погуглить еще раз как это исправить

    @Query(
            value = "select * from books where author_id =:author_id",
            nativeQuery = true
    )
    List<Book> allBooksFindByAuthor(@Param("author_id") Integer authorId);//все книги одного автора

    @Query(
            value = "select * from books where name =:name AND author_id=:author_id",
            nativeQuery = true
    )
    Book findBookByNameAndAuthor(@Param("name") String name, @Param("author_id") Long authorId);

    @Transactional
    @Modifying
    @Query(
            value = "UPDATE books SET availability = false WHERE id=:id",
            nativeQuery = true
    )
    void updateAvailability(@Param("id") Integer id);

    @Query(
            value = "select * from books where id =:bookId",
            nativeQuery = true
    )
    Book findById(Integer bookId);

    @Query(
            value = "SELECT * from filterbyparambooks(:name,:number,:availability)",
            nativeQuery = true
    )
    List<Book> filterByParam(@Param("name") String name, @Param("number") int number, @Param("availability") boolean availability);

    List<Book> findByName(String name);

    List<Book> findAll();

    List<Book> findByAvailability(Boolean availability);

    List<Book> findBookByNumber(Integer number);
}
