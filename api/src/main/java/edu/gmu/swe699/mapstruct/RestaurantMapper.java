package edu.gmu.swe699.mapstruct;


import edu.gmu.swe699.dynamodb.model.MenuItem;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import edu.gmu.swe699.restaurantmanager.model.MenuList;
import edu.gmu.swe699.restaurantmanager.model.RestaurantItem;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RestaurantMapper {

  @Mapping(source = "restaurantItem.id", target = "id")
  @Mapping(source = "restaurantItem.name", target = "name")
  @Mapping(target = "description", constant = "Restaurant description")
  @Mapping(source = "restaurantItem.location", target = "address")
  @Mapping(source = "restaurantItem.categories", target = "cuisines")
  @Mapping(source = "restaurantItem.menuList.menuItems", target = "menu")
  public abstract Restaurant toRestaurant(RestaurantItem restaurantItem);

}
