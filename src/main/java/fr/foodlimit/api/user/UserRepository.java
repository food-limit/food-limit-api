package fr.foodlimit.api.user;

import fr.foodlimit.api.shared.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour la classe User
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
