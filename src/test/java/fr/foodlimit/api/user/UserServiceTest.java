package fr.foodlimit.api.user;

import fr.foodlimit.api.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class UserServiceTest {
  @Autowired
  UserService userService;

  @MockBean
  UserRepository userRepository;

  private User user;

  @Before
  public void init() {
    this.user = new User();
    this.user.setUsername("test");
    this.user.setEmail("test@test.fr");
    this.user.setPassword("$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy");
    this.user.setName("Test Test");
  }

  @Test
  public void shouldLookup() {
    when(this.userRepository.findById("admin")).thenReturn(Optional.of(this.user));
    assertEquals(user, this.userService.lookup("admin"));
  }

  @Test
  public void shouldSaveUser() {
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    this.userService.save(user);

    Mockito.verify(userRepository).save(userCaptor.capture());

    assertEquals(user, userCaptor.getValue());
  }

  @Test
  public void shouldUsernameExists() {
    when(this.userRepository.existsById("admin")).thenReturn(true);
    assertEquals(true, this.userService.usernameExists("admin"));
  }
}
