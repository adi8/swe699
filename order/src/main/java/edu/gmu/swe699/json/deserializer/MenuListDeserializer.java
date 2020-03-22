package edu.gmu.swe699.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import edu.gmu.swe699.restaurantmanager.model.MenuItem;
import edu.gmu.swe699.restaurantmanager.model.MenuList;
import java.io.IOException;
import java.util.Iterator;

public class MenuListDeserializer extends StdDeserializer<MenuList> {

  public MenuListDeserializer() {
    this(null);
  }

  public MenuListDeserializer(Class<?> vc) {
    super(vc);
  }

  public MenuList deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = jp.getCodec().readTree(jp);
    Iterator<String> itr = node.fieldNames();

    MenuList menuList = new MenuList();

    while (itr.hasNext()) {
      String id = itr.next();
      JsonNode curNode = node.get(id);
      String name = curNode.findValue("Name").asText();
      String description = curNode.findValue("Description").asText();
      double price = Double.parseDouble(curNode.findValue("Price").asText());

      MenuItem menuItem = new MenuItem(id, name, description, price);
      menuList.addMenuItem(menuItem);
    }
    return menuList;
  }
}
