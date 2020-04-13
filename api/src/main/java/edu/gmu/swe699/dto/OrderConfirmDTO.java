package edu.gmu.swe699.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderConfirmDTO {

  private String orderId;
  private String streetAddr1;
  private String streetAddr2;
  private String city;
  private String state;
  private String country;
  private String deliveryInst;
  private String nameOnCard;
  private String cardNumber;
  private String cardValidThru;
  private String cardCvv;
  private String zipCode;

  public String getDeliveryAddress() {
    StringBuilder deliveryAddress = new StringBuilder();
    deliveryAddress.append(streetAddr1).append(", ");
    if (!streetAddr2.isEmpty())
      deliveryAddress.append(streetAddr2).append(", ");
    deliveryAddress.append(city).append(", ")
        .append(state).append(", ")
        .append(country).append(", ")
        .append(zipCode);
    return deliveryAddress.toString();
  }

  @Override
  public String toString() {
    return String.format(
        "OrderConfirmDTO[orderId='%s', streetAddr1='%s', streetAddr2='%s', city='%s', state='%s', country='%s', deliveryInst='%s', nameOnCard='%s', cardNumber='%s', cardValidThru='%s', cardCvv='%s', zipCode='%s']",
        orderId, streetAddr1, streetAddr2, city, state, country, deliveryInst, nameOnCard,
        cardNumber, cardValidThru, cardCvv, zipCode);
  }
}
