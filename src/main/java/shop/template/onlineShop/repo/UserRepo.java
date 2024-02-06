package shop.template.onlineShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.template.onlineShop.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
}
