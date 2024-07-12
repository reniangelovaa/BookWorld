package bg.softuni.bookworld.web;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<ShoppingCart> showCart(Principal principal) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ShoppingCart cart = shoppingCartService.getShoppingCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestParam Long bookId, @RequestParam int quantity, Principal principal) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new RuntimeException("Book not found"));
        shoppingCartService.addBookToCart(book, quantity, user);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/cart").build();
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(@PathVariable Long cartItemId) {
        shoppingCartService.removeBookFromCart(cartItemId);
            return ResponseEntity.noContent().build();
    }

}

