package com.example.demo.order;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.api.OrderService;
import com.example.demo.api.Restaurant;

@RestController
public class OrderServiceImpl implements OrderService {
    @Override
    public Restaurant getRestaurant(int restaurantId) {
        return new Restaurant(restaurantId, "name-" + restaurantId, new String[10]);
    }
}
