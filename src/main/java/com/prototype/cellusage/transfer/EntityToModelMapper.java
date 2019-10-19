package com.prototype.cellusage.transfer;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prototype.cellusage.model.UsageByMonth;
import com.prototype.cellusage.model.UsageSummary;
import com.prototype.cellusage.entity.CellPhoneUsage;
import com.prototype.cellusage.entity.CellPhone;

@Component
public class EntityToModelMapper {
  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");

  public UsageSummary generateSummary(Set<CellPhoneUsage> usages, Set<CellPhone> phones) {
    Date runDate = new Date();
    int numberOfPhones = phones.size();
    int totalMinutes = usages.stream().map(CellPhoneUsage ::getTotalMinute).reduce(0, Integer::sum);
    float totalData = usages.stream().map(CellPhoneUsage ::getTotalData).reduce(0f, Float::sum);
    int averageMinutes = totalMinutes / numberOfPhones;
    float averageData = totalData / numberOfPhones;

    return UsageSummary
      .builder()
      .runDate(runDate)
      .numberOfPhones(numberOfPhones)
      .totalMinutes(totalMinutes)
      .totalData(DECIMAL_FORMAT.format(totalData))
      .averageMinutes(DECIMAL_FORMAT.format(averageMinutes))
      .averageData(DECIMAL_FORMAT.format(averageData))
      .build();
  }

  public Map<Integer, UsageByMonth> generateDetails(Set<CellPhoneUsage> usages, Set<CellPhone> phones) {
    Map<Integer, List<CellPhoneUsage>> usageByMonths = usages.stream().collect(groupingBy(CellPhoneUsage ::getEmployeeId));

    return toUsageDetails(usageByMonths, phones);
  }

  private Map<Integer, UsageByMonth> toUsageDetails(Map<Integer, List<CellPhoneUsage>> results, Set<CellPhone> phones) {
    return results.entrySet().stream().collect(toMap(
      Map.Entry::getKey,
      usageByMonth -> toUsageDetail(usageByMonth.getValue().stream().collect(groupingBy(CellPhoneUsage ::getMonth)), phones)
    ));
  }

  private UsageByMonth toUsageDetail(Map<Integer, List<CellPhoneUsage>> usageByMonths, Set<CellPhone> phones) {
    Integer employeeId = usageByMonths.get(1).get(0).getEmployeeId();
    Optional<CellPhone> phoneOptional = phones.stream().filter(phone -> employeeId.equals(phone.getEmployeeId())).findAny();
    String employeeName = phoneOptional.isPresent() ? phoneOptional.get().getEmployeeName() : null;
    String purchaseDate = phoneOptional.isPresent() ? phoneOptional.get().getPurchaseDate() : null;
    String model = phoneOptional.isPresent() ? phoneOptional.get().getModel() : null;
    return UsageByMonth
      .builder()
      .employeeId(employeeId)
      .employeeName(employeeName)
      .model(model)
      .purchaseDate(purchaseDate)
      .minutesPerMonth(sumMinutes(usageByMonths))
      .dataPerMonth(sumData(usageByMonths))
      .build();
  }

  private Map<Integer, Integer> sumMinutes(Map<Integer, List<CellPhoneUsage>> usageByMonths) {
    return usageByMonths.entrySet().stream().collect(Collectors.toMap(
      Map.Entry::getKey,
      entry -> entry.getValue().stream().map(CellPhoneUsage ::getTotalMinute).reduce(0, Integer::sum)
    ));
  }

  private Map<Integer, String> sumData(Map<Integer, List<CellPhoneUsage>> usageByMonths) {
    return usageByMonths.entrySet().stream().collect(Collectors.toMap(
      Map.Entry::getKey,
      entry -> DECIMAL_FORMAT.format(entry.getValue().stream().map(CellPhoneUsage ::getTotalData).reduce(0f, Float::sum))
    ));
  }
}
