package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.entity.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostDTO {

    Long productId;
    String text;

    int rating;

}
