package cz.pumpitup.interview.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ShopItem {
  public ShopItem(ShopItemType type, Map<Attribute, String> attributeValues) {
    this.type = type;
    this.attributeValues = attributeValues;
  }

  private ShopItemType type;
  private Map<Attribute, String> attributeValues;
  @Setter
  private boolean available = true;

  public boolean attributesMatch(Map<Attribute, String> requestedAttributes) {
    for (Map.Entry<Attribute, String> entry : requestedAttributes.entrySet()) {
      if (!attributeValues.containsKey(entry.getKey())) {
        return false;
      }
      if (!attributeValues.get(entry.getKey()).equals(entry.getValue())) {
        return false;
      }
    }
    return true;
  }
}
