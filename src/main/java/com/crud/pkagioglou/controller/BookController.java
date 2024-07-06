package com.crud.pkagioglou.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.crud.pkagioglou.model.Author;
import com.crud.pkagioglou.model.Book;
import com.crud.pkagioglou.service.BookService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class BookController {

    @Autowired
    BookService bookService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(Set.class, "authors", new CustomCollectionEditor(Set.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element != null) {
                    String authorsFullName = element.toString().trim();
                    System.out.println("authorsFullName: " + authorsFullName);

                    // Split the string by the comma to get individual authors
                    String[] authors = authorsFullName.split(",");
                    Set<Author> authorSet = new HashSet<>();
                    for (String author : authors) {
                        String trimmedAuthor = author.trim();
                        String[] nameParts = trimmedAuthor.split(" ");
                        Author authorObj = new Author();
                        if (nameParts.length >= 2) {
                            authorObj.setFirstName(nameParts[0]);
                            authorObj.setLastName(nameParts[1]);
                            System.out.println("FirstName: " + nameParts[0] + ", LastName: " + nameParts[1]);
                        } else if (nameParts.length == 1) {
                            authorObj.setFirstName(nameParts[0]);
                            authorObj.setLastName(""); // or some default value if needed
                            System.out.println("FirstName: " + nameParts[0] + ", LastName: ");
                        } else {
                            System.out.println("Invalid author name format: " + trimmedAuthor);
                            continue; // Skip invalid author names
                        }
                        authorSet.add(authorObj);
                    }
                    return authorSet;
                }
                return null;
            }
        });
    }

    @GetMapping("/all-books")
    public String findAll(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "all-books";
    }

    @GetMapping("/add")
    public String launchAddBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/addbook")
    public String createBook(Book book, Model model) {
        if (bookService.isDuplicateBook(book)) {
            model.addAttribute("message", "Book with the same title, version, and author already exists.");
            model.addAttribute("book", book);
            return "add-book";
        }
        boolean added = bookService.addBook(book);
        if (added) {
            return "redirect:/all-books";
        } else {
            model.addAttribute("message", "Failed to add the book. Please try again.");
            model.addAttribute("book", book);
            return "add-book";
        }
    }

    @GetMapping("/edit/{id}")
    public String launchEditPage(Model model, @PathVariable("id") Long bookId) {
        model.addAttribute("book", bookService.findBookById(bookId));
        return "edit-book";
    }

    @PostMapping("/updatebook")
    public String updateBook(Book book, Model model) {
        if (bookService.isDuplicateBook(book)) {
            model.addAttribute("message", "Book with the same title, version, and author already exists.");
            model.addAttribute("book", book);
            return "edit-book";
        }
        boolean update = bookService.updateBook(book);
        if (update) {
            return "redirect:/all-books";
        } else {
            model.addAttribute("message", "Failed to update the book. Please try again.");
            model.addAttribute("book", book);
            return "edit-book";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);
        return "redirect:/all-books";
    }

    @GetMapping("/info/{id}")
    public String getBookInfo(@PathVariable("id") Long bookId, Model model) {
        Book book = bookService.findBookById(bookId);
        Set<Author> authors = book.getAuthors();
        Set<Book> relatedBooks = bookService.findBooksByAuthors(authors);
        relatedBooks.remove(book); // Remove the current book from the set of related books if it's present

        model.addAttribute("book", book);
        model.addAttribute("relatedBooks", relatedBooks);
        return "book-info";
    }
}
