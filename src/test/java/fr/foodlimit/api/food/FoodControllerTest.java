
package fr.foodlimit.api.food;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Food;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static fr.foodlimit.api.security.jwt.JWTFilter.AUTHORIZATION_HEADER;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FoodControllerTest {

  @MockBean
  FoodService foodService;

  @MockBean
  TokenProvider tokenProvider;

  @Autowired
  private MockMvc mockMvc;

  private List<Food> foods;

  @Before
  public void init() {
    this.foods = new ArrayList<>();
    Food banane = new Food();
    banane.setName("banane");
    this.foods.add(banane);
    Food abricot = new Food();
    banane.setName("abricot");
    this.foods.add(abricot);
  }

  @Test
  public void shouldGetFoods() throws Exception {
    Mockito.when(
      foodService.getFoodsByPlace(Mockito.anyLong())).thenReturn(this.foods);

    Mockito.when(
      foodService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
      "/places/1/foods")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "[{\"id\":null," +
      "\"name\":\"abricot\"," +
      "\"dlc\":null," +
      "\"quantity\":null," +
      "\"picture\":null}," +
      "{\"id\":null," +
      "\"name\":null," +
      "\"dlc\":null," +
      "\"quantity\":null," +
      "\"picture\":null}]";

    JSONAssert.assertEquals(
      expected,
      result.getResponse().getContentAsString(),
      false);
  }

  @Test
  public void shouldGetFood() throws Exception {
    Mockito.when(
      foodService.getFood(1L)).thenReturn(foods.get(0));

    Mockito.when(
      foodService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    Mockito.when(
      foodService.checkFood(Mockito.anyLong(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
      "/places/1/foods/1")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"id\" :null,\"name\":\"abricot\",\"dlc\":null,\"quantity\":null,\"picture\":null}";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }

  @Test
  public void shouldCreateFood() throws Exception {
    Food food = foods.get(0);
    food.setId(2L);

    Mockito.when(
      foodService.createFood(any(), any())).thenReturn(food);

    Mockito.when(
      foodService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
      "/places/1/foods")
      .contentType(APPLICATION_JSON_UTF8)
      .content("{\"name\":\"abricot\",\"dlc\":null,\"quantity\":null,\"picture\":null}")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"id\" :2,\"name\":\"abricot\",\"dlc\":null,\"quantity\":null,\"picture\":null}";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }

  @Test
  public void shouldUpdateFood() throws Exception {
    Food food = foods.get(0);
    food.setId(1L);

    Mockito.when(
      foodService.updateFood(any(), any())).thenReturn(food);

    Mockito.when(
      foodService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    Mockito.when(
      foodService.checkFood(Mockito.anyLong(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
      "/places/1/foods/1")
      .contentType(APPLICATION_JSON_UTF8)
      .content("{\"id\" :1,\"name\":\"abricot\",\"dlc\":null,\"quantity\":null,\"picture\":null}")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"id\" :1,\"name\":\"abricot\",\"dlc\":null,\"quantity\":null,\"picture\":null}";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }

  @Test
  public void shouldDeleteFood() throws Exception {
    ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);

    Mockito.when(
      foodService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
      "/places/1/foods/1")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder).andReturn();

    Mockito.verify(foodService).deleteFood(longCaptor.capture());

    assertEquals(new Long(1L), longCaptor.getValue());
  }
}
