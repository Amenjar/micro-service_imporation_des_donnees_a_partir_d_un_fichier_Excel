package com.dosilink.datasource;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

  private final String path;

  public ExcelReader(String path) {
    this.path = path;
  }

  private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)
      throws IOException {
    Workbook workbook;
    if (excelFilePath.endsWith("xlsx")) {
      workbook = new XSSFWorkbook(inputStream);
    } else if (excelFilePath.endsWith("xls")) {
      workbook = new HSSFWorkbook(inputStream);
    } else {
      throw new IllegalArgumentException("The specified file is not Excel file");
    }
    return workbook;
  }

  public Sheet readSheet(int indexOfSheet) throws Exception {

    FileInputStream inputStream = new FileInputStream(this.path);
    ExcelReader r = new ExcelReader(this.path);
    Workbook workbook = r.getWorkbook(inputStream, this.path);
    Sheet styleSheet = workbook.getSheetAt(indexOfSheet);
    return styleSheet;
  }
}
