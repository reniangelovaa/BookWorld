package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//    @Query( "FROM books b " +
//            "JOIN books_categories bc ON b.id = bc.book_id " +
//            "JOIN categories c ON bc.category_id = c.id " +
//            "WHERE c.categoryName = ?")
//    List<Book> findAllByCategory(String categoryName);
}
