package com.prototype.cellusage.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@Builder
public class CellPhoneUsage {
  private static final String DATE_SEPARATOR = "/";
  private Integer employeeId;
  private String date;
  private Integer totalMinute;
  private float totalData;

  public Integer getMonth() {
    return Integer.valueOf(date.split(DATE_SEPARATOR)[0]);
  }
}
