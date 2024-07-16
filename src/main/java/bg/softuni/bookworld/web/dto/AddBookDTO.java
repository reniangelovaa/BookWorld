package bg.softuni.bookworld.web.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class AddBookDTO {
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;
    @NotBlank
    @Size(min = 2, max = 50)
    private String author;
    @NotBlank
    @Size(min = 2)
    private String description;
    @NotBlank
    private Double price;
    @NotBlank
    @Min(0)
    private int copies;
    @NotBlank
    @Min(0)
    private Integer numberOfPages;
    @NotBlank
    private LocalDate releaseDate;
    private String imageUrl;

}
