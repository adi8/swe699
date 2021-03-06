package edu.gmu.swe699;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.OrderMenuItem;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import edu.gmu.swe699.dynamodb.repo.OrderRepository;
import edu.gmu.swe699.dynamodb.repo.RestaurantRepository;
import edu.gmu.swe699.order.OrderServiceImpl;
import edu.gmu.swe699.tasks.SendConfirmedOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

  private static final Logger logger = LogManager.getLogger("OrderServiceImplTest");

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private SendConfirmedOrder sendConfirmedOrder;

  @InjectMocks
  @Autowired
  private OrderServiceImpl orderServiceImpl;

  private String getTestName() {
    return Thread.currentThread().getStackTrace()[3].getMethodName();
  }

  private void printSuccessMessage() {
    logger.info("{} succeeded", getTestName());
  }

  @Test
  public void getOrderDoesNotExistsTest() {
    when(orderRepository.findById(any())).thenReturn(Optional.empty());

    Order order = orderServiceImpl.getOrder(any());

    verify(orderRepository, times(1)).findById(any());
    assertNull(order);
    printSuccessMessage();
  }

  @Test
  public void getOrderExistsTest() {
    when(orderRepository.findById(any())).thenReturn(Optional.of(new Order()));

    Order order = orderServiceImpl.getOrder(any());

    verify(orderRepository, times(1)).findById(any());
    assertNotNull(order);
    printSuccessMessage();
  }

  @Test
  public void getRestaurantDoesNotExistsTest() {
    when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

    Restaurant restaurant = orderServiceImpl.getRestaurant("");

    verify(restaurantRepository, times(1)).findById(any());
    assertNull(restaurant);
    printSuccessMessage();
  }

  @Test
  public void getRestaurantExistsTest() {
    when(restaurantRepository.findById(any())).thenReturn(Optional.of(new Restaurant()));

    Restaurant restaurant = orderServiceImpl.getRestaurant("");

    verify(restaurantRepository, times(1)).findById(any());
    assertNotNull(restaurant);
    printSuccessMessage();
  }

  @Test
  public void createOrderRestaurantDoesNotExistTest() {
    Order order = mock(Order.class);

    when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

    Order createdOrder = orderServiceImpl.createOrder(order);
    verify(restaurantRepository, times(1)).findById(any());
    assertNull(createdOrder);
    printSuccessMessage();
  }

  @Test
  public void createOrderMenuItemDoesNotExistTest() {
    Restaurant restaurant = mock(Restaurant.class);
    Order order = mock(Order.class);
    OrderMenuItem dummyItem = mock(OrderMenuItem.class);
    List<OrderMenuItem> menuItemList = Arrays.asList(dummyItem, dummyItem);

    when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));
    when(order.getOrderMenuItems()).thenReturn(menuItemList);
    when(restaurant.hasMenuItem(any())).thenReturn(false);

    Order createdOrder = orderServiceImpl.createOrder(order);

    verify(restaurantRepository, times(1)).findById(any());
    verify(order, times(1)).getOrderMenuItems();
    verify(restaurant, times(1)).hasMenuItem(any());
    assertNull(createdOrder);
    printSuccessMessage();
  }

  @Test
  public void createOrderSucceedsTest() {
    Restaurant restaurant = mock(Restaurant.class);
    Order order = mock(Order.class);
    OrderMenuItem dummyItem = mock(OrderMenuItem.class);
    List<OrderMenuItem> menuItemList = Arrays.asList(dummyItem, dummyItem);

    when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));
    when(order.getOrderMenuItems()).thenReturn(menuItemList);
    when(restaurant.hasMenuItem(any())).thenReturn(true);

    Order createdOrder = orderServiceImpl.createOrder(order);

    verify(restaurantRepository, times(1)).findById(any());
    verify(order, times(1)).getOrderMenuItems();
    verify(restaurant, times(2)).hasMenuItem(any());
    verify(order, times(1)).setId(any());
    verify(order, times(1)).setStatus(any());
    assertNotNull(createdOrder);
    printSuccessMessage();
  }

  @Test
  public void confirmOrderDoesNotExist() {
    OrderConfirmDTO orderConfirmDTO = mock(OrderConfirmDTO.class);

    when(orderRepository.findById(any())).thenReturn(Optional.empty());

    Order confirmedOrder = orderServiceImpl.confirmOrder(orderConfirmDTO);

    verify(orderRepository, times(1)).findById(any());
    assertNull(confirmedOrder);
    printSuccessMessage();
  }

  @Test
  public void confirmOrderOnAlreadyConfirmedOrderTest() {
    Order order = mock(Order.class);
    OrderConfirmDTO orderConfirmDTO = mock(OrderConfirmDTO.class);

    when(orderRepository.findById(any())).thenReturn(Optional.of(order));
    when(order.getStatus()).thenReturn("confirmed");

    Order confirmedOrder = orderServiceImpl.confirmOrder(orderConfirmDTO);

    verify(orderRepository, times(1)).findById(any());
    verify(order, times(0)).setDeliveryAddr(any());
    verify(order, times(0)).setDeliveryInst(any());
    verify(order, times(0)).setStatus(any());
    verify(orderRepository, times(0)).save(any());
    assertNotNull(confirmedOrder);
    printSuccessMessage();
  }

  @Test
  public void confirmOrderExists() {
    Order order = mock(Order.class);
    OrderConfirmDTO orderConfirmDTO = mock(OrderConfirmDTO.class);

    when(orderRepository.findById(any())).thenReturn(Optional.of(order));
    when(order.getStatus()).thenReturn("review");

    Order confirmedOrder = orderServiceImpl.confirmOrder(orderConfirmDTO);

    verify(orderRepository, times(1)).findById(any());
    verify(order, times(1)).setDeliveryAddr(any());
    verify(order, times(1)).setDeliveryInst(any());
    verify(order, times(1)).setStatus(any());
    verify(orderRepository, times(1)).save(any());
    assertNotNull(confirmedOrder);
  }
}