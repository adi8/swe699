package edu.gmu.swe699.composite.services;

import edu.gmu.swe699.api.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CompositeIntegration implements OrderService {

  private final RestTemplate restTemplate;
  private final ObjectMapper mapper;

  private final String orderServiceUrl;

  @Autowired
  public CompositeIntegration(
      RestTemplate restTemplate,
      ObjectMapper mapper,

      @Value("${app.order-service.host}") String orderServiceHost,
      @Value("${app.order-service.port}") int orderServicePort
  ) {
    this.restTemplate = restTemplate;
    this.mapper = mapper;

    orderServiceUrl = "http://" + orderServiceHost + ":" + orderServicePort + "/order/";
  }

  @Override
  public Restaurant getRestaurant(String restaurantId) {
    try {
      String url = orderServiceUrl + restaurantId;

      Restaurant restaurant = restTemplate.getForObject(url, Restaurant.class);

      return restaurant;
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      throw ex;
    }
  }
}
