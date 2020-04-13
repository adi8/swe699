package edu.gmu.swe699.api.order;

import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface OrderService {
    @GetMapping(
        value = "/restaurant/menu/{restaurantId}",
        produces = "application/json")
    Restaurant getRestaurant(@PathVariable String restaurantId);

    @PostMapping(
        value = "/order",
        consumes = "application/json",
        produces = "application/json"
    )
    Order createOrder(@RequestBody Order order);

    @GetMapping(
        value = "/order/{orderId}",
        produces = "application/json"
    )
    Order getOrder(@PathVariable String orderId);

    @PostMapping(
        value ="/order/confirm",
        produces = "application/json"
    )
    Order confirmOrder(@RequestBody OrderConfirmDTO orderConfirmDTO);

    @GetMapping(
        value = "/order/restaurant/{restaurantId}",
        produces = "application/json"
    )
    List<Order> getRestaurantOrders(@PathVariable String restaurantId);
}
