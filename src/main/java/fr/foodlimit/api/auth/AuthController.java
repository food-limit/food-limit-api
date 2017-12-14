package fr.foodlimit.api.auth;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.User;
import fr.foodlimit.api.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Ressource AuthController
 *
 * Cette ressource sert à l'authentification sur Food Limit
 */
@RestController
@CrossOrigin
public class AuthController {

  private final UserService userService;

  private final TokenProvider tokenProvider;

  private final PasswordEncoder passwordEncoder;

  private final AuthenticationManager authenticationManager;

  public AuthController(PasswordEncoder passwordEncoder, UserService userService,
                        TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
  }

  /**
   * Check si l'utilisateur est authentifié
   */
  @GetMapping("/authenticate")
  public void authenticate() {
  }

  /**
   * Permet de logger un utilisateur sur l'application
   * @param loginUser
   * @param response
   * @return
   */
  @PostMapping("/login")
  public String authorize(@Valid @RequestBody User loginUser,
                          HttpServletResponse response) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
      loginUser.getUsername(), loginUser.getPassword());

    try {
      this.authenticationManager.authenticate(authenticationToken);
      return this.tokenProvider.createToken(loginUser.getUsername());
    } catch (AuthenticationException e) {
      Application.logger.info("Security exception {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return null;
    }
  }

  /**
   * Permet d'inscrire un utilisateur sur l'application
   * @param signupUser
   * @return
   */
  @PostMapping("/signup")
  public String signup(@RequestBody User signupUser) {
    if (this.userService.usernameExists(signupUser.getUsername())) {
      return "EXISTS";
    }

    signupUser.encodePassword(this.passwordEncoder);
    this.userService.save(signupUser);
    return this.tokenProvider.createToken(signupUser.getUsername());
  }

}
