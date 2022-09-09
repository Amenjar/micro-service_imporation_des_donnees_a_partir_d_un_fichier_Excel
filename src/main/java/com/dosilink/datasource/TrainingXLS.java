package com.dosilink.datasource;

import com.dosilink.database.Database;
import com.axelor.meta.db.MetaJsonRecord;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class TrainingXLS {

  private static final int STRUCTURE_CLIENT_SHEET = 0;
  private static final int TRAINING_SHEET = 2;
  private static final int FUNCTIONGRADE_NAME = 5;
  private static final int FUNCTIONGRADE_TRAINING = 2;
  private static final int TRAINING_TYPE = 0;

  private final String sourceFile;
  private Database database = new Database();


  public TrainingXLS(String sourceFile) {
    this.sourceFile = sourceFile;
  }

  public void saveTrainingSheet() throws Exception {
    ExcelReader reader = new ExcelReader(sourceFile);
    HashMap<String, MetaJsonRecord> mapTrainings = new HashMap<>();
    List<MetaJsonRecord> metaJsonRecordTrainings = database.findTrainingTypes();
    mapTrainings.put("Utilisation des DMERI", metaJsonRecordTrainings.get(0));
    mapTrainings.put("Radioprotection travailleur exposé", metaJsonRecordTrainings.get(1));
    mapTrainings.put("Magnétoprotection", metaJsonRecordTrainings.get(2));
    mapTrainings.put("Radioprotection patient", metaJsonRecordTrainings.get(3));
    mapTrainings.put("Utilisation du dosimètre actif", metaJsonRecordTrainings.get(4));
    HashMap<String, MetaJsonRecord> mapFunctionGrades = new HashMap<>();
    Sheet clientStructureSheet = reader.readSheet(STRUCTURE_CLIENT_SHEET);
    int i = 0;
    for (Row row : clientStructureSheet) {
      if (row.getRowNum() < 2) {
        continue;
      }
      if (validRow(row, FUNCTIONGRADE_NAME)) {
        mapFunctionGrades.put(
            row.getCell(FUNCTIONGRADE_NAME).getStringCellValue(), database.findFunctionGrade().get(i));
        i++;
      }
    }
    System.out.println(mapFunctionGrades);
    Sheet trainingSheet = reader.readSheet(TRAINING_SHEET);
    for (Row row : trainingSheet) {
      if (row.getRowNum() < 2 || !validRow(row,TRAINING_TYPE)) {
        continue;
      }
        MetaJsonRecord functionGrade = null;
        if (validRow(row,FUNCTIONGRADE_TRAINING)){
          functionGrade = mapFunctionGrades.get(row.getCell(FUNCTIONGRADE_TRAINING).getStringCellValue());
          System.out.println(functionGrade);
        }
        MetaJsonRecord trainingType = null;
        if (validRow(row,TRAINING_TYPE)){
          trainingType = mapTrainings.get(row.getCell(TRAINING_TYPE).getStringCellValue());
          System.out.println(trainingType);
        }
        MetaJsonRecord training = database.saveTraining(row, trainingType, functionGrade);
        System.out.println(training);

    }
  }

  private boolean validRow(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }
}
