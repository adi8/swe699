package edu.gmu.swe699.order;

import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.MenuItem;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.OrderMenuItem;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import edu.gmu.swe699.dynamodb.repo.OrderRepository;
import edu.gmu.swe699.dynamodb.repo.RestaurantRepository;
import edu.gmu.swe699.tasks.SendConfirmedOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import edu.gmu.swe699.api.order.OrderService;

@RestController
public class OrderServiceImpl implements OrderService {

  RestaurantRepository restaurantRepository;
  OrderRepository orderRepository;
  SendConfirmedOrder sendConfirmedOrder;

  @Autowired
  public OrderServiceImpl(RestaurantRepository restaurantRepository,
      OrderRepository orderRepository, SendConfirmedOrder sendConfirmedOrder) {
    this.restaurantRepository = restaurantRepository;
    this.orderRepository = orderRepository;
    this.sendConfirmedOrder = sendConfirmedOrder;
  }

  @Override
  public Restaurant getRestaurant(String restaurantId) {
    Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
    return restaurant.isPresent() ? restaurant.get() : null;
  }

  @Override
  public Order createOrder(Order order) {
    // Validate if restaurant exists
    Optional<Restaurant> restaurant = restaurantRepository.findById(order.getRestaurantId());
    if (!restaurant.isPresent()) {
      return null;
    }

    // Validate if menu item exists.
    for (OrderMenuItem orderMenuItem : order.getOrderMenuItems()) {
      if (!restaurant.get()
          .hasMenuItem(new MenuItem(orderMenuItem.getId(), orderMenuItem.getPrice()))) {
        return null;
      }
    }

    // Create a new uuid for the order being saved
    String uuid = UUID.randomUUID().toString();
    order.setId(uuid);

    // Set state of the order to review
    order.setStatus("review");

    orderRepository.save(order);
    return order;
  }

  @Override
  public Order getOrder(String orderId) {
    Optional<Order> order = orderRepository.findById(orderId);
    return order.isPresent() ? order.get() : null;
  }

  @Override
  public Order confirmOrder(OrderConfirmDTO orderConfirmDTO) {
    Optional<Order> orderOptional = orderRepository.findById(orderConfirmDTO.getOrderId());

    if (!orderOptional.isPresent()) {
      return null;
    }

    Order order = orderOptional.get();

    if (order.getStatus().equals("confirmed"))
      return order;

    order.setDeliveryAddr(orderConfirmDTO.getDeliveryAddress());
    order.setDeliveryInst(orderConfirmDTO.getDeliveryInst());
    order.setStatus("confirmed");
    orderRepository.save(order);
    sendConfirmedOrder.sendConfirmedOrder(order);
    return order;
  }

  @Override
  public List<Order> getRestaurantOrders(String restaurantId) {
    return orderRepository.getRestaurantOrders(restaurantId);
  }
}
