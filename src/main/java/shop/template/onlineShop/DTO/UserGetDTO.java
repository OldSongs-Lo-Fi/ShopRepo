package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.template.onlineShop.enumerator.Country;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetDTO {
    Long id;
    String email;
    String username;

    Country country;
    String address;
    String phoneNumber;
}
