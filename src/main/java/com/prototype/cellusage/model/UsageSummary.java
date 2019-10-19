package com.prototype.cellusage.model;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class UsageSummary {
  private Date runDate;
  private int numberOfPhones;
  private long totalMinutes;
  private String totalData;
  private String averageMinutes;
  private String averageData;
}
