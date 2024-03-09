package cz.pumpitup.interview.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ShopItemType {
  public ShopItemType(String name, Category category) {
    this.name = name;
    this.category = category;
  }

  public void addAttribute(Attribute attribute) {
    attributes.add(attribute);
  }

  private String name;
  private Category category;
  private Set<Attribute> attributes = new HashSet<>();
}
