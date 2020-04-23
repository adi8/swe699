package edu.gmu.swe699.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderConfirmDTO {

  private String orderId;

  @NotBlank(message = "Street Address line 1 must not be blank.")
  private String streetAddr1;

  private String streetAddr2;

  @NotBlank(message = "City must not be blank.")
  private String city;

  @NotBlank(message = "State must be selected.")
  private String state;

  @NotBlank(message = "Country must be selected.")
  private String country;

  @NotBlank(message = "Zicode must not be blank.")
  @Size(min = 4, max = 6, message = "Zipcode should be min. 4 and max. 6 digits in length.")
  @Digits(fraction = 0, integer = 6, message = "Zipcode must comprise of only numbers.")
  private String zipCode;

  private String deliveryInst;

  @NotBlank(message = "Name on card must not be blank.")
  private String nameOnCard;

  @NotBlank(message = "Card Number must not be blank.")
  @Size(min = 16, max = 16, message = "Card Number should be of length 16.")
  @Digits(fraction = 0, integer = 16, message = "Card Number must comprise of only numbers.")
  private String cardNumber;

  @NotBlank(message = "Valid Thru must not be blank.")
  @Size(min = 5, max = 5, message = "Valid Thru must in MM/YY format.")
  private String cardValidThru;

  @NotBlank(message = "CVV must not be blank.")
  @Size(min = 3, max = 3, message = "CVV should be of length 3.")
  @Digits(fraction = 0, integer = 3, message = "CVV must comprise of only numbers.")
  private String cardCvv;

  public String getDeliveryAddress() {
    StringBuilder deliveryAddress = new StringBuilder();
    deliveryAddress.append(streetAddr1).append(" ");
    if (!streetAddr2.isEmpty())
      deliveryAddress.append(streetAddr2).append(" ");
    deliveryAddress.append(city).append(" ")
        .append(state).append(" ")
        .append(country).append(" ")
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
