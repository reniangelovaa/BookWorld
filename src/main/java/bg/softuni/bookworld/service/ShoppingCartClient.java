package bg.softuni.bookworld.service;

import bg.softuni.bookworld.model.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ShoppingCartClient {
    @Autowired
    private RestTemplate restTemplate;

    private final String shoppingCartServiceUrl = "http://localhost:8080/api/cart";

    public ShoppingCart getShoppingCart(String username) {
        return restTemplate.getForObject(shoppingCartServiceUrl, ShoppingCart.class);
    }

    public void addBookToCart(Long bookId, int quantity, String username) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(shoppingCartServiceUrl + "/add")
                .queryParam("bookId", bookId)
                .queryParam("quantity", quantity);
        restTemplate.postForEntity(builder.toUriString(), null, Void.class);
    }

    public void removeBookFromCart(Long cartItemId) {
        restTemplate.delete(shoppingCartServiceUrl + "/remove/" + cartItemId);
    }
}
