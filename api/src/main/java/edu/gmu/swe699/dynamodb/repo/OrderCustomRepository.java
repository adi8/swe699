package edu.gmu.swe699.dynamodb.repo;

import edu.gmu.swe699.dynamodb.model.Order;
import java.util.List;

public interface OrderCustomRepository {
  List<Order> getRestaurantOrders(String restaurantId);
}
