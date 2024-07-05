package bg.softuni.bookworld.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(name = "number_of_pages", nullable = false)
    private Integer numberOfPages;
    @Column(nullable = false)
    private Double price;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @Column(nullable = false)
    private Integer copies;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Author author;
    @ManyToMany
    private Set<Category> categories;
    @OneToMany(targetEntity = Comment.class, mappedBy = "book")
    private Set<Comment> comments;
    @OneToMany(targetEntity = Picture.class, mappedBy = "book")
    private Set<Picture> pictures;

    public Book() {
        this.categories = new HashSet<>();
        this.comments = new HashSet<>();
        this.pictures = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
}
