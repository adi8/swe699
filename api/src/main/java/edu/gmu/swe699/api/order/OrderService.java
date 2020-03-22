package edu.gmu.swe699.api.order;

import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface OrderService {
    @GetMapping(
        value = "/order/{restaurantId}",
        produces = "application/json")
    Restaurant getRestaurant(@PathVariable String restaurantId);
}
