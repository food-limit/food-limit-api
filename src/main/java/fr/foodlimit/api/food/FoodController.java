package fr.foodlimit.api.food;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

