package fr.foodlimit.api.food;

import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {

  List<Food> findByUser(User user);
}
