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
}
