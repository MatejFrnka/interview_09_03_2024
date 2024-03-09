package cz.pumpitup.interview.service;

import cz.pumpitup.interview.client.ExternalSupplierClient;
import cz.pumpitup.interview.model.Order;
import cz.pumpitup.interview.model.ShopItem;
import cz.pumpitup.interview.repository.ShopInventoryRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderService {
  private ShopInventoryRepository shopInventoryRepository;
  private ExternalSupplierClient externalSupplierClient;
  private ShippingService shippingService;

  public void handleOrder(Order order) {
    Optional<ShopItem> shopItemOptional = shopInventoryRepository.findByAttributesAndType(
        order.getShopItemType(),
        order.getAttributeValues()
    );

    if (shopItemOptional.isEmpty()) {
      externalSupplierClient.orderShopItem(order);
    } else {
      // set available to false so that no one else orders it
      shopItemOptional.get().setAvailable(false);
      shippingService.shipOrder(order, shopItemOptional.get());
    }
  }
}
