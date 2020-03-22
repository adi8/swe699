package edu.gmu.swe699.composite.services;

import edu.gmu.swe699.api.composite.CompositeService;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompositeServiceImpl implements CompositeService {

  private CompositeIntegration integration;

  @Autowired
  public CompositeServiceImpl(CompositeIntegration integration) {
    this.integration = integration;
  }

  @Override
  public Restaurant getRestaurant(String restaurantId) {
    Restaurant restaurant = integration.getRestaurant(restaurantId);

    return restaurant;
  }
}
