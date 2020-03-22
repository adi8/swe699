package edu.gmu.swe699.restaurantmanager.model;

import java.util.Set;

public class RestaurantItem {

  private String id;
  private String name;
  private String location;
  private Set<String> categories;
  private MenuList menuList;

  public RestaurantItem() {

  }

  public RestaurantItem(String id, String name, String location, Set<String> categories) {
    this.id = id;
    this.name = name;
    this.location = location;
    this.categories = categories;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<String> getCategories() {
    return categories;
  }

  public void setCategories(Set<String> categories) {
    this.categories =  categories;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public MenuList getMenuList() {
    return menuList;
  }

  public void setMenuList(MenuList menuList) {
    this.menuList = menuList;
  }

  @Override
  public String toString() {
    return String
        .format("Restaurant[id='%s', name='%s', location='%s', categories='%s', menuList='%s']",
            id, name, location, categories, menuList.toString());
  }
}
