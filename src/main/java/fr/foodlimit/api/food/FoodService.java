package fr.foodlimit.api.food;

import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

  @Autowired
  FoodRepository foodRepository;

  public List<Food> getFoods(String username) {
    User user = new User();
    user.setUsername(username);

    return foodRepository.findByUser(user);
  }

  public Food getFood(Long id) {
    return foodRepository.findById(id).get();
  }

  public void deleteFood(Long id) {
    foodRepository.deleteById(id);
  }

  public Food createFood(Food food, String username) {
    food.setId(null);
    User user = new User();
    user.setUsername(username);
    food.setUser(user);
    return foodRepository.save(food);
  }

  public Food updateFood(Food food, String username) {
    food.setId(food.getId());
    User user = new User();
    user.setUsername(username);
    food.setUser(user);
    return foodRepository.save(food);
  }
}
