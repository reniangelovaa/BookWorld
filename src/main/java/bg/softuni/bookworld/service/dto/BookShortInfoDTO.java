package bg.softuni.bookworld.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookShortInfoDTO {
    private long id;
    private String name;
    private String author;
    private Double price;
    private String imageUrl;
}
