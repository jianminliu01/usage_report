package com.prototype.cellusage.model;

import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder(toBuilder = true)
public class UsageByMonth {
  private Integer employeeId;
  private String employeeName;
  private String model;
  private String purchaseDate;
  private Map<Integer, Integer> minutesPerMonth;
  private Map<Integer, String> dataPerMonth;
}
