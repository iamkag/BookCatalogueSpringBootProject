package com.crud.pkagioglou.repository;

import com.crud.pkagioglou.model.Author;
import com.crud.pkagioglou.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.firstName = :firstName AND a.lastName = :lastName")
    List<Book> findByAuthorFullName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Book> findByTitleAndVersion(String title, int version);

    @Query("SELECT a FROM Author a WHERE NOT EXISTS (SELECT b FROM Book b JOIN b.authors ba WHERE ba.id = a.id)")
    List<Author> findOrphanedAuthors();
}
