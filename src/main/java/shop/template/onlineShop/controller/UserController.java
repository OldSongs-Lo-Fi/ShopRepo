package shop.template.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.template.onlineShop.DTO.PasswordChangerDTO;
import shop.template.onlineShop.DTO.UserGetDTO;
import shop.template.onlineShop.DTO.UserPostDTO;
import shop.template.onlineShop.DTO.UserPutDTO;
import shop.template.onlineShop.entity.User;
import shop.template.onlineShop.service.UserService;
import shop.template.onlineShop.utils.EntityMapperUtil;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;



    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PostMapping("/")
    public void createUser(@RequestBody UserPostDTO userPostDTO){
        userService.createUser(userPostDTO);
    }



    //EMAIL CONFIRMATION

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    @PostMapping("/sendConfirmationMessage")
    public void sendConfirmationMessage(@RequestBody UserPostDTO userPostDTO){
        userService.sendConfirmationMessage(userPostDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("permitAll()")
    @GetMapping("/confirmEmail")
    public void confirmEmail(@RequestParam("key") String key){
        userService.confirmEmail(key);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/changeEmail")
    public void changeEmail(@AuthenticationPrincipal User user, @RequestParam("email") String email){
        userService.updateEmailSendEmail(user, email);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("permitAll()")
    @GetMapping("/changeEmail")
    public void change(@RequestParam("key") String key){
        userService.updateEmail(key);
    }



    //RUD


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/current")
    public ResponseEntity<UserGetDTO> getCurrentUser(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200)
                .body(userService.mapUserToUserGetDTO(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/")
    public ResponseEntity<Page<UserGetDTO>> getAllUsers(@RequestParam("page") int page){
        return ResponseEntity.status(200)
                .body(userService.getUsers(page)
                        .map(EntityMapperUtil::mapUser));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/")
    public void updateUser(@AuthenticationPrincipal User user, @RequestBody UserPutDTO userPutDTO){
        userService.updateUser(user, userPutDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/changePassword")
    public void changePassword(@AuthenticationPrincipal User user, @RequestBody PasswordChangerDTO passwordChangerDTO){
        userService.updatePassword(user, passwordChangerDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserGetDTO> getUser(@PathVariable("userId") Long userId){
        return ResponseEntity.status(200)
                .body(EntityMapperUtil.mapUser(userService.getUser(userId)));
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PatchMapping("/{id}/ban")
    public void banUser(@PathVariable("id") Long userId){
        userService.banUser(userId);
    }

}
