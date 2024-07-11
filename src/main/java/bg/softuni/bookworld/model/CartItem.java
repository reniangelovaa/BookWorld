package bg.softuni.bookworld.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne(optional = false)
    private Book book;
    @ManyToOne(optional = false)
    private ShoppingCart shoppingCart;
    @Column(nullable = false)
    private int quantity;

}
