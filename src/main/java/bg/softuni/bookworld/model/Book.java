package bg.softuni.bookworld.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
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
    @OneToMany(targetEntity = Picture.class, mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Picture> pictures;

    public Book() {
        this.categories = new HashSet<>();
        this.comments = new HashSet<>();
        this.pictures = new HashSet<>();
    }

}
