package fr.foodlimit.api.food;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de service concernant les denrées alimentaires
 */
@Service
public class FoodService {

  @Autowired
  FoodRepository foodRepository;

  /**
   * Récupère tous aliments
   * @return
   */
  public Iterable<Food> getFoods() {
    return foodRepository.findAll();
  }

  /**
   * Récupère les aliments de l'utilisateur
   * @param placeId
   * @return
   */
  public List<Food> getFoodsByPlace(Long placeId) {
    Place place = new Place();
    place.setId(placeId);

    return foodRepository.findByPlace(place);
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
   * @param placeId
   * @return
   */
  public Food createFood(Food food, Long placeId) {
    food.setId(null);
    Place place = new Place();
    place.setId(placeId);
    food.setPlace(place);
    return foodRepository.save(food);
  }

  /**
   * Modifie un aliment de l'utilisateur
   * @param food
   * @param placeId
   * @return
   */
  public Food updateFood(Food food, Long placeId) {
    food.setId(food.getId());
    Place place = new Place();
    place.setId(placeId);
    food.setPlace(place);
    return foodRepository.save(food);
  }

  public boolean checkPlace(TokenProvider tokenProvider, HttpServletRequest request, Place place) {
    String username = tokenProvider.getUsername(JWTFilter.resolveToken(request));

    return place.getUser().getUsername().equals(username);
  }

  public boolean checkFood(Long id, Place place) {
    List<Food> foodsFiltered = place.getFoods()
      .stream()
      .filter(food -> food.getId().equals(id))
      .collect(Collectors.toList());

    return !foodsFiltered.isEmpty();
  }
}
