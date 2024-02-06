package shop.template.onlineShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.template.onlineShop.enumerator.OrderStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    Date date;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    List<OrderItem> products;
    BigDecimal summaryPrice;
    String description;
    String additionalComment;

    OrderStatus orderStatus = OrderStatus.IN_PROGRESS;

    public Order(User user, Date date, List<OrderItem> products, BigDecimal summaryPrice, String description, String additionalComment) {
        this.user = user;
        this.date = date;
        this.products = products;
        this.summaryPrice = summaryPrice;
        this.description = description;
        this.additionalComment = additionalComment;
    }

    @Override
    public String toString() {
        String productsText = "";
        for (OrderItem product: products
             ) {
            productsText.concat(product.toString());
            productsText += "\n";

        }
        return "Order" + "\n" +
                "id: " + id + "\n" +
                "User Id: " + user.getId() + "\n" +
                "User Email: " + user.getEmail() + "\n" +
                "User Username: " + user.getUsername() + "\n" +
                "User Address: " + user.getAddress() + "\n" +
                "User PhoneNumber: " + user.getPhoneNumber() + "\n" +
                "Date=" + date + "\n" +
                "Products: " + productsText + "\n" +
                "SummaryPrice: " + summaryPrice + "\n" +
                "Description: '" + description + '\'' + "\n" +
                "AdditionalComment: '" + additionalComment + '\'' +
                "Orders status: " + orderStatus.toString();
    }
}
