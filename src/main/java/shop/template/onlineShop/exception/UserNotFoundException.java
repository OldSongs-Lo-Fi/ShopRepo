package shop.template.onlineShop.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
