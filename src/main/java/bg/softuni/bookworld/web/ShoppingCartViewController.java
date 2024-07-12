package bg.softuni.bookworld.web;

import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.CartItem;
import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
public class ShoppingCartViewController {


    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/cart")
    public String showCart(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(()-> new UsernameNotFoundException("User not found"));
        ShoppingCart cart = shoppingCartService.getShoppingCart(user);
        double totalPrice = calculateTotalPrice(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    private double calculateTotalPrice(ShoppingCart cart) {
        if (cart == null || cart.getCartItems().isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (CartItem item : cart.getCartItems()) {
            total += item.getBook().getPrice() * item.getQuantity();
        }
        return total;
    }
}
