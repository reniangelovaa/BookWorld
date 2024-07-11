package bg.softuni.bookworld.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "full_name", nullable = false, unique = true)
    private String fullName;
    @OneToMany(targetEntity = Book.class, mappedBy = "author")
    private Set<Book> books;
    @Column(columnDefinition = "TEXT")
    private String description;
}
