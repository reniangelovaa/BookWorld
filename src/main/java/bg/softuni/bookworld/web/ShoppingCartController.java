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
import jakarta.servlet.http.HttpServletRequest;
import org.bouncycastle.math.raw.Mod;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public String showCart(Model model, Principal principal) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        User user = optionalUser.orElseThrow(() -> new ObjectNotFoundException("User", principal.getName()));
        ShoppingCart cart = shoppingCartService.getShoppingCart(user);

        if (cart != null) {
            shoppingCartService.deleteEmptyShoppingCart(cart);
            double totalPrice = shoppingCartService.calculateTotalPrice(cart);
            model.addAttribute("cart", cart);
            model.addAttribute("totalPrice", totalPrice);
        }

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long bookId, @RequestParam int quantity, Principal principal, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        Optional<User> optionalUser = userRepository.findByUsername(principal.getName());
        User user = optionalUser.orElseThrow(() -> new ObjectNotFoundException("User", principal.getName()));
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        Book book = optionalBook.orElseThrow(() -> new ObjectNotFoundException("Book", bookId.toString()));

        shoppingCartService.addBookToCart(book, quantity, user);

        redirectAttributes.addFlashAttribute("message", "Book added to cart successfully!");


        String referer = request.getHeader("Referer");

            return "redirect:" + referer;

    }


    @PostMapping("/remove/{cartItemId}")
    public ModelAndView removeFromCart(@PathVariable Long cartItemId, RedirectAttributes redirectAttributes) {
        cartItemService.removeBookFromCart(cartItemId);
        redirectAttributes.addFlashAttribute("message", "Item removed from cart!");
        return new ModelAndView(new RedirectView("/cart", true));
    }


}

