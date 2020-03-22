package edu.gmu.swe699.order;

import edu.gmu.swe699.dynamodb.model.Restaurant;
import edu.gmu.swe699.dynamodb.repo.RestaurantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import edu.gmu.swe699.api.order.OrderService;

@RestController
public class OrderServiceImpl implements OrderService {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Override
  public Restaurant getRestaurant(String restaurantId) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
    return restaurant.isPresent() ? restaurant.get() : null;
  }
}
