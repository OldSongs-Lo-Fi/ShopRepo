package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.enumerator.Country;

import java.util.Set;

@AllArgsConstructor
@Data
public class UserPostDTO {

    String email;
    String username;
    String password;

    Country country;
    String address;
    String phoneNumber;
}
