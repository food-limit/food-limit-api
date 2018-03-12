package fr.foodlimit.api.place;

import fr.foodlimit.api.shared.models.Place;
import fr.foodlimit.api.shared.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respository concernant les foyers alimentaires
 */
@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {

  /**
   * Récupère une liste des foyers en fonction de l'utilisateur
   *
   * @param user
   * @return
   */
  List<Place> findByUser(User user);

  /**
   * Récupère un foyer par l'id
   *
   * @param id
   * @return
   */
  Place findById(int id);
}
