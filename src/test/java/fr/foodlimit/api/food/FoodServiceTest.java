package fr.foodlimit.api.food;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.security.jwt.JWTFilter;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import fr.foodlimit.api.shared.models.Place;
import fr.foodlimit.api.shared.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class FoodServiceTest {

  @Autowired
  FoodService foodService;

  @MockBean
  FoodRepository foodRepository;

  @MockBean
  TokenProvider tokenProvider;

  User user;
  List<Place> places;

  @Before
  public void init() {
    List<Food> foods = new ArrayList<>();
    Food yahourt = new Food();
    yahourt.setName("yahourt");
    foods.add(yahourt);

    Place maison = new Place();
    maison.setName("maison");
    maison.setFoods(foods);
    this.places = new ArrayList<>();
    places.add(maison);

    user = new User();
    user.setUsername("test");
    user.setEmail("test@test.fr");
    user.setPassword("$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy");
    user.setName("Test Test");
    user.setPlaces(this.places);
  }

  @Test
  public void shouldGetFoodsByPlace() {
    when(this.foodRepository.findByPlace(Mockito.any())).thenReturn(user.getPlaces().get(0).getFoods());

    assertEquals(this.foodService.getFoodsByPlace(1L), user.getPlaces().get(0).getFoods());
  }

  @Test
  public void shouldGetAllFoods() {
    Food food = new Food();
    food.setId(1L);
    List<Food> foods = new ArrayList<>();
    foods.add(food);

    when(this.foodRepository.findAll()).thenReturn(foods);
    assertEquals(this.foodService.getFoods(), foods);
  }

  @Test
  public void shouldGetFood() {
    when(this.foodRepository.findById(1L)).thenReturn(Optional.of(this.places.get(0).getFoods().get(0)));

    assertEquals(this.foodService.getFood(1L), user.getPlaces().get(0).getFoods().get(0));
  }

  @Test
  public void shouldDeleteFood() {
    ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);

    this.foodService.deleteFood(1L);

    Mockito.verify(foodRepository).deleteById(longCaptor.capture());

    assertEquals(new Long(1L), longCaptor.getValue());
  }

  @Test
  public void shouldCreateFood() {
    Food food = new Food();
    food.setId(2L);

    when(this.foodRepository.save(Mockito.any())).thenReturn(food);

    assertEquals(this.foodService.createFood(food, 1L).getId(), null);
    assertEquals(this.foodService.createFood(food, 1L).getName(), food.getName());
  }

  @Test
  public void shouldUpdateFood() {
    Food food = new Food();
    food.setId(3L);

    when(this.foodRepository.save(Mockito.any())).thenReturn(food);

    assertEquals(this.foodService.updateFood(food, 1L).getName(), food.getName());
  }

  @Test
  public void shouldCheckPlace() {
    Mockito.when(
      tokenProvider.getUsername(Mockito.anyString())).thenReturn("admin");

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer 1234");
    Place place = new Place();
    User user = new User();
    user.setUsername("admin");
    place.setUser(user);

    assertTrue(this.foodService.checkPlace(tokenProvider, request, place));
  }

  @Test
  public void shouldNotCheckPlace() {
    Mockito.when(
      tokenProvider.getUsername(Mockito.anyString())).thenReturn("toto");

    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(JWTFilter.AUTHORIZATION_HEADER, "Bearer 1234");
    Place place = new Place();
    User user = new User();
    user.setUsername("admin");
    place.setUser(user);

    assertFalse(this.foodService.checkPlace(tokenProvider, request, place));
  }

  @Test
  public void shouldCheckFood() {
    Food food1 = new Food();
    food1.setId(1L);
    Food food2 = new Food();
    food2.setId(2L);
    Place place = new Place();
    place.setFoods(Arrays.asList(food1, food2));

    assertTrue(this.foodService.checkFood(1L, place));
  }

  @Test
  public void shouldNotCheckFood() {
    Food food1 = new Food();
    food1.setId(1L);
    Food food2 = new Food();
    food2.setId(2L);
    Place place = new Place();
    place.setFoods(Arrays.asList(food1, food2));

    assertFalse(this.foodService.checkFood(3L, place));
  }
}
