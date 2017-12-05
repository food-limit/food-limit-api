package fr.foodlimit.api.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import fr.foodlimit.api.Application;
import fr.foodlimit.api.food.FoodService;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.User;
import fr.foodlimit.api.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static fr.foodlimit.api.security.jwt.JWTFilter.AUTHORIZATION_HEADER;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

  @MockBean
  FoodService foodService;

  @MockBean
  UserService userService;

  @MockBean
  TokenProvider tokenProvider;

  @MockBean
  AuthenticationManager authenticationManager;

  @Autowired
  private MockMvc mockMvc;

  private User user;
  private String userJson;

  @Before
  public void init() throws JsonProcessingException {
    this.user = new User();
    this.user.setUsername("admin");
    this.user.setEmail("test@test.fr");
    this.user.setPassword("$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy");
    this.user.setName("Test Test");

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    this.userJson = ow.writeValueAsString(user);
  }

  @Test
  public void shouldAuthenticate() throws Exception {
    this.mockMvc.perform(get("/authenticate"))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void shouldUnauthorize() throws Exception {
    Mockito.when(
      authenticationManager.authenticate(Mockito.any())).thenThrow(new BadCredentialsException("no"));

    this.mockMvc.perform(post("/login")
      .contentType(APPLICATION_JSON_UTF8)
      .content(this.userJson))
      .andDo(print())
      .andExpect(status().isUnauthorized());
  }

  @Test
  public void shouldAuthorizeLogin() throws Exception {
    Mockito.when(
      authenticationManager.authenticate(Mockito.any())).thenReturn(null);

    Mockito.when(
      tokenProvider.createToken(this.user.getUsername())).thenReturn("Token");

    this.mockMvc.perform(post("/login")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .contentType(APPLICATION_JSON_UTF8)
      .content(this.userJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Token")));
  }

  @Test(expected = NestedServletException.class)
  public void shouldThrowLoginException() throws Exception {
    Mockito.when(
      authenticationManager.authenticate(Mockito.any())).thenThrow(AuthenticationException.class);

    this.mockMvc.perform(post("/login")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .contentType(APPLICATION_JSON_UTF8)
      .content(this.userJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Token")));
  }

  @Test
  public void shouldNotSignUpBecauseAlreadyExists() throws Exception {
    Mockito.when(
      userService.usernameExists("admin")).thenReturn(true);

    this.mockMvc.perform(post("/signup")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .contentType(APPLICATION_JSON_UTF8)
      .content(this.userJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("EXISTS")));

  }

  @Test
  public void shouldSignUp() throws Exception {
    Mockito.when(
      userService.usernameExists("admin")).thenReturn(false);

    Mockito.when(
      tokenProvider.createToken(this.user.getUsername())).thenReturn("Token");

    this.mockMvc.perform(post("/signup")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .contentType(APPLICATION_JSON_UTF8)
      .content(this.userJson))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Token")));
  }
}


