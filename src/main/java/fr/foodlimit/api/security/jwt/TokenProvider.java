package fr.foodlimit.api.security.jwt;

import fr.foodlimit.api.config.AppConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Composant permettant de créer des JWT
 */
@Component
public class TokenProvider {

  private final String secretKey;

  private final long tokenValidityInMilliseconds;

  private final UserDetailsService userService;

  public TokenProvider(AppConfig config, UserDetailsService userService) {
    this.secretKey = Base64.getEncoder().encodeToString(config.getSecret().getBytes());
    this.tokenValidityInMilliseconds = 1000 * config.getTokenValidityInSeconds();
    this.userService = userService;
  }

  /**
   * Création d'un token
   * @param username
   * @return
   */
  public String createToken(String username) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

    return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
      .setIssuedAt(now).signWith(SignatureAlgorithm.HS512, this.secretKey)
      .setExpiration(validity).compact();
  }

  /**
   * Récupéreration de l'utilisateur concerné par le JWT
   * @param token
   * @return
   */
  public Authentication getAuthentication(String token) {
    String username = this.getUsername(token);
    UserDetails userDetails = this.userService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(userDetails, "",
      userDetails.getAuthorities());
  }

  /**
   * Récupération de l'identifiant en fonction d'un token
   * @param token
   * @return
   */
  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token)
      .getBody().getSubject();
  }

}
