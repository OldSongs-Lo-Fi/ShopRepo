package shop.template.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.template.onlineShop.exception.UserNotFoundException;
import shop.template.onlineShop.repo.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
    }
}
