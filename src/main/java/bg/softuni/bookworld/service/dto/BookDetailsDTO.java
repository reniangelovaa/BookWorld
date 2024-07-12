package bg.softuni.bookworld.service.dto;

import bg.softuni.bookworld.model.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookDetailsDTO {
    private long id;
    private String name;
    private String author;
    private String description;
    private Double price;
    private Integer numberOfPages;
    private LocalDate releaseDate;
    private String imageUrl;
    private List<String> category;
}
