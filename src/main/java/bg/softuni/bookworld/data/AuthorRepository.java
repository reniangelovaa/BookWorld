package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFullName(String name);
}