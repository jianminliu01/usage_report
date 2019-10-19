package com.prototype.cellusage.cleanser;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prototype.cellusage.entity.CellPhoneUsage;
import com.prototype.cellusage.util.Pair;

@Service
public class UsageCleanser {
  public Set<CellPhoneUsage> cleanUp(Set<CellPhoneUsage> inputs) {
    Set<CellPhoneUsage> results = cleanMultipleDailyRecords(inputs);
    results = cleanMultipleMonthlyRecords(results);
    return results;
  }

  private Set<CellPhoneUsage> cleanMultipleDailyRecords(Set<CellPhoneUsage> inputs) {
    return inputs.stream().collect(
      groupingBy(usage -> new Pair(usage.getEmployeeId(), usage.getDate()),
      collectingAndThen(
        reducing((usage1, usage2) -> useMinuteMax(usage1, usage2)), Optional::get)))
      .values()
      .stream()
      .collect(Collectors.toSet());
  }

  private Set<CellPhoneUsage> cleanMultipleMonthlyRecords(Set<CellPhoneUsage> inputs) {
    return inputs.stream().collect(
      groupingBy(usage -> new Pair(usage.getEmployeeId(), usage.getMonth()),
        collectingAndThen(
          reducing((usage1, usage2) -> useSum(usage1, usage2)), Optional::get)))
      .values()
      .stream()
      .collect(Collectors.toSet());
  }

  //For demon with a different cleaning up rule
  private CellPhoneUsage useMinuteMax(CellPhoneUsage usage1, CellPhoneUsage usage2) {
    return usage1.getTotalMinute() > usage2.getTotalMinute() ? usage1 : usage2;
  }

  private CellPhoneUsage useSum(CellPhoneUsage usage1, CellPhoneUsage usage2) {
    return CellPhoneUsage.builder()
      .employeeId(usage1.getEmployeeId())
      .date(usage1.getDate())
      .totalMinute(usage1.getTotalMinute() + usage2.getTotalMinute())
      .totalData(usage1.getTotalData() + usage2.getTotalData()).build();
  }
}
