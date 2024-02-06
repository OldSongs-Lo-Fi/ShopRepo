package shop.template.onlineShop.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.template.onlineShop.entity.Order;
import shop.template.onlineShop.entity.User;

import java.util.Date;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Page<Order> findAllByUser(User user, Pageable pageable);
    Page<Order> findOrdersByDateIsBetween(Date start, Date end, Pageable pageable);
}
