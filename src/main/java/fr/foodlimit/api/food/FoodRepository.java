package fr.foodlimit.api.food;

import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respository concernant les denrées alimentaires
 */
@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {

  /**
   * Récupère une liste d'aliments en fonction d'un foyer
   * @param place
   * @return
   */
  List<Food> findByPlace(Place place);

  /**
   * Récupère une denrée alimentaire par l'id
   * @param id
   * @return
   */
  Food findById(int id);
}
