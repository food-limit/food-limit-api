package fr.foodlimit.api.shared.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@SuppressWarnings("squid:S1068")
public class User {

  @Id
  private String username;

  private String name;

  private String email;

  private String password;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Food> foods;

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
