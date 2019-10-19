package com.prototype.cellusage.viewer;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import org.springframework.stereotype.Component;

@Component
public class Printer {

  public void printPdf(byte[] reportBytes) throws PrintException, IOException {
    javax.print.PrintService printService = PrintServiceLookup
      .lookupDefaultPrintService();
    DocPrintJob printJob = printService.createPrintJob();
    InputStream is = new ByteArrayInputStream(reportBytes);
    Doc pdfDoc = new SimpleDoc(is, DocFlavor.INPUT_STREAM.PDF, null);
    is.close();
    HashPrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
    attributeSet.add(OrientationRequested.LANDSCAPE);
    attributeSet.add(MediaSizeName.ISO_A4);
    attributeSet.add(new JobName("Usage Report", null));
    attributeSet.add(DialogTypeSelection.NATIVE);
    printJob.print(pdfDoc, attributeSet);
  }
}
