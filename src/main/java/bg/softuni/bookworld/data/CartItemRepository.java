package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.CartItem;
import bg.softuni.bookworld.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
