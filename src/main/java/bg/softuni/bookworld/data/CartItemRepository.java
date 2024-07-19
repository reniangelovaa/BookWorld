package bg.softuni.bookworld.data;

import bg.softuni.bookworld.model.CartItem;
import bg.softuni.bookworld.model.ShoppingCart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.id= :id")
    void deleteCartItemById(Long id);
}

