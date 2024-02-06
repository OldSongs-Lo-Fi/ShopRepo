package shop.template.onlineShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    int count;
    BigDecimal summaryPrice;

    public OrderItem(Product product, int count) {
        this.product = product;
        this.count = count;
        summaryPrice = product.getPrice().multiply(BigDecimal.valueOf(count));
    }

    @Override
    public String toString() {
        return "OrderItem №" + id +
                "id: " + id + "\n" +
                "Product: " + product + "\n" +
                "Order: " + order + "\n" +
                "Count: " + count + "\n" +
                "SummaryPrice: " + summaryPrice;
    }
}
