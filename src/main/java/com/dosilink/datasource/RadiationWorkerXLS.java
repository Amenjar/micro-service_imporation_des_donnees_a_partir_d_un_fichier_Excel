package com.dosilink.datasource;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.hr.db.Employee;
import com.axelor.meta.db.MetaJsonRecord;
import com.dosilink.database.Database;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class RadiationWorkerXLS {
  private static final int STRUCTURE_CLIENT_SHEET = 0;
  private static final int RADIATION_WORKER_SHEET = 1;

  private static final int WORKER_NAME = 2;
  private static final int FUNCTIONGRADE_NAME = 5;
  private static final int FUNCTIONGRADE_RADIATION_WORKER = 11;
  private static final int SERVICE_NAME = 2;
  private static final int SERVICE_RADIATION_WORKER = 12;
  private static final int NUMBER_SISERI = 10;
  private final String sourceFile;
  private Database database = new Database();

  public RadiationWorkerXLS(String sourceFile) {
    this.sourceFile = sourceFile;
  }

  public void saveRadiationWorkerSheet() throws Exception {
    ExcelReader reader = new ExcelReader(sourceFile);
    List<MetaJsonRecord> individualEvaluations = database.saveIndividualEvaluation();
    System.out.println(individualEvaluations);
    List<MetaJsonRecord> doseConstraints = database.saveDoseConstraint();
    System.out.println(doseConstraints);
    List<MetaJsonRecord> passiveDosimetries = database.savePassiveDosimetry();
    System.out.println(passiveDosimetries);
    MetaJsonRecord [] tabIndividualEvaluations = {individualEvaluations.get(0),individualEvaluations.get(1)};
    MetaJsonRecord [] tabDoseConstraints = {doseConstraints.get(0),doseConstraints.get(1),doseConstraints.get(2)};
    MetaJsonRecord [] tabPassiveDosimetries = {passiveDosimetries.get(0),passiveDosimetries.get(1),passiveDosimetries.get(2),passiveDosimetries.get(3)};
    Sheet clientStructureSheet = reader.readSheet(STRUCTURE_CLIENT_SHEET);
    List<MetaJsonRecord> functionGrades = database.findFunctionGrade();
    HashMap<String, MetaJsonRecord> mapFunctionGrades = createMapFromXls(clientStructureSheet,functionGrades,FUNCTIONGRADE_NAME);
    System.out.println(mapFunctionGrades);
    List<MetaJsonRecord> services = database.findService();
    HashMap<String, MetaJsonRecord> mapServices = createMapFromXls(clientStructureSheet,services,SERVICE_NAME);
    System.out.println(mapServices);
    Sheet radiationWorkerSheet = reader.readSheet(RADIATION_WORKER_SHEET);
    for (Row row : radiationWorkerSheet) {
      if (row.getRowNum() < 2 || !validRow(row, WORKER_NAME)) {
        System.out.println("worker row number: " + row.getRowNum());
        continue;
      }
        MetaJsonRecord functionGrade = null;
        if(validRow(row,FUNCTIONGRADE_RADIATION_WORKER)) {
          functionGrade = mapFunctionGrades.get(row.getCell(FUNCTIONGRADE_RADIATION_WORKER).getStringCellValue());
          System.out.println(functionGrade);
        }
        MetaJsonRecord service = null;
        if(validRow(row,SERVICE_RADIATION_WORKER)) {
          service = mapServices.get(row.getCell(SERVICE_RADIATION_WORKER).getStringCellValue());
          System.out.println(service);
        }
        if(validRow(row,WORKER_NAME)) {
          Partner partner = database.createPartner(row);
          System.out.println(partner);
          Employee employee = database.createEmployee(row, functionGrade, service);
          database.saveEmployee(employee);
          System.out.println(employee);
          MetaJsonRecord radiationWorker = database.saveRadiationWorker(row, functionGrade, service, employee, partner, tabDoseConstraints, tabIndividualEvaluations, tabPassiveDosimetries);
          employee.setAttrs(" {\"deleted\": false, \"employeeService\": {\"id\":\"" + service.getId() + "\", \"name\": \"" + service.getName() + "\", \"$version\": 0},\"radiationWorker\": {\"id\":\"" + radiationWorker.getId() + "\"},\"employeeFunction\": {\"id\":\"" + functionGrade.getId() + "\", \"name\":\"" + functionGrade.getName() + "\", \"$version\": 0}, \"serviceAffectation\": {\"id\":\"" + service.getId() + "\", \"name\": \"" + service.getName() + "\"}}");
          database.saveEmployee(employee);
        }

    }

  }
  private HashMap<String,MetaJsonRecord> createMapFromXls (Sheet xlsSheet,List<MetaJsonRecord> metaJsonRecords,int NUM_COLUMN){
    int i =0;
    HashMap<String,MetaJsonRecord> metaJsonRecordHashMap = new HashMap<>();
    for(Row row : xlsSheet){
      if (row.getRowNum()<2){
        continue;
      }
      if(validRow(row,NUM_COLUMN)){
        metaJsonRecordHashMap.put(row.getCell(NUM_COLUMN).getStringCellValue(),metaJsonRecords.get(i));
        i++;
      }
    }
    return metaJsonRecordHashMap;
  }

  private boolean validRow(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }
}
