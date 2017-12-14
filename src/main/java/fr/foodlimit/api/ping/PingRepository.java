package fr.foodlimit.api.ping;

import fr.foodlimit.api.shared.models.Ping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository de ping permettant de récupérer le pong en base
 */
@Repository
public interface PingRepository extends CrudRepository<Ping, Long> {
}
