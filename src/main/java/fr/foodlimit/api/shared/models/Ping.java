package fr.foodlimit.api.shared.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe métier représentant un ping
 */
@Data
@Entity
public class Ping {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @SuppressWarnings("squid:S00112")
  private String title;
}
