package shop.template.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.template.onlineShop.DTO.CommentGetDTO;
import shop.template.onlineShop.DTO.CommentPostDTO;
import shop.template.onlineShop.DTO.CommentPutDTO;
import shop.template.onlineShop.entity.Comment;
import shop.template.onlineShop.entity.User;
import shop.template.onlineShop.service.CommentService;
import shop.template.onlineShop.utils.EntityMapperUtil;
import shop.template.onlineShop.utils.TextHardFilter;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/comments")
public class CommentController {


    @Autowired
    CommentService commentService;


    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    public void createComment(@AuthenticationPrincipal User user, @RequestBody CommentPostDTO commentPostDTO){
        System.out.println(user.getUsername());
        TextHardFilter.filterTextUnsecured(commentPostDTO.getText());
        commentService.createComment(user, commentPostDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{commentId}")
    public void updateComment(@AuthenticationPrincipal User user, @RequestBody CommentPutDTO commentPutDTO, @PathVariable("commentId") Long id){
        commentService.updateComment(user, id, commentPutDTO);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user")
    public ResponseEntity<Page<CommentGetDTO>> getUserComments(@AuthenticationPrincipal User user, @RequestParam("page") int page){
        return ResponseEntity.status(200)
                .body(
                        commentService.getUserComments(user, page)
                                .map(EntityMapperUtil::mapComment)
                );
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
        commentService.deleteCommentById(commentId);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/user/{commentId}")
    public void deleteUserComment(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal User user){
        commentService.deleteCommentById(user, commentId);

    }

}
