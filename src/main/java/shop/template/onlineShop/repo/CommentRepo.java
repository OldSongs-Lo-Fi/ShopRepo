package shop.template.onlineShop.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.template.onlineShop.entity.Comment;
import shop.template.onlineShop.entity.Product;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    Page<Comment> findCommentsByProduct(Product product, Pageable pageable);

    Page<Comment> findCommentsByProductId(Long productId, Pageable pageable);

    Page<Comment> findCommentsByAuthorId(Long authorId, Pageable pageable);
}
