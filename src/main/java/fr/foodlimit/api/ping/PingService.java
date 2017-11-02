package fr.foodlimit.api.ping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PingService {

  @Autowired
  PingRepository pingRepository;

  public String getPing() {
    return pingRepository.findOne(new Long(1)).getTitle();
  }
}
