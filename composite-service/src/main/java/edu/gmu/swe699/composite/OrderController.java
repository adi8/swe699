package edu.gmu.swe699.composite;

import edu.gmu.swe699.composite.services.CompositeServiceImpl;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping(value = "/order/review", consumes = "application/JSON", produces = "text/plain")
    @ResponseBody
    public String reviewOrder(@RequestBody Order order) {
        System.out.println("Order: " + order.toString());
        return "order-placed";
    }
}
