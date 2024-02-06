package shop.template.onlineShop.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.template.onlineShop.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
