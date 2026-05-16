package vn.iuh.flashsale.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient inventoryClient(@Value("${flashsale.inventory-url}") String baseUrl) {
    return RestClient.builder()
        .baseUrl(baseUrl)
        .requestFactory(timeoutRequestFactory())
        .build();
  }

  private static org.springframework.http.client.SimpleClientHttpRequestFactory timeoutRequestFactory() {
    var f = new org.springframework.http.client.SimpleClientHttpRequestFactory();
    f.setConnectTimeout((int) Duration.ofSeconds(2).toMillis());
    f.setReadTimeout((int) Duration.ofSeconds(3).toMillis());
    return f;
  }
}
