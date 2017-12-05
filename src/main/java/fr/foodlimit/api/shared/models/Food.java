package fr.foodlimit.api.shared.models;


import com.fasterxml.jackson.annotation.JsonFormat;
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

  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dlc;

  private Integer quantity;

  private String picture;

  @ManyToOne
  @JoinColumn(name="USER_ID")
  private User user;
}

