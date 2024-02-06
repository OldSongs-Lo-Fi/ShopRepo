package shop.template.onlineShop.utils;

import shop.template.onlineShop.DTO.CommentGetDTO;
import shop.template.onlineShop.DTO.ProductGetDTO;
import shop.template.onlineShop.DTO.UserGetDTO;
import shop.template.onlineShop.entity.Comment;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.entity.User;

public class EntityMapperUtil {

    public static UserGetDTO mapUser(User user){
        return new UserGetDTO(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getCountry(),
                user.getAddress(),
                user.getPhoneNumber()
        );
    }

    public static ProductGetDTO mapProduct(Product product){
        return new ProductGetDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getRating(),
                product.isAvailable()
        );
    }

    public static CommentGetDTO mapComment(Comment comment){
        return new CommentGetDTO(
                comment.getId(),
                mapUser(comment.getAuthor()),
                mapProduct(comment.getProduct()),
                comment.getRating(),
                comment.getDate(),
                comment.getText()
        );
    }

}
