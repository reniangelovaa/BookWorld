package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUser(User user);
}
