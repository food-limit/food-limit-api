package fr.foodlimit.api.ping;

import fr.foodlimit.api.Application;
import fr.foodlimit.api.ping.PingController;
import fr.foodlimit.api.ping.PingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PingControllerTest {

  @InjectMocks
  PingController pingController;

  @Mock
  PingService pingService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void shouldReturnPong() throws Exception {
    this.mockMvc.perform(get("/ping"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("PONG EN TEST !")));
  }

  @Test
  public void shouldCallPingServiceMethod() {
    pingController.getPing();
    verify(pingService).getPing();
  }
}

