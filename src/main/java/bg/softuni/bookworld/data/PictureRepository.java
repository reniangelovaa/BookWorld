package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
