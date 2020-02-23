package com.example.demo.api;

public class Restaurant {
    private final int restaurantId;
    private final String name;
    private final String menuItems[];

    public Restaurant() {
        restaurantId = 0;
        name = null;
        menuItems = new String[10];
    }

    public Restaurant(int restaurantId, String name, String menuItems[]) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.menuItems = menuItems;
    }

    public int getRestaurantId() {
        return restaurantId;
    }
    
    public String getRestaurantName() {
        return name;
    }

    public String[] getRestaurantMenu() {
        return menuItems;
    }
}
