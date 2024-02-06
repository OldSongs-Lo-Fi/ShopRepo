package shop.template.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import shop.template.onlineShop.DTO.OrderPostDTO;
import shop.template.onlineShop.entity.Order;
import shop.template.onlineShop.entity.OrderItem;
import shop.template.onlineShop.entity.User;
import shop.template.onlineShop.enumerator.OrderStatus;
import shop.template.onlineShop.repo.OrderRepo;
import shop.template.onlineShop.repo.ProductRepo;
import shop.template.onlineShop.repo.UserRepo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    MailService mailService;

    public void createOrder(User user, OrderPostDTO o){
        Order order = new Order();
        order.setDate(new Date());
        order.setDescription(o.getDescription());
        order.setAdditionalComment(o.getAdditionalComment());
        order.setUser(user);

        List<OrderItem> orderItems = o.getProducts().stream().map(orderItemDTO -> new OrderItem(
                productRepo.findById(orderItemDTO.getItemId())
                .orElseThrow(()-> new ResourceNotFoundException("Product didnt found!")), orderItemDTO.getCount())).toList();

        BigDecimal summary = BigDecimal.valueOf(0);

        for (OrderItem orderItem: orderItems
             ) {
            summary.add(orderItem.getSummaryPrice());
        }

        order.setSummaryPrice(summary);
        order.setProducts(orderItems);

        mailService.notifyShopSupport(order);
        orderRepo.save(order);
    }

    public Page<Order> getOrders(int page) {
        return orderRepo.findAll(PageRequest.of(page, 20));
    }

    public Order getOrderById(Long id){
        return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order doesnt exist!"));
    }

    public Page<Order> getMyOrders(User user, int page){
        return orderRepo.findAllByUser(user, PageRequest.of(page, 5));
    }

    public void changeStatus(Long id, OrderStatus status) {
        Order order = orderRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order wasn't found!"));
        order.setOrderStatus(status);
        orderRepo.save(order);
    }

    public Order getOrderOfUser(User user, Long orderId) {
        return user.getOrders().stream().filter(orderOfUser ->{
            return orderOfUser.getId().equals(orderId);
        }).findFirst().orElseThrow(() -> new ResourceNotFoundException("User doesnt have this order"));
    }

    public Page<Order> getOrdersByDate(Date start, Date end, int page) {
        return orderRepo.findOrdersByDateIsBetween(start, end, PageRequest.of(page, 20));
    }

    //Оповестить поддержку
}
