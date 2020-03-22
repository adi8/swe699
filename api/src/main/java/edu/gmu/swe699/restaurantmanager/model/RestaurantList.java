package edu.gmu.swe699.restaurantmanager.model;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList {

  private List<RestaurantItem> restaurantItems;

  public RestaurantList() {
    this.restaurantItems = new ArrayList<>();
  }

  public List<RestaurantItem> getRestaurantList() {
    return this.restaurantItems;
  }

  public void setRestaurantItems(List<RestaurantItem> restaurantItems) {
    this.restaurantItems = restaurantItems;
  }

  public void addRestaurantItem(RestaurantItem restaurantItem) {
    this.restaurantItems.add(restaurantItem);
  }

  @Override
  public String toString() {
    StringBuilder restaurantListStr = new StringBuilder();
    restaurantListStr.append("RestaurantList[ ");
    for (RestaurantItem restaurantItem : restaurantItems) {
      restaurantListStr.append(restaurantItem.toString());
      restaurantListStr.append(" ");
    }
    restaurantListStr.append("]");
    return restaurantListStr.toString();
  }
}
