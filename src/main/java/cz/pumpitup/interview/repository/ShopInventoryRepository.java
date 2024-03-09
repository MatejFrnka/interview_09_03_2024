package cz.pumpitup.interview.repository;

import cz.pumpitup.interview.model.Attribute;
import cz.pumpitup.interview.model.ShopItem;
import cz.pumpitup.interview.model.ShopItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ShopInventoryRepository {
  private final List<ShopItem> shopItems = new ArrayList<>();


  public void addShopItem(ShopItem shopItem) {
    shopItems.add(shopItem);
  }

  public Optional<ShopItem> findByAttributesAndType(ShopItemType shopItemType,
                                                    Map<Attribute, String> attributeConfigurations) {

    // this is the bottleneck when dealing with large inventory
    // of course, in a real application, we would store the data in a database and let the database do the filtering
    // optimization would depend on the specific database
    return shopItems.stream()
        .filter(ShopItem::isAvailable)
        .filter(shopItem -> shopItem.getType().equals(shopItemType))
        .filter(shopItem -> shopItem.attributesMatch(attributeConfigurations)).findFirst();
  }
}
