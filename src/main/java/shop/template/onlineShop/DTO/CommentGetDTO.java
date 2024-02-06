package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentGetDTO {

    Long id;
    UserGetDTO author;
    ProductGetDTO product;
    int rating;
    Date date;
    String text;

}

