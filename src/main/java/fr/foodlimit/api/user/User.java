package fr.foodlimit.api.user;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@SuppressWarnings("squid:S1068")
public class User {

  private String name;

  private String username;

  private String email;

  private String password;

  public void encodePassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
  }

}
