package fr.foodlimit.api.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

/**
 * Classe métier représentant un utilisateur
 */
@Data
@Entity
@Table(name = "user_profile")
@SuppressWarnings("squid:S1068")
public class User {

  @Id
  private String username;

  private String name;

  private String email;

  private String password;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Place> places;


  /**
   * Encode le mot de passe de l'utilisateur
   *
   * @param passwordEncoder
   */
  public void encodePassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
  }

  @Override
  public String toString() {
    return "User{" +
      "username='" + username + '\'' +
      ", name='" + name + '\'' +
      ", email='" + email + '\'' +
      '}';
  }
}
