package fr.foodlimit.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app")
@Component
@Data
public class AppConfig {
  @SuppressWarnings("squid:S1068")
  private String secret;
  
  @SuppressWarnings("squid:S1068")
  private long tokenValidityInSeconds;
}
