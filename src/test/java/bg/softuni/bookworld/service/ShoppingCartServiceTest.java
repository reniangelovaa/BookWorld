package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.CartItemRepository;
import bg.softuni.bookworld.data.ShoppingCartRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.CartItem;
import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceTest {

    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository mockShoppingCartRepository;

    @Mock
    private CartItemRepository mockCartItemRepository;

    @Mock
    private BookRepository mockBookRepository;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingCartService = new ShoppingCartService(mockShoppingCartRepository, mockCartItemRepository, mockBookRepository, mockUserRepository);
    }

    @Test
    void testGetShoppingCart() {
        User user = new User();
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);

        when(mockShoppingCartRepository.findByUser(user)).thenReturn(cart);

        ShoppingCart result = shoppingCartService.getShoppingCart(user);

        assertEquals(cart, result);
    }

    @Test
    void testAddBookToCart() {
        User user = new User();
        Book book = new Book();
        book.setPrice(10.0);
        int quantity = 2;

        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        when(mockShoppingCartRepository.findByUser(user)).thenReturn(cart);

        shoppingCartService.addBookToCart(book, quantity, user);

        ArgumentCaptor<CartItem> cartItemCaptor = ArgumentCaptor.forClass(CartItem.class);
        verify(mockCartItemRepository).save(cartItemCaptor.capture());

        CartItem savedCartItem = cartItemCaptor.getValue();
        assertEquals(book, savedCartItem.getBook());
        assertEquals(quantity, savedCartItem.getQuantity());
        assertEquals(cart, savedCartItem.getShoppingCart());
    }

    @Test
    void testCalculateTotalPrice() {
        ShoppingCart cart = mock(ShoppingCart.class);
        CartItem item1 = new CartItem();
        Book book1 = new Book();
        book1.setPrice(10.0);
        item1.setBook(book1);
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        Book book2 = new Book();
        book2.setPrice(5.0);
        item2.setBook(book2);
        item2.setQuantity(3);

        when(cart.getCartItems()).thenReturn(List.of(item1, item2));

        double result = shoppingCartService.calculateTotalPrice(cart);

        assertEquals(35.0, result);
    }
}
