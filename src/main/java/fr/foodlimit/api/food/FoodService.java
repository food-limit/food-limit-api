package fr.foodlimit.api.food;

import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Classe de service concernant les denrées alimentaires
 */
@Service
public class FoodService {

  @Autowired
  FoodRepository foodRepository;

  /**
   * Récupère un aliment de l'utilisateur
   * @return
   */
  public Iterable<Food> getFoods() {
    return foodRepository.findAll();
  }

  /**
   * Récupère un aliment de l'utilisateur
   * @param username
   * @return
   */
  public List<Food> getFoods(String username) {
    User user = new User();
    user.setUsername(username);

    return foodRepository.findByUser(user);
  }

  /**
   * Récupère un aliment de l'utilisateur
   * @param id
   * @return
   */
  public Food getFood(Long id) {
    return foodRepository.findById(id).get();
  }

  /**
   * Supprime un aliment de l'utilisateur
   * @param id
   */
  public void deleteFood(Long id) {
    foodRepository.deleteById(id);
  }

  /**
   * Créé un aliment pour l'utilisateur
   * @param food
   * @param username
   * @return
   */
  public Food createFood(Food food, String username) {
    food.setId(null);
    User user = new User();
    user.setUsername(username);
    food.setUser(user);
    return foodRepository.save(food);
  }

  /**
   * Modifie un aliment de l'utilisateur
   * @param food
   * @param username
   * @return
   */
  public Food updateFood(Food food, String username) {
    food.setId(food.getId());
    User user = new User();
    user.setUsername(username);
    food.setUser(user);
    return foodRepository.save(food);
  }
}
