
package fr.foodlimit.api.place;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.security.jwt.TokenProvider;
import fr.foodlimit.api.shared.models.Place;
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
public class PlaceControllerTest {

  @Autowired
  PlaceController placeController;

  @MockBean
  PlaceService placeService;

  @MockBean
  TokenProvider tokenProvider;

  @Autowired
  private MockMvc mockMvc;

  private List<Place> places;

  @Before
  public void init() {
    this.places = new ArrayList<>();
    Place maison = new Place();
    maison.setName("maison");
    this.places.add(maison);
    Place appart = new Place();
    appart.setName("appart");
    this.places.add(appart);
  }

  @Test
  public void shouldGetPlaces() throws Exception {
    Mockito.when(
      placeService.getPlaces(Mockito.any())).thenReturn(this.places);

    Mockito.when(
      tokenProvider.getUsername(Mockito.anyString())).thenReturn(Mockito.anyString());

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
      "/places")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "[{\"id\":null,\"name\":\"maison\"},{\"id\":null,\"name\":\"appart\"}]";

    JSONAssert.assertEquals(
      expected,
      result.getResponse().getContentAsString(),
      false);
  }

 @Test
  public void shouldGetPlace() throws Exception {
    Mockito.when(
      placeService.getPlace(1L)).thenReturn(places.get(0));

   Mockito.when(
     placeService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
      "/places/1")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"id\":null,\"name\":\"maison\"}";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }

  @Test
  public void shouldCreatePlace() throws Exception {
    Place place = places.get(0);
    place.setId(2L);

    Mockito.when(placeService.createPlace(any(), any())).thenReturn(place);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
      "/places")
      .contentType(APPLICATION_JSON_UTF8)
      .content("{\"name\":\"maison\"}")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"id\" :2,\"name\":\"maison\"}";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }

  @Test
  public void shouldUpdatePlace() throws Exception {
    Place place = places.get(0);
    place.setId(1L);
    place.setName("maison update");

    Mockito.when(
      placeService.updatePlace(any(), any())).thenReturn(place);

    Mockito.when(
      placeService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
      "/places/1")
      .contentType(APPLICATION_JSON_UTF8)
      .content("{\"id\" :1,\"name\":\""+ place.getName() +"\"}")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    String expected = "{\"id\" :1,\"name\":\"maison update\"}";

    JSONAssert.assertEquals(expected, result.getResponse()
      .getContentAsString(), false);
  }

  @Test
  public void shouldDeletePlace() throws Exception {
    ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);

    Mockito.when(
      placeService.checkPlace(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(true);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
      "/places/1")
      .header(AUTHORIZATION_HEADER, "Bearer test")
      .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder).andReturn();

    Mockito.verify(placeService).deletePlace(longCaptor.capture());

    assertEquals(new Long(1L), longCaptor.getValue());
  }
}
