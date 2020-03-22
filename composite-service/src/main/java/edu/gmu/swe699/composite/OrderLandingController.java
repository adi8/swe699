package edu.gmu.swe699.composite;

import edu.gmu.swe699.composite.services.CompositeServiceImpl;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderLandingController {

    private CompositeServiceImpl implementation;

    @Autowired
    public OrderLandingController(CompositeServiceImpl implementation) {
        this.implementation = implementation;
    }

    @GetMapping("/order")
    public String orderLanding(@RequestParam(name="id", required=true) String id, Model model) {
        Restaurant restaurant = implementation.getRestaurant(id);
        model.addAttribute("restaurant", restaurant);
        return "order-landing";
    }
}
