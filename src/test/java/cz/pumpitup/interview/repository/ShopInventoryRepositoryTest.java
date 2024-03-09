package cz.pumpitup.interview.repository;

import cz.pumpitup.interview.model.Attribute;
import cz.pumpitup.interview.model.Category;
import cz.pumpitup.interview.model.ShopItem;
import cz.pumpitup.interview.model.ShopItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ShopInventoryRepositoryTest {
  private ShopInventoryRepository repository;
  private ShopItem shopItem;
  private Map<Attribute, String> attributeConfigurations;
  private ShopItemType shopItemType;

  @BeforeEach
  void setUp() {
    repository = new ShopInventoryRepository();
    attributeConfigurations = new HashMap<>();
    attributeConfigurations.put(new Attribute("Author"), "John Doe");
    shopItemType = new ShopItemType("Book", new Category("Literature"));
    shopItem = new ShopItem(shopItemType, attributeConfigurations);
    repository.addShopItem(shopItem);
  }

  @Test
  void testFindByAttributesAndType_MatchFound() {
    Optional<ShopItem> result =
        repository.findByAttributesAndType(shopItemType, attributeConfigurations);
    assertTrue(result.isPresent());
    assertEquals(shopItem, result.get());
  }

  @Test
  void testFindByAttributesAndType_MatchNotFound() {
    Optional<ShopItem> result =
        repository.findByAttributesAndType(shopItemType,
            Map.of(new Attribute("Author"), "Jane Doe"));
    assertFalse(result.isPresent());
  }

  @Test
  void testFindByAttributesAndType_ItemUnavailable() {
    shopItem.setAvailable(false);
    Optional<ShopItem> result =
        repository.findByAttributesAndType(shopItemType, attributeConfigurations);
    assertFalse(result.isPresent());
  }
}