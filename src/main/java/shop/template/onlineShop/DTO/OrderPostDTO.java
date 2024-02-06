package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.template.onlineShop.entity.OrderItem;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.entity.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPostDTO {

    List<OrderItemDTO> products;
    String description;
    String additionalComment;

}
