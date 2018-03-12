package fr.foodlimit.api.shared.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Classe métier représentant un foyer
 */
@Entity
@Data
@SuppressWarnings("squid:S1068")
public class Place {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "USER_ID")
  private User user;

  @JsonIgnore
  @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
  private List<Food> foods;
}

