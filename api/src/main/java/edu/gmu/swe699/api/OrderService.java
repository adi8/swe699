package edu.gmu.swe699.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface OrderService {
    @GetMapping(
        value = "/order/{restaurantId}",
        produces = "application/json")
    Restaurant getRestaurant(@PathVariable int restaurantId);
}
