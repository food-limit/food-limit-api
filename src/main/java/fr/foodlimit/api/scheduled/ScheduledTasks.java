package fr.foodlimit.api.scheduled;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Scheduled(fixedRate = 5000)
  public void notifyAllUsersWithExpiredFoodsIn3Days() throws IOException {
    log.info("The time is now {}", dateFormat.format(new Date()));

    CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    try {
      // Build request
      HttpPost request = new HttpPost("https://onesignal.com/api/v1/notifications");

      // Headers
      request.addHeader("Content-Type", "application/json");
      request.addHeader("Authorization", "Basic ZDMzN2M2NTgtMzZiMy00OGRjLTk4MzUtNmRmOTA0MWFkYmNm");

      // Body
      JSONObject json = new JSONObject();
      json.put("app_id", "e4dc2ee4-9388-4706-b699-fe8c493eb5d0");
      json.put("headings", new JSONObject().put("en", "PUSH-NOTIF"));
      json.put("contents", new JSONObject().put("en", "Hello phone 2 beau goss !!!"));
      json.put("filters", new JSONArray().put(
        new JSONObject()
          .put("field", "tag")
          .put("key", "userID")
          .put("relation", "=")
          .put("value", "phone_2")
      ));
      StringEntity body = new StringEntity(json.toString());
      request.setEntity(body);

      // Execute
      httpClient.execute(request);
      log.info(request.toString());
    } catch (Exception ex) {
      log.error(ex.getMessage());
    } finally {
        httpClient.close();
    }
  }
}
