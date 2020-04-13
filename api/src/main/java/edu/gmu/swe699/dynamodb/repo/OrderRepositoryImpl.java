package edu.gmu.swe699.dynamodb.repo;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import edu.gmu.swe699.dynamodb.model.Order;
import java.util.HashMap;
import java.util.List;
import javax.management.Attribute;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderRepositoryImpl implements OrderCustomRepository {

  @Autowired
  private DynamoDBMapper dynamoDBMapper;

  @Override
  public List<Order> getRestaurantOrders(String restaurantId) {
    HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
    eav.put(":v1", new AttributeValue().withS(restaurantId));
    DynamoDBScanExpression se = new DynamoDBScanExpression()
        .withIndexName("RestaurantIdx")
        .withFilterExpression("RestaurantId = :v1")
        .withExpressionAttributeValues(eav)
        .withProjectionExpression(
            "Id,RestaurantId,BaseAmount,Amount,Tax,OrderMenuItems,DeliveryAddress,DeliveryInstructions");

    return dynamoDBMapper.scan(Order.class, se);
  }
}
