package shop.template.onlineShop.exceptionHandler;


import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import shop.template.onlineShop.exception.UserNotFoundException;

@ControllerAdvice(basePackages = "shop.template.onlineShop.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String> userNotFound(Exception e){
        return ResponseEntity.status(404)
                .body("UserNotFoundException:\n" + e.getMessage());
    }


    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFound(Exception e){
        return ResponseEntity.status(404)
                .body("ResourceNotFoundException:\n" + e.getMessage());
    }
}
