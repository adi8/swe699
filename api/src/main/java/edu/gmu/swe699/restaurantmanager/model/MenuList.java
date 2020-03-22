package edu.gmu.swe699.restaurantmanager.model;

import java.util.ArrayList;
import java.util.List;

public class MenuList {

  private List<MenuItem> menuItems;

  public MenuList() {
    this.menuItems = new ArrayList<>();
  }

  public List<MenuItem> getMenuItems() {
    return menuItems;
  }

  public void addMenuItem(MenuItem menuItem) {
    this.menuItems.add(menuItem);
  }

  @Override
  public String toString() {
    StringBuilder menuListStr = new StringBuilder();
    menuListStr.append("MenuList[ ");
    for (MenuItem menuItem : menuItems) {
      menuListStr.append(menuItem.toString());
      menuListStr.append(" ");
    }
    menuListStr.append("]");
    return menuListStr.toString();
  }

}
