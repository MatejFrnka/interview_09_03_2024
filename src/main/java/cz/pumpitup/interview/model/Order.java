package cz.pumpitup.interview.model;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Order {
  private ShopItemType shopItemType;
  private Map<Attribute, String> attributeValues;
}
