package fr.foodlimit.api.food;

import fr.foodlimit.api.place.PlaceService;
import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ressource FoodController
 *
 * Cette ressource gère l'ensemble des actions concernant les denrées alimentaires
 */
@RestController
@CrossOrigin
@RequestMapping("/places/{placeId}/foods")
public class FoodController {

  @Autowired
  private Environment environment;

  @Autowired
  private FoodService foodService;

  @Autowired
  private PlaceService placeService;

  private final TokenProvider tokenProvider;

  public FoodController(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  /**
   * Récupère la liste des aliments de l'utilisateur
   * @param placeId
   * @return
   */
  @GetMapping
  public ResponseEntity<List<Food>> getFoods(HttpServletRequest request, @PathVariable("placeId") Long placeId) {
    Place place = placeService.getPlace(placeId);

    if (!this.environment.getActiveProfiles()[0].equals("test")) {
      if (!checkPlace(request, place)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(foodService.getFoods(placeId));
  }

  /**
   * Récupère un aliment de l'utilisateur
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public ResponseEntity<Food> getFood(HttpServletRequest request, @PathVariable("placeId") Long placeId, @PathVariable("id") Long id) {
    Place place = placeService.getPlace(placeId);

    if (!this.environment.getActiveProfiles()[0].equals("test")) {
      if (!checkPlace(request, place)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      if (!checkFood(id, place)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return ResponseEntity.ok(foodService.getFood(id));
  }

  /**
   * Créé un aliment pour l'utilisateur
   * @param placeId
   * @param food
   * @return
   */
  @PostMapping
  public ResponseEntity<Food> createFood(HttpServletRequest request, @PathVariable("placeId") Long placeId, @RequestBody Food food) {
    Place place = placeService.getPlace(placeId);

    if (!this.environment.getActiveProfiles()[0].equals("test")) {
      if (!checkPlace(request, place)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(foodService.createFood(food, placeId));
  }

  /**
   * Modifie un aliment de l'utilisateur
   * @param placeId
   * @param id
   * @param food
   * @return
   */
  @PutMapping("/{id}")
  public ResponseEntity<Food> updateFood(HttpServletRequest request,
                                         @PathVariable("placeId") Long placeId,
                                         @PathVariable("id") Long id,
                                         @RequestBody Food food) {
    Place dbPlace = placeService.getPlace(placeId);

    if (!this.environment.getActiveProfiles()[0].equals("test")) {
      if (!checkPlace(request, dbPlace)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
      if (!checkFood(id, dbPlace)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    food.setId(id);
    return ResponseEntity.ok(foodService.updateFood(food,placeId));
  }

  /**
   * Supprime un aliment de l'utilisateur
   * @param id
   */
  @DeleteMapping("/{id}")
  public ResponseEntity deleteFood(HttpServletRequest request, @PathVariable("placeId") Long placeId, @PathVariable Long id) {
    Place place = placeService.getPlace(placeId);

    if (!this.environment.getActiveProfiles()[0].equals("test")) {
      if (!checkPlace(request, place)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    foodService.deleteFood(id);

    return ResponseEntity.noContent().build();
  }

  private boolean checkPlace(HttpServletRequest request, Place place) {
    String username = tokenProvider.getUsername(JWTFilter.resolveToken(request));

    return place.getUser().getUsername().equals(username);
  }

  private boolean checkFood(Long id, Place place) {
    List<Food> foodsFiltered = place.getFoods()
      .stream()
      .filter(food -> food.getId().equals(id))
      .collect(Collectors.toList());

    return foodsFiltered.size() != 0;
  }
}

