package shop.template.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import shop.template.onlineShop.DTO.ProductPostDTO;
import shop.template.onlineShop.DTO.ProductPutDTO;
import shop.template.onlineShop.entity.Comment;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.repo.CommentRepo;
import shop.template.onlineShop.repo.ProductRepo;

import java.util.Set;

@Service
public class ProductService {
    @Autowired
    ProductRepo productRepo;

    @Autowired
    CommentRepo commentRepo;

    public void createProduct(ProductPostDTO p){
        Product product = new Product(p.getName(), p.getDescription(), p.getPrice());
        productRepo.save(product);
    }

    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product EROOR"));
    }

    public Page<Product> getProducts(int page) {
        return productRepo.findAll(PageRequest.of(page, 30));
    }

    public void deleteProductById(Long productId) {
        productRepo.deleteById(productId);
    }

    public Page<Comment> getProductComments(Long productId, int page) {
        return commentRepo.findCommentsByProduct(productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist!")),
                PageRequest.of(page, 10));
    }

    public Page<Comment> getProductCommentsById(Long productId, int page) {
        return commentRepo.findCommentsByProductId(productId,
                PageRequest.of(page, 10));
    }

    public void setProductActivity(Long productId, boolean available) {
        Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product doesn't exist!"));
        product.setAvailable(available);
        productRepo.save(product);
    }

    public void updateProduct(Long productId, ProductPutDTO productPutDTO) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product doesn't exist!"));
        product.setName(productPutDTO.getName());
        product.setDescription(productPutDTO.getDescription());
        product.setPrice(productPutDTO.getPrice());
        product.setRating(productPutDTO.getRating());
        productRepo.save(product);

    }
}
