package fr.foodlimit.api;

import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.user.User;
import fr.foodlimit.api.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    User user = new User();
    user.setUsername("admin");
    user.setPassword(this.passwordEncoder.encode("admin"));
    this.userService.save(user);
  }

  @GetMapping("/authenticate")
  public void authenticate() {
    // we don't have to do anything here
    // this is just a secure endpoint and the JWTFilter
    // validates the token
    // this service is called at startup of the app to check
    // if the fr.foodlimit.foodlimitapi token is still valid
  }

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
