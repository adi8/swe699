package com.example.demo.composite.services;

import com.example.demo.api.Restaurant;
import com.example.demo.api.composite.CompositeService;
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
    public Restaurant getRestaurant(int restaurantId) {
        Restaurant restaurant = integration.getRestaurant(restaurantId);

        return restaurant;
    }
}
