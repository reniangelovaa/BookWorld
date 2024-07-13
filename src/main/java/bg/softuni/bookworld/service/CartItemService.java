package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.CartItemRepository;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void removeBookFromCart(Long cartItemId) {
         cartItemRepository.deleteCartItemById(cartItemId);
    }
}
