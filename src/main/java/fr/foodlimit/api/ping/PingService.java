package fr.foodlimit.api.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service de ping
 */
@Service
public class PingService {

  @Autowired
  PingRepository pingRepository;

  /**
   * Récupère un pong
   * @return
   */
  public String getPing() {
    return pingRepository.findById(new Long(1)).get().getTitle();
  }
}
