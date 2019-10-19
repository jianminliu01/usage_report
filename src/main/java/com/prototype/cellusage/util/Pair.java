package com.prototype.cellusage.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@AllArgsConstructor
public class Pair<F, S> {
  private F first;
  private S second;
}
