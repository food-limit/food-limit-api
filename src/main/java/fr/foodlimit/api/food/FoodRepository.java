package fr.foodlimit.api.food;

import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respository concernant les denrées alimentaires
 */
@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {

  /**
   * Récupère une liste d'aliments en fonction de l'utilisateur
   * @param user
   * @return
   */
  List<Food> findByUser(User user);

  /**
   * Récupère une denrée alimentaire par l'id
   * @param id
   * @return
   */
  Food findById(int id);

  /**
   * Supprime une denrée alimentaire par l'id
   * @param id
   */
  void deleteById(int id);
}
