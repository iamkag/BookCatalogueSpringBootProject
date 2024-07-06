package com.crud.pkagioglou.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.pkagioglou.model.Author;
import com.crud.pkagioglou.model.Book;
import com.crud.pkagioglou.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    BookRepository repository;

    public List<Book> findAllBooks() {
        return repository.findAll();
    }

    public Book findBookById(Long bookId) {
        return repository.findById(bookId).orElse(new Book());
    }

    public boolean updateBook(Book book) {
        repository.save(book);
        return true;
    }

    public void deleteById(Long bookId) {
        repository.deleteById(bookId);
    }

    public boolean addBook(Book book) {
        repository.save(book);
        return true;
    }

    public List<Book> findBooksByAuthorFullName(String firstName, String lastName) {
        return repository.findByAuthorFullName(firstName, lastName);
    }

    public Set<Book> findBooksByAuthors(Set<Author> authors) {
        Set<Book> books = new HashSet<>();
        for (Author author : authors) {
            List<Book> booksByAuthor = findBooksByAuthorFullName(author.getFirstName(), author.getLastName());
            books.addAll(booksByAuthor);
        }
        return books;
    }
}
