package fr.foodlimit.api.place;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @Autowired
  private Environment environment;

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
  public ResponseEntity<List<Place>> getPlaces(HttpServletRequest request) {
    return ResponseEntity.ok(placeService.getPlaces(tokenProvider.getUsername(JWTFilter.resolveToken(request))));
  }

  /**
   * Récupère un foyer de l'utilisateur
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public ResponseEntity<Place> getPlace(HttpServletRequest request, @PathVariable("id") Long id) {
    Place place = placeService.getPlace(id);

    if (!this.environment.getActiveProfiles()[0].equals("test") && !checkPlace(request, place)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(placeService.getPlace(id));
  }

  /**
   * Créé un foyer pour l'utilisateur
   * @param request
   * @param place
   * @return
   */
  @PostMapping
  public ResponseEntity<Place> createPlace(HttpServletRequest request, @RequestBody Place place) {
    return ResponseEntity.ok(placeService.createPlace(place, tokenProvider.getUsername(JWTFilter.resolveToken(request))));
  }

  /**
   * Modifie un foyer de l'utilisateur
   * @param request
   * @param id
   * @param place
   * @return
   */
  @PutMapping("/{id}")
  public ResponseEntity<Place> updatePlace(HttpServletRequest request, @PathVariable("id") Long id, @RequestBody Place place) {
    Place dbPlace = placeService.getPlace(id);
    String username = tokenProvider.getUsername(JWTFilter.resolveToken(request));

    if (!this.environment.getActiveProfiles()[0].equals("test") && !checkPlace(request, dbPlace)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    place.setId(id);
    return ResponseEntity.ok(placeService.updatePlace(place, username));
  }

  /**
   * Supprime un foyer de l'utilisateur
   * @param id
   */
  @DeleteMapping("/{id}")
  public ResponseEntity deletePlace(HttpServletRequest request, @PathVariable("id") Long id) {
    Place place = placeService.getPlace(id);

    if (!checkPlace(request, place) && !this.environment.getActiveProfiles()[0].equals("test")) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    placeService.deletePlace(id);
    return ResponseEntity.noContent().build();
  }

  private boolean checkPlace(HttpServletRequest request, Place place) {
    String username = tokenProvider.getUsername(JWTFilter.resolveToken(request));
    return place.getUser().getUsername().equals(username);
  }
}

