package fr.foodlimit.api.place;

import fr.foodlimit.api.Application;
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
public class PlaceServiceTest {

  @Autowired
  PlaceService placeService;

  @MockBean
  PlaceRepository placeRepository;

  User user;
  List<Place> places;

  @Before
  public void init() {
    places = new ArrayList<>();
    Place maison = new Place();
    maison.setName("maison");
    places.add(maison);
    Place appart = new Place();
    appart.setName("appart");
    places.add(appart);

    user = new User();
    user.setUsername("test");
    user.setEmail("test@test.fr");
    user.setPassword("$2a$12$I6nKjXN24kFy6Q1sB5EHUeK/wRe7u7LnPesWUHOPKvGah4eGgygvy");
    user.setName("Test Test");
    user.setPlaces(this.places);
  }

  @Test
  public void shouldGetPlaces() {
    when(this.placeRepository.findByUser(Mockito.any())).thenReturn(user.getPlaces());

    assertEquals(this.placeService.getPlaces("test"), user.getPlaces());
  }

  @Test
  public void shouldGetFood() {
    when(this.placeRepository.findById(1L)).thenReturn(Optional.of(this.places.get(0)));

    assertEquals(this.placeService.getPlace(1L), user.getPlaces().get(0));
  }

  @Test
  public void shouldDeleteFood() {
    ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);

    this.placeService.deletePlace(1L);

    Mockito.verify(placeRepository).deleteById(longCaptor.capture());

    assertEquals(new Long(1L), longCaptor.getValue());
  }

  @Test
  public void shouldCreateFood() {
    Place place = new Place();
    place.setId(2L);

    when(this.placeRepository.save(Mockito.any())).thenReturn(place);

    assertEquals(this.placeService.createPlace(place, "test").getId(), place.getId());
  }

  @Test
  public void shouldUpdateFood() {
    Place place = new Place();
    place.setId(3L);

    when(this.placeRepository.save(Mockito.any())).thenReturn(place);

    assertEquals(this.placeService.updatePlace(place, "test").getId(), place.getId());
  }
}
