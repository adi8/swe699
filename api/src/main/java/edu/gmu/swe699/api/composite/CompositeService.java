package edu.gmu.swe699.api.composite;

import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CompositeService {
    @GetMapping(
            value = "/restaurant/{restaurantId}",
            produces = "application/json"
    )
    Restaurant getRestaurant(@PathVariable String restaurantId);

    @GetMapping(
        value = "/order/{orderId}",
        produces = "application/json"
    )
    Order getOrder(@PathVariable String orderId);

    @GetMapping(
        value = "/order/restaurant/{restaurantId}",
        produces = "application/json"
    )
    List<Order> getRestaurantOrders(@PathVariable String restaurantId);
}
