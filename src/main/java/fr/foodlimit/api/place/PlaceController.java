package fr.foodlimit.api.place;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Ressource FoodController
 *
 * Cette ressource gère l'ensemble des actions concernant les foyers
 */
@RestController
@CrossOrigin
@RequestMapping("/places")
public class PlaceController {

  private final TokenProvider tokenProvider;

  @Autowired
  private PlaceService placeService;

  public PlaceController(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  /**
   * Récupère la liste des foyers de l'utilisateur
   * @param request
   * @return
   */
  @GetMapping
  public List<Place> getPlaces(HttpServletRequest request) {
    return placeService.getPlaces(tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }

  /**
   * Récupère un foyer de l'utilisateur
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public Place getPlace(@PathVariable("id") Long id) {
    return placeService.getPlace(id);
  }

  /**
   * Supprime un foyer de l'utilisateur
   * @param id
   */
  @DeleteMapping("/{id}")
  public void deletePlace(@PathVariable("id") Long id) {
    placeService.deletePlace(id);
  }

  /**
   * Créé un foyer pour l'utilisateur
   * @param request
   * @param place
   * @return
   */
  @PostMapping
  public Place createPlace(HttpServletRequest request, @RequestBody Place place) {
    return placeService.createPlace(place, tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }

  /**
   * Modifie un foyer de l'utilisateur
   * @param request
   * @param id
   * @param place
   * @return
   */
  @PutMapping("/{id}")
  public Place updatePlace(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Place place) {
    place.setId(id);
    return placeService.updatePlace(place,tokenProvider.getUsername(JWTFilter.resolveToken(request)));
  }
}

