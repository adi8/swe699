package edu.gmu.swe699.order;

import org.springframework.web.bind.annotation.RestController;
import edu.gmu.swe699.api.OrderService;
import edu.gmu.swe699.api.Restaurant;

@RestController
public class OrderServiceImpl implements OrderService {
    @Override
    public Restaurant getRestaurant(int restaurantId) {
        return new Restaurant(restaurantId, "name-" + restaurantId, new String[10]);
    }
}
