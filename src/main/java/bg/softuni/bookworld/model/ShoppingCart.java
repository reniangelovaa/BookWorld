package bg.softuni.bookworld.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartItem> cartItems;
    @OneToOne
    private User user;

    public ShoppingCart(){
        this.cartItems = new ArrayList<>();
    }
}
