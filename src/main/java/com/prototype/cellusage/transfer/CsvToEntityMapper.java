package com.prototype.cellusage.transfer;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.prototype.cellusage.entity.CellPhone;
import com.prototype.cellusage.entity.CellPhoneUsage;

@Component
public class CsvToEntityMapper {
  private static final String CSV_SEPARATOR = ",";

   public Function<String, CellPhoneUsage> toCellPhoneUsage() {
   return line -> {
     String[] usages = line.split(CSV_SEPARATOR);
     return CellPhoneUsage
       .builder()
       .employeeId(Integer.valueOf(usages[0]))
       .date(usages[1])
       .totalMinute(Integer.valueOf(usages[2]))
       .totalData(Float.valueOf(usages[3])).build();
     };
  }

  public Function<String, CellPhone> toCellPhone() {
    return line -> {
      String[] phones = line.split(CSV_SEPARATOR);
      return CellPhone
        .builder()
        .employeeId(Integer.valueOf(phones[0]))
        .employeeName(phones[1])
        .purchaseDate(phones[2])
        .model(phones[3]).build();
    };
  }
}
