package shop.template.onlineShop.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import shop.template.onlineShop.enumerator.Country;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPutDTO {
    String username;
    String password;

    Country country;
    String address;
    String phoneNumber;
}
