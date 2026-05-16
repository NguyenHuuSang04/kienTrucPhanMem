package vn.iuh.flashsale.order.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryClient {

  private final RestClient inventoryClient;

  public void decrementOrThrow(String productId, int qty) {
    try {
      inventoryClient.post()
          .uri(uriBuilder -> uriBuilder
              .path("/stock/decrement/{id}")
              .queryParam("qty", qty)
              .build(productId))
          .retrieve()
          .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
            if (res.getStatusCode() == HttpStatus.CONFLICT) {
              throw new ResponseStatusException(HttpStatus.CONFLICT, "OUT_OF_STOCK:" + productId);
            }
            throw new ResponseStatusException(res.getStatusCode(),
                "INVENTORY_ERROR:" + productId);
          })
          .toBodilessEntity();
    } catch (ResponseStatusException e) {
      throw e;
    } catch (Exception e) {
      log.error("Inventory call failed: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
          "INVENTORY_UNREACHABLE");
    }
  }
}
