package com.prototype.cellusage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CellPhoneUsageReportApplication implements CommandLineRunner {

  @Autowired
  private CellPhoneUsageReporter reporter;

  public static void main(String[] args) {
    SpringApplication.run(CellPhoneUsageReportApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    reporter.generateReport();
  }
}
