package bg.softuni.bookworld.model;

import bg.softuni.bookworld.model.enums.CategoryType;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private CategoryType name;
    @Column(columnDefinition = "TEXT")
    private String description;

    public Category() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CategoryType getName() {
        return name;
    }

    public void setName(CategoryType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
