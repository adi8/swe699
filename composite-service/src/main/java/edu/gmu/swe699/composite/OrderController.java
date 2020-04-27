package edu.gmu.swe699.composite;

import edu.gmu.swe699.composite.services.CompositeServiceImpl;
import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import java.util.Arrays;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OrderController {

  private List<String> states = Arrays
      .asList("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
          "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
          "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
          "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
          "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
          "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
          "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
          "Wisconsin", "Wyoming");

  private List<String> countries = Arrays
      .asList("Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua & Deps",
          "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain",
          "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia",
          "Bosnia Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina", "Burundi",
          "Cambodia", "Cameroon", "Canada", "Cape Verde", "Central African Rep", "Chad", "Chile",
          "China", "Colombia", "Comoros", "Congo", "Congo {Democratic Rep}", "Costa Rica",
          "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica",
          "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador",
          "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Fiji", "Finland", "France",
          "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala",
          "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India",
          "Indonesia", "Iran", "Iraq", "Ireland {Republic}", "Israel", "Italy", "Ivory Coast",
          "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea North",
          "Korea South", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho",
          "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Madagascar",
          "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania",
          "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro",
          "Morocco", "Mozambique", "Myanmar, {Burma}", "Namibia", "Nauru", "Nepal", "Netherlands",
          "New Zealand", "Nicaragua", "Niger", "Nigeria", "Norway", "Oman", "Pakistan", "Palau",
          "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal",
          "Qatar", "Romania", "Russian Federation", "Rwanda", "St Kitts & Nevis", "St Lucia",
          "Saint Vincent & the Grenadines", "Samoa", "San Marino", "Sao Tome & Principe",
          "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore",
          "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Sudan",
          "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland", "Syria",
          "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Togo", "Tonga", "Trinidad & Tobago",
          "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine",
          "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan",
          "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe");

  private CompositeServiceImpl implementation;

  @Autowired
  public OrderController(CompositeServiceImpl implementation) {
    this.implementation = implementation;
  }

  @GetMapping("/order")
  public String menu(@RequestParam(name = "id", required = true) String id, Model model) {
    Restaurant restaurant = implementation.getRestaurant(id);
    model.addAttribute("restaurant", restaurant);
    return "order-landing";
  }

  @PostMapping(value = "/order", consumes = "application/JSON")
  public String review(@RequestBody Order order, Model model,
      RedirectAttributes redirectAttributes) {
    Order saveOrder = implementation.createOrder(order);
    return "redirect:/order/review?id=" + saveOrder.getId();
  }

  @GetMapping(value = "/order/review")
  public String review(@RequestParam(name = "id", required = true) String id, Model model) {
    Order order = implementation.getOrder(id);
    if (order.getStatus().equals("confirmed")) {
      return "redirect:/order/confirm?id=" + order.getId();
    }
    Restaurant restaurant = implementation.getRestaurant(order.getRestaurantId());
    model.addAttribute("orderConfirmDTO", new OrderConfirmDTO());
    model.addAttribute("order", order);
    model.addAttribute("restaurant", restaurant);
    model.addAttribute("states", states);
    model.addAttribute("countries", countries);
    return "order-review";
  }

  @PostMapping(value = "/order/review")
  public String review(@Valid OrderConfirmDTO orderConfirmDTO, BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      Order order = implementation.getOrder(orderConfirmDTO.getOrderId());
      Restaurant restaurant = implementation.getRestaurant(order.getRestaurantId());
      model.addAttribute("orderConfirmDTO", orderConfirmDTO);
      model.addAttribute("order", order);
      model.addAttribute("restaurant", restaurant);
      model.addAttribute("states", states);
      model.addAttribute("countries", countries);
      return "order-review";
    }
    Order confirmOrder = implementation.confirmOrder(orderConfirmDTO);
    return "redirect:/order/confirm?id=" + confirmOrder.getId();
  }

  @GetMapping(value = "/order/confirm")
  public String confirm(@RequestParam(name = "id", required = true) String id, Model model) {
    Order order = implementation.getOrder(id);
    Restaurant restaurant = implementation.getRestaurant(order.getRestaurantId());
    model.addAttribute("order", order);
    model.addAttribute("restaurant", restaurant);
    return "order-confirm";
  }
}
