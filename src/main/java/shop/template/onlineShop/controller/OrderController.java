package shop.template.onlineShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shop.template.onlineShop.DTO.OrderPostDTO;
import shop.template.onlineShop.entity.Order;
import shop.template.onlineShop.entity.User;
import shop.template.onlineShop.enumerator.OrderStatus;
import shop.template.onlineShop.service.MailService;
import shop.template.onlineShop.service.OrderService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    //Order Controller is already done

    @Autowired
    MailService mailService;
    @Autowired
    OrderService orderService;


    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/")
    public void createOrder(@AuthenticationPrincipal User user, @RequestBody OrderPostDTO orderPostDTO){
        orderService.createOrder(user, orderPostDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @PatchMapping("/{id}/status")
    public void changeStatusOfOrder(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status){
        orderService.changeStatus(id, status);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<Order> getUserOrder(@PathVariable("id") Long orderId, @AuthenticationPrincipal User user){
        return ResponseEntity.status(200)
                .body(orderService.getOrderOfUser(user, orderId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id){
        return ResponseEntity.status(200)
                .body(orderService.getOrderById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/byDate")
    public ResponseEntity<Page<Order>> getOrdersByDate(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
            @RequestParam("page") int page){
        return ResponseEntity.status(200)
                .body(orderService.getOrdersByDate(start, end, page));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/")
    public ResponseEntity<Page<Order>> getOrders(@RequestParam(value = "page", defaultValue = "0") int page){
        return ResponseEntity.status(200)
                .body(orderService.getOrders(page));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/myOrders")
    public ResponseEntity<Page<Order>> getMyOrders(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @AuthenticationPrincipal User user){
        return ResponseEntity.status(200)
                .body(orderService.getMyOrders(user, page));
    }

}
