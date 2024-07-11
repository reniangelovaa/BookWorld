package bg.softuni.bookworld.web.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class AddBookDTO {
    private String name;
    private String author;
    private String description;
    private Double price;
    private Integer numberOfPages;
    private LocalDate releaseDate;
    private String imageUrl;

}
