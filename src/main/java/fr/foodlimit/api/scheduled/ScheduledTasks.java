package fr.foodlimit.api.scheduled;

import fr.foodlimit.api.food.FoodService;
import fr.foodlimit.api.shared.models.Food;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;

@Component
public class ScheduledTasks {
  @Autowired
  FoodService foodService;

  @Value("${onesignal.url}")
  private String url;
  @Value("${onesignal.api-key}")
  private String apiKey;
  @Value("${onesignal.app-id}")
  private String appId;

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  @Scheduled(cron="0 0 19 * * *")
  public void notifyAllUsersWithExpiredFoodsIn3Days() throws IOException {
    System.out.println(url);
    System.out.println(apiKey);
    System.out.println(appId);

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    for (Iterator<Food> i = foodService.getFoods().iterator(); i.hasNext();) {
      Food food = i.next();
      if(LocalDate.now().plusDays(3).isAfter(food.getDlc())){
        this.sendNotif(httpClient, food);
      }
    }
    httpClient.close();
  }

  private void sendNotif(HttpClient httpClient, Food food) throws IOException {
    try {
      // Build request
      HttpPost request = new HttpPost(url);

      // Headers
      request.addHeader("Content-Type", "application/json");
      request.addHeader("Authorization", "Basic " + apiKey);

      // Body
      JSONObject json = new JSONObject();
      json.put("app_id", appId);
      json.put("headings", new JSONObject().put("en", "FOOD-LIMIT"));
      json.put("contents", new JSONObject().put("en", "Votre aliment '"+food.getName()+"' arrive à sa date de péremption !"));
      json.put("filters", new JSONArray().put(
        new JSONObject()
          .put("field", "tag")
          .put("key", "username")
          .put("relation", "=")
          .put("value", food.getPlace().getUser().getUsername())
      ));
      StringEntity body = new StringEntity(json.toString());
      request.setEntity(body);

      // Execute
      httpClient.execute(request);
      log.info(request.toString());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }
}
