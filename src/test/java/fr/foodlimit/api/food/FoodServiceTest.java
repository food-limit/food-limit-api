package fr.foodlimit.api.food;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.ping.Ping;
import fr.foodlimit.api.ping.PingRepository;
import fr.foodlimit.api.ping.PingService;
import fr.foodlimit.api.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class FoodServiceTest {

  @Autowired
  FoodService foodService;

  @MockBean
  FoodRepository foodRepository;

  User user;

  @Before
  public void init() {
    List<Food> foods = new ArrayList<>();
    Food yahourt = new Food();
    yahourt.setName("yahourt");
    foods.add(yahourt);

    user = new User();
    user.setUsername("test");
    user.setEmail("test@test.fr");
    user.setPassword("$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy");
    user.setName("Test Test");
    user.setFoods(foods);
  }

  @Test
  public void shouldCallPingRepositoryMethod() {
    when(this.foodRepository.findByUser(Mockito.any())).thenReturn(user.getFoods());

    assertEquals(this.foodService.getFoods("test"), user.getFoods());
  }
}
