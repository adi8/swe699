package edu.gmu.swe699.composite.services;

import edu.gmu.swe699.api.composite.CompositeService;
import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeServiceImpl implements CompositeService {

  @Autowired
  private CompositeIntegration integration;

  public CompositeServiceImpl() {
  }

  @Override
  public Restaurant getRestaurant(String restaurantId) {
    return integration.getRestaurant(restaurantId);
  }

  public Order createOrder(Order order) {
    return integration.createOrder(order);
  }

  @Override
  public Order getOrder(String orderId) {
    return integration.getOrder(orderId);
  }

  public Order confirmOrder(OrderConfirmDTO orderConfirmDTO) {
    return integration.confirmOrder(orderConfirmDTO);
  }

  @Override
  public List<Order> getRestaurantOrders(String restaurantId) {
    return integration.getRestaurantOrders(restaurantId);
  }
}
