package edu.gmu.swe699;

import edu.gmu.swe699.dto.OrderConfirmDTO;
import edu.gmu.swe699.dynamodb.model.Order;
import edu.gmu.swe699.dynamodb.model.OrderMenuItem;
import edu.gmu.swe699.dynamodb.model.Restaurant;
import edu.gmu.swe699.dynamodb.repo.OrderRepository;
import edu.gmu.swe699.dynamodb.repo.RestaurantRepository;
import edu.gmu.swe699.order.OrderServiceImpl;
import edu.gmu.swe699.tasks.SendConfirmedOrder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private SendConfirmedOrder sendConfirmedOrder;

  @InjectMocks
  @Autowired
  private OrderServiceImpl orderServiceImpl;

  @Test
  public void getOrderDoesNotExistsTest() {
    when(orderRepository.findById(any())).thenReturn(Optional.empty());

    Order order = orderServiceImpl.getOrder(any());

    verify(orderRepository, times(1)).findById(any());
    assertNull(order);
  }

  @Test
  public void getOrderExistsTest() {
    when(orderRepository.findById(any())).thenReturn(Optional.of(new Order()));

    Order order = orderServiceImpl.getOrder(any());

    verify(orderRepository, times(1)).findById(any());
    assertNotNull(order);
  }

  @Test
  public void getRestaurantDoesNotExistsTest() {
    when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

    Restaurant restaurant = orderServiceImpl.getRestaurant("");

    verify(restaurantRepository, times(1)).findById(any());
    assertNull(restaurant);
  }

  @Test
  public void getRestaurantExistsTest() {
    when(restaurantRepository.findById(any())).thenReturn(Optional.of(new Restaurant()));

    Restaurant restaurant = orderServiceImpl.getRestaurant("");

    verify(restaurantRepository, times(1)).findById(any());
    assertNotNull(restaurant);
  }

  @Test
  public void createOrderRestaurantDoesNotExistTest() {
    Order order = mock(Order.class);

    when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

    Order createdOrder = orderServiceImpl.createOrder(order);
    verify(restaurantRepository, times(1)).findById(any());
    assertNull(createdOrder);
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
  }

  @Test
  public void confirmOrderDoesNotExist() {
    OrderConfirmDTO orderConfirmDTO = mock(OrderConfirmDTO.class);

    when(orderRepository.findById(any())).thenReturn(Optional.empty());

    Order confirmedOrder = orderServiceImpl.confirmOrder(orderConfirmDTO);

    verify(orderRepository, times(1)).findById(any());
    assertNull(confirmedOrder);
  }

  @Test
  public void confirmOrderExists() {
    Order order = mock(Order.class);
    OrderConfirmDTO orderConfirmDTO = mock(OrderConfirmDTO.class);

    when(orderRepository.findById(any())).thenReturn(Optional.of(order));

    Order confirmedOrder = orderServiceImpl.confirmOrder(orderConfirmDTO);

    verify(orderRepository, times(1)).findById(any());
    verify(order, times(1)).setDeliveryAddr(any());
    verify(order, times(1)).setDeliveryInst(any());
    verify(order, times(1)).setStatus(any());
    verify(orderRepository, times(1)).save(any());
    assertNotNull(confirmedOrder);
  }
}