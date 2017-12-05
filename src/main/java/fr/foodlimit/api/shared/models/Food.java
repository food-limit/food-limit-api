package fr.foodlimit.api.shared.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@SuppressWarnings("squid:S1068")
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDate dlc;

  private Integer quantity;

  private String picture;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id")
  private User user;
}

