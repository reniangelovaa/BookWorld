package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.CartItemRepository;
import bg.softuni.bookworld.data.ShoppingCartRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.CartItem;
import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public ShoppingCart getShoppingCart(User user) {
        return shoppingCartRepository.findByUser(user);
    }

    public void addBookToCart(Book book, int quantity, User user) {
        ShoppingCart cart = getShoppingCart(user);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUser(user);
            cart = shoppingCartRepository.save(cart);
        }
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(cart);
        cartItemRepository.save(cartItem);
    }

    public void deleteEmptyShoppingCart(ShoppingCart shoppingCart){
        if (shoppingCart.getCartItems().isEmpty()){
            shoppingCart.setUser(null);
            shoppingCartRepository.delete(shoppingCart);
        }
    }

}
