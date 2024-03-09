package cz.pumpitup.interview.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import cz.pumpitup.interview.client.ExternalSupplierClient;
import cz.pumpitup.interview.model.Attribute;
import cz.pumpitup.interview.model.Category;
import cz.pumpitup.interview.model.Order;
import cz.pumpitup.interview.model.ShopItem;
import cz.pumpitup.interview.model.ShopItemType;
import cz.pumpitup.interview.repository.ShopInventoryRepository;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceIntegrationTest {

  private ShopInventoryRepository shopInventoryRepository;

  Category tshirtCategory;
  Attribute color;
  Attribute size;

  ShopItem blueNikeShirt1;
  ShopItem blueNikeShirt2;
  ShopItem redNikeShirt1;
  ShopItem blueAdidasShirt1;
  ShopItem blueAdidasShirt2;

  ShopItemType adidasShirt;
  ShopItemType nikeShirt;

  OrderService orderService;
  @Mock
  private ShippingService shippingService;

  @Mock
  private ExternalSupplierClient externalSupplierClient;

  @BeforeEach
  public void setup() {
    shopInventoryRepository = new ShopInventoryRepository();

    tshirtCategory = new Category("T-shirts");

    size = new Attribute("Size");
    color = new Attribute("Color");

    nikeShirt = new ShopItemType("Nike t-shirt", tshirtCategory);
    nikeShirt.addAttribute(size);
    nikeShirt.addAttribute(color);

    adidasShirt = new ShopItemType("Adidas t-shirt", tshirtCategory);
    adidasShirt.addAttribute(size);


    blueNikeShirt1 = new ShopItem(nikeShirt, Map.of(size, "30", color, "Blue"));
    blueNikeShirt2 = new ShopItem(nikeShirt, Map.of(size, "31", color, "Blue"));
    redNikeShirt1 = new ShopItem(nikeShirt, Map.of(color, "Red"));
    blueAdidasShirt1 = new ShopItem(nikeShirt, Map.of(color, "Blue"));
    blueAdidasShirt2 = new ShopItem(nikeShirt, Map.of(color, "Blue"));

    shopInventoryRepository.addShopItem(blueNikeShirt1);
    shopInventoryRepository.addShopItem(blueNikeShirt2);
    shopInventoryRepository.addShopItem(redNikeShirt1);
    shopInventoryRepository.addShopItem(blueAdidasShirt1);
    shopInventoryRepository.addShopItem(blueAdidasShirt2);

    orderService =
        new OrderService(shopInventoryRepository, externalSupplierClient, shippingService);

  }

  @Test
  public void testOrderServiceIntegration_ItemInStock() {
    Order order = new Order(nikeShirt, Map.of(color, "Blue"));
    orderService.handleOrder(order);
    verify(shippingService, times(1)).shipOrder(order, blueNikeShirt1);
  }

  @Test
  public void testOrderServiceIntegration_ItemOutOfStock() {
    Order order = new Order(nikeShirt, Map.of(color, "Red", size, "100"));
    orderService.handleOrder(order);
    verify(externalSupplierClient, times(1)).orderShopItem(order);
  }
}