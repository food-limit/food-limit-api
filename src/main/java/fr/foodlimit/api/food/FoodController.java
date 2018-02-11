package fr.foodlimit.api.food;

import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ressource FoodController
 *
 * Cette ressource gère l'ensemble des actions concernant les denrées alimentaires
 */
@RestController
@CrossOrigin
@RequestMapping("/places/{placeId}/foods")
public class FoodController {

  private final TokenProvider tokenProvider;

  @Autowired
  private FoodService foodService;

  public FoodController(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  /**
   * Récupère la liste des aliments de l'utilisateur
   * @param placeId
   * @return
   */
  @GetMapping
  public List<Food> getFoods(@PathVariable("placeId") Long placeId) {
    return foodService.getFoods(placeId);
  }

  /**
   * Récupère un aliment de l'utilisateur
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public Food getFood(@PathVariable("id") Long id) {
    return foodService.getFood(id);
  }

  /**
   * Supprime un aliment de l'utilisateur
   * @param id
   */
  @DeleteMapping("/{id}")
  public void deleteFood(@PathVariable("id") Long id) {
    foodService.deleteFood(id);
  }

  /**
   * Créé un aliment pour l'utilisateur
   * @param placeId
   * @param food
   * @return
   */
  @PostMapping
  public Food createFood(@PathVariable("placeId") Long placeId, @RequestBody Food food) {
    return foodService.createFood(food, placeId);
  }

  /**
   * Modifie un aliment de l'utilisateur
   * @param placeId
   * @param id
   * @param food
   * @return
   */
  @PutMapping("/{id}")
  public Food updateFood(@PathVariable("placeId") Long placeId, @PathVariable("id") Long id, @RequestBody Food food) {
    food.setId(id);
    return foodService.updateFood(food,placeId);
  }
}

