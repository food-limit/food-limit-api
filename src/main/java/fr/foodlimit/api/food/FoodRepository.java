package fr.foodlimit.api.food;

import fr.foodlimit.api.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {

  List<Food> findByUser(User user);
}
