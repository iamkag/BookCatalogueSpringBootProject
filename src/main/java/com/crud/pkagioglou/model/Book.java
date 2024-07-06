package com.crud.pkagioglou.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "BOOK")
public class Book extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 250)
    private String title;

    @NotNull
    private int version;

    @NotNull
    private Double price;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishingDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "AUTHOR_BOOK", joinColumns = { @JoinColumn(name = "BOOK_ID") }, inverseJoinColumns = {
        @JoinColumn(name = "AUTHOR_ID") })
    private Set<Author> authors = new HashSet<>();

    public Book() {

    }

    public Book(Long id, String title, int version, Double price, Date publishingDate, Set<Author> authors) {

        this.id = id;
        this.title = title;
        this.version = version;
        this.price = price;
        this.publishingDate = publishingDate;
        this.authors = authors;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public int getVersion() {

        return version;
    }

    public void setVersion(int version) {

        this.version = version;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    public Date getPublishingDate() {

        return publishingDate;
    }

    public void setPublishingDate(Date publishingDate) {

        this.publishingDate = publishingDate;
    }

    public Set<Author> getAuthors() {
    
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        
        this.authors = authors;
    }
}
