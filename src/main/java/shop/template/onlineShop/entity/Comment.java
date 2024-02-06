package shop.template.onlineShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    int rating;

    Date date;
    String text;

    public Comment(User author, Product product, Date date, String text, int rating) {
        this.author = author;
        this.product = product;
        this.date = date;
        this.text = text;
        this.rating = rating;
    }

}
