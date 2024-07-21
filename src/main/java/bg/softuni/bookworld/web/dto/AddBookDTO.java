package bg.softuni.bookworld.web.dto;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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
    @NotNull
    @Min(1)
    private Double price;
    @NotNull
    @Min(1)
    private int copies;
    @NotNull
    @Min(1)
    private Integer numberOfPages;
    @NotNull
    private LocalDate releaseDate;
    @NotBlank
    private String imageUrl;
    @NotNull
    @NotEmpty
    private List<String> categories;


}
