package fr.foodlimit.api.ping;

import fr.foodlimit.api.shared.models.Ping;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PingRepository extends CrudRepository<Ping, Long> {
}
