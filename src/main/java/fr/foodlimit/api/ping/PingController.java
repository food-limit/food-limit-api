package fr.foodlimit.api.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ressource PingController
 *
 * Cette ressource sert à vérifier que l'application tourne du contrôleur jusqu'à la base de données
 */
@RestController
public class PingController {

  @Autowired
  PingService pingService;

  /**
   * Récupère un pong
   * @return
   */
  @GetMapping("/ping")
  public String getPing() {
    return pingService.getPing();
  }
}
