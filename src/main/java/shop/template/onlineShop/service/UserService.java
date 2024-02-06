package shop.template.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.template.onlineShop.DTO.PasswordChangerDTO;
import shop.template.onlineShop.DTO.UserGetDTO;
import shop.template.onlineShop.DTO.UserPostDTO;
import shop.template.onlineShop.DTO.UserPutDTO;
import shop.template.onlineShop.entity.User;
import shop.template.onlineShop.exception.UserNotFoundException;
import shop.template.onlineShop.repo.UserRepo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    MailService mailService;

    static final long TIME_FOR_CONFIRMATION = 300_000;

    Map<String, UserPostDTO> nonActivatedUsers = new HashMap<>();

    @Value("${site.settings.domen}")
    String link;

    Map<String, Long> changeEmailRequests = new HashMap<>();



    public void createUser(UserPostDTO u){
        User user = new User(u.getEmail(), u.getUsername(), u.getPassword(), u.getCountry(), u.getAddress(), u.getPhoneNumber());
        userRepo.save(user);
    }

    public void sendConfirmationMessage(UserPostDTO userPostDTO){
        String key = UUID.randomUUID().toString();
        key += "_" + new Date().getTime();
        nonActivatedUsers.put(key, userPostDTO);
        mailService.sendEmail(String.format(
                "This is your activation code: %s/users/confirmEmail?key=%s %nLink is valid for only 5 minutes!", link, key
        ), userPostDTO.getEmail(), "Activation Code!");
    }

    public void banUser(Long userId){
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        user.setNonLocked(false);
        userRepo.save(user);
    }

    public void confirmEmail(String key){
        UserPostDTO user = nonActivatedUsers.get(key);
        if(user == null){
            throw new RuntimeException("Confirmation Code is invalid!");
        }

        if(new Date().getTime() - Long.parseLong(key.split("_")[1]) > TIME_FOR_CONFIRMATION){
            throw new RuntimeException("Confirmation Code is invalid!");
        }
        createUser(user);
    }


    public void updateUser(User user, UserPutDTO userPutDTO){
        user.setUsername(userPutDTO.getUsername());
        user.setPassword(userPutDTO.getPassword());


        user.setCountry(userPutDTO.getCountry());
        user.setAddress(userPutDTO.getAddress());
        user.setPhoneNumber(userPutDTO.getPhoneNumber());
        userRepo.save(user);
    }

    public UserGetDTO mapUserToUserGetDTO(User user) {

        return new UserGetDTO(user.getId(), user.getEmail(), user.getUsername(), user.getCountry(), user.getAddress(), user.getPhoneNumber());
    }

    public void updateEmailSendEmail(User user, String email) {
        String key = UUID.randomUUID() + "_" + new Date().getTime();
        mailService.sendEmail(String.format(
                "This is your activation code: %s/users/changeEmail?key=%s %nLink is valid for only 5 minutes!", link, key
        ), email, "Activation Code!");
        changeEmailRequests.put(key, user.getId());
    }

    public void updateEmail(String key){
        User user = userRepo.findById(changeEmailRequests.get(key))
                .orElseThrow(() -> new UserNotFoundException("User wasn't found!"));
        if(user == null){
            throw new RuntimeException("Confirmation Code is invalid!");
        }

        if(new Date().getTime() - Long.parseLong(key.split("_")[1]) > TIME_FOR_CONFIRMATION){
            throw new RuntimeException("Confirmation Code is invalid!");
        }
        userRepo.save(user);
    }

    public void updatePassword(User user, PasswordChangerDTO passwordChangerDTO) {
        user.setPassword(passwordChangerDTO.getPassword());
        userRepo.save(user);
    }

    public User getUser(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User doesnt exist!"));
    }

    public Page<User> getUsers(int page) {
        return userRepo.findAll(PageRequest.of(page, 20));
    }
}
