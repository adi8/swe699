package edu.gmu.swe699.composite.services;

import edu.gmu.swe699.api.order.OrderService;
import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CompositeIntegration implements OrderService {

  private final RestTemplate restTemplate;

  private final String orderServiceUrl;

  @Autowired
  public CompositeIntegration(
      RestTemplate restTemplate,

      @Value("${app.order-service.host}") String orderServiceHost,
      @Value("${app.order-service.port}") int orderServicePort
  ) {
    this.restTemplate = restTemplate;

    orderServiceUrl = "http://" + orderServiceHost + ":" + orderServicePort;
  }

  @Override
  public Restaurant getRestaurant(String restaurantId) {
    try {
      String url = orderServiceUrl + "/restaurant/menu/" + restaurantId;
      Restaurant restaurant = restTemplate.getForObject(url, Restaurant.class);
      return restaurant;
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      throw ex;
    }
  }

  @Override
  public Order createOrder(Order order) {
    try {
      String url = orderServiceUrl + "/order";
      Order savedOrder = restTemplate.postForObject(url, order, Order.class);
      return savedOrder;
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      throw ex;
    }
  }

  @Override
  public Order getOrder(String orderId) {
    try {
      String url = orderServiceUrl + "/order/" + orderId;
      Order order = restTemplate.getForObject(url, Order.class);
      return order;
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      throw ex;
    }
  }

  @Override
  public Order confirmOrder(OrderConfirmDTO orderConfirmDTO) {
    try {
      String url = orderServiceUrl + "/order/confirm";
      Order order = restTemplate.postForObject(url, orderConfirmDTO, Order.class);
      return order;
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      throw ex;
    }
  }

  public List<Order> getRestaurantOrders(String restaurantId) {
    try {
      String url = orderServiceUrl + "/order/restaurant/" + restaurantId;
      Order[] orders = restTemplate.getForObject(url, Order[].class);
      return Arrays.asList(orders);
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      throw ex;
    }
  }
}
