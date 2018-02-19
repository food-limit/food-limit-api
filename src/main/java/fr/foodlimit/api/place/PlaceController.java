package fr.foodlimit.api.place;

import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Place;
import org.springframework.beans.factory.annotation.Autowired;
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
    String username = tokenProvider.getUsername(JWTFilter.resolveToken(request));

    if (!place.getUser().getUsername().equals(username)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    return ResponseEntity.ok(placeService.getPlace(id));
  }

  /**
   * Supprime un foyer de l'utilisateur
   * @param id
   */
  @DeleteMapping("/{id}")
  public ResponseEntity deletePlace(HttpServletRequest request, @PathVariable("id") Long id) {
    Place place = placeService.getPlace(id);
    String username = tokenProvider.getUsername(JWTFilter.resolveToken(request));

    if (!place.getUser().getUsername().equals(username)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    placeService.deletePlace(id);
    return ResponseEntity.noContent().build();
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

    if (!dbPlace.getUser().getUsername().equals(username)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    place.setId(id);
    return ResponseEntity.ok(placeService.updatePlace(place,tokenProvider.getUsername(JWTFilter.resolveToken(request))));
  }
}

