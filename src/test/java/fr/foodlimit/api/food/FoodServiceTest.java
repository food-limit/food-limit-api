package fr.foodlimit.api.food;

import fr.foodlimit.api.Application;
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
  public void shouldGetFoods() {
    when(this.foodRepository.findByPlace(Mockito.any())).thenReturn(user.getPlaces().get(0).getFoods());

    assertEquals(this.foodService.getFoods(1L), user.getPlaces().get(0).getFoods());
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
}
