package cz.pumpitup.interview.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopItemTest {

  ShopItemType type1;
  Attribute color = new Attribute("Color");
  Attribute size = new Attribute("Size");
  Attribute material = new Attribute("Material");

  @BeforeEach
  public void setup() {
    type1 = new ShopItemType("Name", new Category("Category"));
    type1.addAttribute(color);
    type1.addAttribute(size);
    type1.addAttribute(material);
  }

  @Test
  public void testAttributesMatch_AllAttributesMatch() {
    Map<Attribute, String> itemAttributes = new HashMap<>();
    itemAttributes.put(color, "Red");
    itemAttributes.put(size, "Medium");

    ShopItem item = new ShopItem(type1, itemAttributes);

    Map<Attribute, String> requestedAttributes =
        new HashMap<>(itemAttributes); // Copying itemAttributes to simulate a match

    assertTrue(item.attributesMatch(requestedAttributes), "Attributes should match.");
  }

  @Test
  public void testAttributesMatch_AttributesDoNotMatch() {
    Map<Attribute, String> itemAttributes = new HashMap<>();
    itemAttributes.put(color, "Blue");
    itemAttributes.put(size, "Large");

    ShopItem item = new ShopItem(type1, itemAttributes);

    Map<Attribute, String> requestedAttributes = new HashMap<>();
    requestedAttributes.put(color, "Red"); // Different color
    requestedAttributes.put(size, "Large");

    assertFalse(item.attributesMatch(requestedAttributes),
        "Attributes should not match due to color difference.");
  }

  @Test
  public void testAttributesMatch_ExtraAttributesInItem() {
    Map<Attribute, String> itemAttributes = new HashMap<>();
    itemAttributes.put(color, "Green");
    itemAttributes.put(size, "Small");
    itemAttributes.put(material, "Cotton"); // Extra attribute not requested

    ShopItem item = new ShopItem(type1, itemAttributes);

    Map<Attribute, String> requestedAttributes = new HashMap<>();
    requestedAttributes.put(color, "Green");
    requestedAttributes.put(size, "Small");

    assertTrue(item.attributesMatch(requestedAttributes),
        "Attributes should match despite extra attributes in the item.");
  }

  @Test
  public void testAttributesMatch_MissingAttributesInItem() {
    Map<Attribute, String> itemAttributes = new HashMap<>();
    itemAttributes.put(color, "Black"); // Missing size attribute

    ShopItem item = new ShopItem(type1, itemAttributes);

    Map<Attribute, String> requestedAttributes = new HashMap<>();
    requestedAttributes.put(color, "Black");
    requestedAttributes.put(size, "Extra Large"); // Requesting missing attribute

    assertFalse(item.attributesMatch(requestedAttributes),
        "Attributes should not match due to missing attribute in the item.");
  }


  @Test
  public void testAttributesMatch_NoAttributesRequested() {
    Map<Attribute, String> itemAttributes = new HashMap<>();
    itemAttributes.put(color, "White");
    itemAttributes.put(size, "Medium");

    ShopItem item = new ShopItem(type1, itemAttributes);

    Map<Attribute, String> requestedAttributes = new HashMap<>(); // No attributes requested

    assertTrue(item.attributesMatch(requestedAttributes),
        "Should match when no attributes are requested.");
  }
}