package fr.foodlimit.api.food;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.ping.PingController;
import fr.foodlimit.api.ping.PingService;
import fr.foodlimit.api.security.jwt.TokenProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
      foodService.getFoods(Mockito.anyString())).thenReturn(this.foods);

    Mockito.when(
      tokenProvider.getUsername(Mockito.anyString())).thenReturn(Mockito.anyString());

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
      "/foods")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    System.out.println(result.getResponse().getContentAsString());
    String expected = "[{\"id\":null," +
      "\"name\":\"abricot\"," +
      "\"dlc\":null," +
      "\"quantity\":null," +
      "\"picture\":null," +
      "\"user\":null}," +
      "{\"id\":null," +
      "\"name\":null," +
      "\"dlc\":null," +
      "\"quantity\":null," +
      "\"picture\":null," +
      "\"user\":null}]";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }
}

