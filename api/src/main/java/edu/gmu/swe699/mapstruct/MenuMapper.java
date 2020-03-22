package edu.gmu.swe699.mapstruct;

import edu.gmu.swe699.dynamodb.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class MenuMapper {

  @Mapping(source = "menuItem.id", target = "id")
  @Mapping(source = "menuItem.name", target = "name")
  @Mapping(source = "menuItem.description", target = "description")
  @Mapping(source = "menuItem.price", target = "price")
  public abstract MenuItem menuItemToMenu(edu.gmu.swe699.restaurantmanager.model.MenuItem menuItem);
}
