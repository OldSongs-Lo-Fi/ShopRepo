package shop.template.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.template.onlineShop.DTO.CommentGetDTO;
import shop.template.onlineShop.DTO.ProductGetDTO;
import shop.template.onlineShop.DTO.ProductPostDTO;
import shop.template.onlineShop.DTO.ProductPutDTO;
import shop.template.onlineShop.entity.Comment;
import shop.template.onlineShop.entity.Product;
import shop.template.onlineShop.repo.ProductRepo;
import shop.template.onlineShop.service.ProductService;
import shop.template.onlineShop.utils.EntityMapperUtil;

import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/")
    public void createProduct(@RequestBody ProductPostDTO productPostDTO){
        productService.createProduct(productPostDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductGetDTO> getProductById(@PathVariable("id") Long id){
        return ResponseEntity.status(200)
                .body(EntityMapperUtil.mapProduct(productService.getProductById(id)));
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProductGetDTO>> getProducts(@RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.status(200)
                .body(productService.getProducts(page)
                        .map(EntityMapperUtil::mapProduct));
    }

    @GetMapping("/{productId}/comments")
    public ResponseEntity<Page<CommentGetDTO>> getProductComments(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @PathVariable("productId") Long id){
        return ResponseEntity.status(200)
                .body(productService.getProductCommentsById(id, page)
                        .map(EntityMapperUtil::mapComment));
    }
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProductById(productId);
    }

    @PatchMapping("/{productId}/available")
    public void updateProductActivity(@PathVariable("productId") Long productId,
                                      @RequestParam(value = "available", defaultValue = "true") boolean available){
        productService.setProductActivity(productId, available);
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable("productId") Long productId,
                              @RequestBody ProductPutDTO productPutDTO){
        productService.updateProduct(productId, productPutDTO);
    }

}
