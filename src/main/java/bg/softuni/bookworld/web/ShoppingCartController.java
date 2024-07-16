package bg.softuni.bookworld.web;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.CartItemRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.BookService;
import bg.softuni.bookworld.service.CartItemService;
import bg.softuni.bookworld.service.ShoppingCartService;
import bg.softuni.bookworld.service.exeption.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<ShoppingCart> showCart(Principal principal) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        User user = optionalUser.orElseThrow(() -> new ObjectNotFoundException("User", principal.getName()));
        ShoppingCart cart = shoppingCartService.getShoppingCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addToCart(@RequestParam Long bookId, @RequestParam int quantity, Principal principal) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        User user = optionalUser.orElseThrow(() -> new ObjectNotFoundException("User", principal.getName()));
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new ObjectNotFoundException("Book", bookId.toString()));

        shoppingCartService.addBookToCart(book, quantity, user);
        return ResponseEntity.status(HttpStatus.SEE_OTHER).header("Location", "/cart").build();
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ModelAndView removeFromCart(@PathVariable Long cartItemId) {
        cartItemService.removeBookFromCart(cartItemId);
        return new ModelAndView(new RedirectView("/cart", true));
    }


}

