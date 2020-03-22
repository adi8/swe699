package edu.gmu.swe699.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class MenuItem {

  private String id;
  private String name;
  private String description;
  private double price;

  public MenuItem() {

  }

  public MenuItem(String id, String name, String description, double price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  @DynamoDBAttribute(attributeName = "id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @DynamoDBAttribute(attributeName = "name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @DynamoDBAttribute(attributeName = "description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @DynamoDBAttribute(attributeName = "price")
  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return String
        .format("MenuItem[id='%s', name='%s', desc='%s', price='%.2f'", id, name, description,
            price);
  }
}
