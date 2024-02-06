package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetDTO {
    Long id;
    String name;
    String description;
    BigDecimal price;
    Double rating;
    boolean isAvailable;
}
