package shop.template.onlineShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    BigDecimal price;
    Double rating;
    boolean isAvailable = true;

    @OneToMany(mappedBy = "product")
    Set<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "favorites", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> lovers;

    public Product(String name, String description, BigDecimal price, Set<Comment> comments, Set<User> lovers) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.comments = comments;
        this.lovers = lovers;
    }

    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
