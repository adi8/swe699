package edu.gmu.swe699.tasks;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import edu.gmu.swe699.json.deserializer.MenuListDeserializer;
import edu.gmu.swe699.json.deserializer.RestaurantListDeserializer;
import edu.gmu.swe699.mapstruct.RestaurantMapper;
import edu.gmu.swe699.restaurantmanager.model.MenuList;
import edu.gmu.swe699.restaurantmanager.model.RestaurantItem;
import edu.gmu.swe699.restaurantmanager.model.RestaurantList;
import javax.inject.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class DynamoDBSync {

  @Value("${app.restaurant-manager.host}")
  private String restaurantManagerHost;

  @Value("${app.restaurant-manager.port}")
  private String restaurantMangerPort;

  @Value("${app.restaurant-manager.base}")
  private String restaurantManagerBase;

  @Autowired
  private RestTemplate restTemplate;

  @Inject
  private RestaurantMapper restaurantMapper;

  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Scheduled(fixedDelay = 900000)
  public void syncRestaurantInfo() {
    // Generate url to call
    String restaurantManagerApiUrl =
        "http://" + restaurantManagerHost + ":" + restaurantMangerPort +
            "/" + restaurantManagerBase;

    String listRestaurantsUrl = restaurantManagerApiUrl + "/restaurant/list";
    String listRestaurantMenuUrl = restaurantManagerApiUrl + "/menu";

    ObjectMapper mapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(RestaurantList.class, new RestaurantListDeserializer());
    module.addDeserializer(MenuList.class, new MenuListDeserializer());
    mapper.registerModule(module);

    try {
      String response = restTemplate.getForObject(listRestaurantsUrl, String.class);
      RestaurantList restaurantList = mapper.readValue(response, RestaurantList.class);

      for (RestaurantItem restaurantItem : restaurantList.getRestaurantList()) {
        String url = listRestaurantMenuUrl + "/" + restaurantItem.getId();
        response = restTemplate.getForObject(url, String.class);
        MenuList menuList = mapper.readValue(response, MenuList.class);
        restaurantItem.setMenuList(menuList);
        System.out.println("Restaurant Manger Object: " + restaurantItem.toString());
        Restaurant restaurant = restaurantMapper.toRestaurant(restaurantItem);
        System.out.println(
            "Restaurant Object (Dynamo): " + restaurant.toString());
        dynamoDBMapper.save(restaurant);
      }
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    } catch (JsonProcessingException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }
  }
}
