package edu.gmu.swe699.dynamodb.repo;

import edu.gmu.swe699.dynamodb.model.Restaurant;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {
}
