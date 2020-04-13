package edu.gmu.swe699.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class OrderMenuItem {

  private String id;
  private String name;
  private double price;
  private int qty;

  public OrderMenuItem() {

  }

  public OrderMenuItem(String id, String name, double price, int qty) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.qty = qty;
  }

  @DynamoDBAttribute(attributeName = "Id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @DynamoDBAttribute(attributeName = "Name")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @DynamoDBAttribute(attributeName = "Price")
  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @DynamoDBAttribute(attributeName = "Qty")
  public int getQty() {
    return qty;
  }

  public void setQty(int qty) {
    this.qty = qty;
  }

  @Override
  public String toString() {
    return String
        .format("OrderMenuItem[id='%s', name='%s', price='%.2f', qty='%d']", id, name, price, qty);
  }

  @Override
  public boolean equals(Object o) {
    OrderMenuItem orderMenuItem = (OrderMenuItem) o;
    return orderMenuItem != null && this.id.equals(orderMenuItem.getId())
        && this.price == orderMenuItem.getPrice();
  }
}
