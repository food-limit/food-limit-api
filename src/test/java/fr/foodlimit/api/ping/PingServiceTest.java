package fr.foodlimit.api.ping;

import fr.foodlimit.api.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class PingServiceTest {

  @Autowired
  PingService pingService;

  @MockBean
  PingRepository pingRepository;

  @Test
  public void shouldCallPingRepositoryMethod() {
    Ping ping = new Ping();
    ping.setId(1L);
    ping.setTitle("pong");

    when(this.pingRepository.findById(1L)).thenReturn(Optional.of(ping));

    assertEquals(ping.getTitle(), this.pingService.getPing());

  }
}
