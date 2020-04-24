package edu.gmu.swe699.tasks;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gmu.swe699.dynamodb.model.Order;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class SendConfirmedOrder {

  @Value("${app.restaurant-manager.host}")
  private String restaurantManagerHost;

  @Value("${app.restaurant-manager.port}")
  private String restaurantManagerPort;

  @Value("${app.restaurant-manager.base}")
  private String restaurantManagerBase;

  @Autowired
  private RestTemplate restTemplate;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  public void sendConfirmedOrder(Order order) {
    // Generate url to call
    String restaurantManagerOrderUrl =
        "http://" + restaurantManagerHost + ":" + restaurantManagerPort + "/"
            + restaurantManagerBase + "/order/send?orders=";
    ObjectMapper mapper = new ObjectMapper();
    String orderJsonStr = "";
    try {
      orderJsonStr = URLEncoder
          .encode(mapper.writeValueAsString(order), StandardCharsets.UTF_8.toString());
      restaurantManagerOrderUrl += "'" + orderJsonStr + "'";
      System.out.println("Sending: " + restaurantManagerOrderUrl);
      restTemplate.postForObject(restaurantManagerOrderUrl, null, Order.class);
    } catch (JsonProcessingException | UnsupportedEncodingException ex) {
      System.out.println("ERROR: " + ex.getMessage());
      ex.printStackTrace();
    } catch (HttpClientErrorException ex) {
      System.out.println("ERROR: " + ex.getMessage());
    }
  }
}
