package edu.gmu.swe699.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.gmu.swe699.restaurantmanager.model.RestaurantItem;
import edu.gmu.swe699.restaurantmanager.model.RestaurantList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class RestaurantListDeserializer extends StdDeserializer<RestaurantList> {

  public RestaurantListDeserializer() {
    this(null);
  }

  public RestaurantListDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public RestaurantList deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    Iterator<String> itr = node.fieldNames();

    RestaurantList restaurantList = new RestaurantList();

    while (itr.hasNext()) {
      String id = itr.next();
      JsonNode curNode = node.get(id);
      String name = curNode.findValue("Name").asText();
      String location = curNode.findValue("Street").asText() + ", " +
          curNode.findValue("City").asText() + ", " +
          curNode.findValue("State").asText() + ", " +
          curNode.findValue("Zipcode").asText();

      List<String> cuisines = new ArrayList<>();
      for (JsonNode category : curNode.findValue("Categories")) {
        cuisines.add(category.asText());
      }
      RestaurantItem restaurantItem = new RestaurantItem(id, name, location,
          new HashSet<>(cuisines));
      restaurantList.addRestaurantItem(restaurantItem);
    }
    return restaurantList;
  }
}
