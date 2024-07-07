package com.crud.pkagioglou.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import com.crud.pkagioglou.model.Author;
import com.crud.pkagioglou.model.Book;
import com.crud.pkagioglou.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElse(new Book());
    }

    public List<Book> findAllBooksSortedByTitle(String direction) {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        if ("desc".equalsIgnoreCase(direction)) {
            sort = Sort.by(Sort.Direction.DESC, "title");
        }
        return bookRepository.findAll(sort);
    }

    public boolean updateBook(Book book) {
        bookRepository.save(book);
        return true;
    }

    public void deleteById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public boolean addBook(Book book) {
        bookRepository.save(book);
        return true;
    }

    public List<Book> findBooksByAuthorFullName(String firstName, String lastName) {
        return bookRepository.findByAuthorFullName(firstName, lastName);
    }

    public Set<Book> findBooksByAuthors(Set<Author> authors) {
        Set<Book> books = new HashSet<>();
        for (Author author : authors) {
            List<Book> booksByAuthor = findBooksByAuthorFullName(author.getFirstName(), author.getLastName());
            books.addAll(booksByAuthor);
        }
        return books;
    }

    // Method to check if a book is a duplicate
    public boolean isDuplicateBook(Book book) {
        List<Book> existingBooks = bookRepository.findByTitleAndVersion(book.getTitle(), book.getVersion());
        for (Book existingBook : existingBooks) {
            if (existingBook.getAuthors().equals(book.getAuthors())) {
                return true;
            }
        }
        return false;
    }
}
