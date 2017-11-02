package fr.foodlimit.api.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

  @Autowired
  PingService pingService;

  @GetMapping("/ping")
  public String getPing() {
    return pingService.getPing();
  }
}
