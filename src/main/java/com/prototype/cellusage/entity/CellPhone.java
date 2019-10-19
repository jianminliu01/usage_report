package com.prototype.cellusage.entity;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CellPhone {
  private Integer employeeId;
  private String employeeName;
  private String purchaseDate;
  private String model;
}
