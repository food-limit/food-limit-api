package fr.foodlimit.api.food;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Ressource FoodController
 *
 * Cette ressource gère l'ensemble des actions concernant les denrées alimentaires
 */
@RestController
@CrossOrigin
@RequestMapping("/foods")
public class FoodController {

  private final TokenProvider tokenProvider;

  @Autowired
  private FoodService foodService;

  public FoodController(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  /**
   * Récupère la liste des aliments de l'utilisateur
   * @param request
   * @return
   */
  @GetMapping
  public List<Food> getFoods(HttpServletRequest request) {
    return foodService.getFoods(tokenProvider.getUsername(JWTFilter.resolveToken(request)));
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
   * @param request
   * @param food
   * @return
   */
  @PostMapping
  public Food createFood(HttpServletRequest request, @RequestBody Food food) {
    return foodService.createFood(food, tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }

  /**
   * Modifie un aliment de l'utilisateur
   * @param request
   * @param id
   * @param food
   * @return
   */
  @PutMapping("/{id}")
  public Food updateFood(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Food food) {
    food.setId(id);
    return foodService.updateFood(food,tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }
}

