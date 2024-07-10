package com.crud.pkagioglou.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crud.pkagioglou.model.Author;
import com.crud.pkagioglou.model.Book;
import com.crud.pkagioglou.repository.BookRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public List<Book> findAllBooksSortedByTitle(String direction) {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        if ("desc".equalsIgnoreCase(direction)) {
            sort = Sort.by(Sort.Direction.DESC, "title");
        }
        return bookRepository.findAll(sort);
    }

    public List<Book> filterBooksByTitle(List<Book> books, String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksByAuthor(List<Book> books, String author) {
        return books.stream()
                .filter(book -> book.getAuthors().stream()
                        .anyMatch(a -> (a.getFirstName() + " " + a.getLastName()).toLowerCase().contains(author.toLowerCase())))
                .collect(Collectors.toList());
    }

    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId).orElse(new Book());
    }

    public boolean updateBook(Book book) {
        bookRepository.save(book);
        return true;
    }


    @Transactional
    public void deleteById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            Set<Author> authors = new HashSet<>(book.getAuthors());
            bookRepository.deleteById(bookId);

            List<Author> orphanedAuthors = bookRepository.findOrphanedAuthors();
            for (Author author : orphanedAuthors) {
                deleteAuthor(author);
            }
        }
    }

    private void deleteAuthor(Author author) {
        // Using EntityManager to delete author
        Author managedAuthor = entityManager.find(Author.class, author.getId());
        if (managedAuthor != null) {
            entityManager.remove(managedAuthor);
        }
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
