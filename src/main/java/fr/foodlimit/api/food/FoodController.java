package fr.foodlimit.api.food;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

  @GetMapping
  public List<Food> getFoods(HttpServletRequest request) {
    return foodService.getFoods(tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }

  @GetMapping("/{id}")
  public Food getFood(@PathVariable("id") Long id) {
    return foodService.getFood(id);
  }

  @DeleteMapping("/{id}")
  public void deleteFood(@PathVariable("id") Long id) {
    foodService.deleteFood(id);
  }

  @PostMapping
  public Food createFood(HttpServletRequest request, @RequestBody Food food) {
    return foodService.createFood(food, tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }

  @PutMapping("/{id}")
  public Food updateFood(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Food food) {
    food.setId(id);
    return foodService.updateFood(food,tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }
}

