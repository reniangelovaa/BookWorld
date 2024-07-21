package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.Category;
import bg.softuni.bookworld.model.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryType name);
}
