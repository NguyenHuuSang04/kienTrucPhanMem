package vn.iuh.flashsale.inventory.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins(
            "http://192.168.137.41:3000",
            "http://localhost:3000",
            "http://192.168.137.20:8083")
        .allowedMethods("GET", "POST", "DELETE", "OPTIONS")
        .allowedHeaders("*");
  }
}
