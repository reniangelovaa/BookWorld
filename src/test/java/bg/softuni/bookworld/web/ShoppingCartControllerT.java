package bg.softuni.bookworld.web;


import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.ShoppingCart;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.CartItemService;
import bg.softuni.bookworld.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShoppingCartControllerT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private UserRepository userRepository;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testShowCart() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        ShoppingCart cart = new ShoppingCart();
        double totalPrice = 100.0;

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(shoppingCartService.getShoppingCart(user)).thenReturn(cart);
        when(shoppingCartService.calculateTotalPrice(cart)).thenReturn(totalPrice);

        mockMvc.perform(get("/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("cart"))
                .andExpect(model().attributeExists("totalPrice"))
                .andExpect(model().attribute("cart", cart))
                .andExpect(model().attribute("totalPrice", totalPrice));
    }



}
