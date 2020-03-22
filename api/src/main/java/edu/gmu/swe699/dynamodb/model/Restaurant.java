package edu.gmu.swe699.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import java.util.List;
import java.util.Set;

@DynamoDBTable(tableName = "Restaurant")
public class Restaurant {

  private String id;
  private String name;
  private String description;
  private String address;
  private Set<String> cuisines;
  private List<MenuItem> menu;

  public Restaurant() {

  }

  public Restaurant(String id, String name, String description, String address,
      Set<String> cuisines) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.address = address;
    this.cuisines = cuisines;
  }

  public Restaurant(String id, String name, String description, String address,
      Set<String> cuisines,
      List<MenuItem> menu) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.address = address;
    this.cuisines = cuisines;
    this.menu = menu;
  }

  @DynamoDBHashKey(attributeName = "Id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @DynamoDBAttribute(attributeName = "Name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @DynamoDBAttribute(attributeName = "Description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @DynamoDBAttribute(attributeName = "Address")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @DynamoDBAttribute(attributeName = "Category")
  public Set<String> getCuisines() {
    return cuisines;
  }

  public void setCuisines(Set<String> cuisines) {
    this.cuisines = cuisines;
  }

  @DynamoDBAttribute(attributeName = "Menu")
  public List<MenuItem> getMenu() {
    return menu;
  }

  public void setMenu(List<MenuItem> menu) {
    this.menu = menu;
  }

  @Override
  public String toString() {
    StringBuilder restaurantMenu = new StringBuilder();
    restaurantMenu.append("Menu[ ");
    if (menu != null && !menu.isEmpty()) {
      for (MenuItem item : menu) {
        restaurantMenu.append(item.toString());
        restaurantMenu.append(" ");
      }
    }
    restaurantMenu.append("]");

    return String
        .format("Restaurant[id=%s, name='%s', desc='%s', addr='%s', cuisines='%s', menu='%s']",
            id, name, description, address, cuisines, restaurantMenu.toString());
  }
}
