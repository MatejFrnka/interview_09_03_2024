package cz.pumpitup.interview.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

// maybe to use enum, I chose class to enable adding and removing categories at runtime.
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Category {
  String name;
}
