package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.Category;
import bg.softuni.bookworld.service.dto.BookShortInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c = :category")
    List<Book> findAllByCategory(@Param("category") Category category);

}
