package vn.iuh.flashsale.order.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, String>> handle(ResponseStatusException e) {
    String reason = e.getReason() == null ? "ERROR" : e.getReason();
    String code = reason.contains(":") ? reason.substring(0, reason.indexOf(':')) : reason;
    return ResponseEntity.status(e.getStatusCode())
        .body(Map.of("error", reason, "code", code));
  }
}
