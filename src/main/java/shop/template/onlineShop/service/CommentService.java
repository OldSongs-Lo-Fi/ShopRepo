package shop.template.onlineShop.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import shop.template.onlineShop.DTO.CommentPostDTO;
import shop.template.onlineShop.DTO.CommentPutDTO;
import shop.template.onlineShop.entity.Comment;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.entity.User;
import shop.template.onlineShop.repo.CommentRepo;
import shop.template.onlineShop.repo.ProductRepo;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {
    final static int PAGE_SIZE = 10;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    ProductRepo productRepo;

    public void createComment(User author, CommentPostDTO commentPostDTO){
        Product product = productRepo.findById(commentPostDTO.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Product wasnt found!"));

        commentRepo.save(new Comment(author, product, new Date(), commentPostDTO.getText(), commentPostDTO.getRating()));
    }


    public void updateComment(User user, Long id, CommentPutDTO commentPutDTO) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment wasn't found!"));
        // TODO: Проверить работоспособность данного сравнения
        if (!comment.getAuthor().getId().equals(user.getId())){
            throw new ResourceNotFoundException("Comment wasn't found!");
        }
        /*Comment comment = user.getComments().stream()
                .filter(comment1 -> comment1.getId().equals(id)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment doesnt exist!"));*/
        comment.setText(commentPutDTO.getText());
        comment.setRating(commentPutDTO.getRating());
        commentRepo.save(comment);
    }

    public Page<Comment> getUserComments(User user, int page) {
        return commentRepo.findCommentsByAuthorId(user.getId(), PageRequest.of(page, PAGE_SIZE));
    }


    public void deleteCommentById(Long commentId) {
        commentRepo.deleteById(commentId);
    }

    public void deleteCommentById(User user, Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment doesn't exist!"));
        //TODO: Проверить работоспособность метода contains()
        if (!user.getComments().contains(comment)){
            throw new ResourceNotFoundException("Comment doesn't exist!");
        }
        commentRepo.delete(comment);
    }
}
