package com.prototype.cellusage;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.print.PrintException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.prototype.cellusage.cleanser.UsageCleanser;
import com.prototype.cellusage.entity.CellPhone;
import com.prototype.cellusage.entity.CellPhoneUsage;
import com.prototype.cellusage.model.UsageByMonth;
import com.prototype.cellusage.model.UsageSummary;
import com.prototype.cellusage.reader.CsvReader;
import com.prototype.cellusage.render.PdfRender;
import com.prototype.cellusage.transfer.CsvToEntityMapper;
import com.prototype.cellusage.transfer.EntityToModelMapper;
import com.prototype.cellusage.viewer.Printer;

@Service
@Log4j2
public class CellPhoneUsageReporter {

  private static final String PHONE_FILE_NAME = "CellPhone.csv";
  private static final String USAGE_FILE_NAME = "CellPhoneUsageByMonth.csv";

  @Autowired
  private CsvReader reader;

  @Autowired
  private CsvToEntityMapper csvToEntityMapper;

  @Autowired
  private UsageCleanser cleanser;

  @Autowired
  private EntityToModelMapper entityToModelMapper;

  @Autowired
  private PdfRender render;

  @Autowired
  private Printer printer;

  public void generateReport() {
    Set<CellPhone> phones = reader.read(PHONE_FILE_NAME, csvToEntityMapper.toCellPhone());
    Set<CellPhoneUsage> usages = reader.read(USAGE_FILE_NAME, csvToEntityMapper.toCellPhoneUsage());
    usages = cleanser.cleanUp(usages);

    UsageSummary summary = entityToModelMapper.generateSummary(usages, phones);

    Map<Integer, UsageByMonth> details =  entityToModelMapper.generateDetails(usages, phones);

    try {
      printer.printPdf(render.rendering(summary, details));
    } catch (DocumentException | PrintException | IOException e) {
      log.error("Printing failed", e);
    }

    log.info("Successfully print out the cell phone usage report!");
  }
}
