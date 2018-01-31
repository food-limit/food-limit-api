package fr.foodlimit.api.scheduled;

import fr.foodlimit.api.food.FoodService;
import fr.foodlimit.api.shared.models.Food;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.tomcat.jni.Local;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;

@Component
public class ScheduledTasks {
  @Autowired
  FoodService foodService;

  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  @Scheduled(fixedRate = 5000)
  public void notifyAllUsersWithExpiredFoodsIn3Days() throws IOException {
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
      HttpPost request = new HttpPost("https://onesignal.com/api/v1/notifications");

      // Headers
      request.addHeader("Content-Type", "application/json");
      request.addHeader("Authorization", "Basic ZDMzN2M2NTgtMzZiMy00OGRjLTk4MzUtNmRmOTA0MWFkYmNm");

      // Body
      JSONObject json = new JSONObject();
      json.put("app_id", "e4dc2ee4-9388-4706-b699-fe8c493eb5d0");
      json.put("headings", new JSONObject().put("en", "FOOD-LIMIT"));
      json.put("contents", new JSONObject().put("en", "Votre alliment '"+food.getName()+"' arrive à sa date de péremption !"));
      json.put("filters", new JSONArray().put(
        new JSONObject()
          .put("field", "tag")
          .put("key", "username")
          .put("relation", "=")
          .put("value", food.getUser().getUsername())
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
