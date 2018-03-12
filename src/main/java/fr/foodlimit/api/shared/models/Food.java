package fr.foodlimit.api.shared.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Classe métier représentant une denrée alimentaire
 */
@Entity
@Data
@SuppressWarnings("squid:S1068")
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dlc;

  private Integer quantity;

  private String picture;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "PLACE_ID")
  private Place place;
}

