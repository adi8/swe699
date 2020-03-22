package edu.gmu.swe699.api.composite;

import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface CompositeService {
    @GetMapping(
            value = "/{restaurantId}",
            produces = "application/json"
    )
    Restaurant getRestaurant(@PathVariable String restaurantId);
}
