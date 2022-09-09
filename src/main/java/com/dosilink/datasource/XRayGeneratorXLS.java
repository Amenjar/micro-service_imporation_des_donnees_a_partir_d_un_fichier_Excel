package com.dosilink.datasource;

import com.axelor.meta.db.MetaJsonRecord;
import com.dosilink.database.Database;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;

public class XRayGeneratorXLS {
  private static final int MODEL = 4;
  private static final int STRUCTURE_CLIENT_SHEET = 0;
  private static final int SERVICE_NAME = 2;
  private static final int LOCATION_NAME = 6;
  private static final int SERVICE_GENERATOR = 1;
  private static final int LOCATION_GENERATOR = 2;
  private static final int BRAND_GENERATOR = 3;
  private static final int INSTALLATION_TYPE_GENERATOR = 9;
  private static final int BRAND_NAME = 9;
  private static final int INSTALLATION_TYPE_NAME = 8;
  private static final int GENERATOR_SHEET = 3;
  private static final int WORK_STATION_NAME = 7;
  private static final int MODALITY = 0;
  private static final int SERIAL_NUMBER = 5;
  private final String sourceFile;

  private Database database = new Database();
  public XRayGeneratorXLS(String sourceFile) {
    this.sourceFile = sourceFile;
  }

  public void saveGeneratorSheet ()throws Exception{
    ExcelReader reader = new ExcelReader(sourceFile);
    HashMap<String, MetaJsonRecord> mapModalities = new HashMap<>();
    List<MetaJsonRecord> modalities = database.findModality();
    List<MetaJsonRecord> services = database.findService();
    List<MetaJsonRecord> locations = database.findLocations();
    List<MetaJsonRecord> brands = database.findBrand();
    List<MetaJsonRecord> installationTypes = database.findInstallationTypes();
    mapModalities.put("Mammographie",modalities.get(0));
    mapModalities.put("Médecine nucléaire",modalities.get(1));
    mapModalities.put("Ostéodensitométrie",modalities.get(2));
    mapModalities.put("Radiologie Conventionnelle",modalities.get(3));
    mapModalities.put("Radiologie dentaire",modalities.get(4));
    mapModalities.put("Radiologie interventionnelle",modalities.get(5));
    mapModalities.put("Radiothérapie",modalities.get(6));
    mapModalities.put("Scanner",modalities.get(7));
    Sheet clientStructureSheet = reader.readSheet(STRUCTURE_CLIENT_SHEET);
    Sheet generatorSheet = reader.readSheet(GENERATOR_SHEET);
    for (Row row : generatorSheet){
      if (row.getRowNum()<2  || !validRow(row,MODALITY)){
        continue;
      }
      if (validRow(row,WORK_STATION_NAME)){
        database.saveWorkerStation(row);
      }
    }
    List<MetaJsonRecord> workerStations = database.findWorkerStation();
    HashMap<String,MetaJsonRecord> mapServices = createMapFromXls(clientStructureSheet,services,SERVICE_NAME);
    HashMap<String,MetaJsonRecord> mapLocations = createMapFromXls(clientStructureSheet,locations,LOCATION_NAME);
    HashMap<String,MetaJsonRecord> mapBrands = createMapFromXls(clientStructureSheet,brands,BRAND_NAME);
    HashMap<String,MetaJsonRecord> mapInstallationTypes = createMapFromXls(clientStructureSheet,installationTypes,INSTALLATION_TYPE_NAME);
    HashMap<String,MetaJsonRecord> mapWorkerStations = createMapFromXls(generatorSheet,workerStations,WORK_STATION_NAME);
    for (Row row : generatorSheet){
      if (row.getRowNum() <2  || !validRow(row,MODALITY)){
        System.out.println("generateur row number: " + row.getRowNum());
        continue;
      }
        System.out.println("generateur row number: " + row.getRowNum());
        MetaJsonRecord modality = null;
        if (validRow(row,MODALITY)) {
          modality = mapModalities.get(row.getCell(MODALITY).getStringCellValue());
        }
        MetaJsonRecord service = null;
        if (validRow(row,SERVICE_GENERATOR)) {
          service = mapServices.get(row.getCell(SERVICE_GENERATOR).getStringCellValue());
        }
        MetaJsonRecord location = null;
        if (validRow(row,LOCATION_GENERATOR)) {
          location = mapLocations.get(row.getCell(LOCATION_GENERATOR).getStringCellValue());
        }
        MetaJsonRecord brand = null;
        if (validRow(row,BRAND_GENERATOR)) {
          brand = mapBrands.get(row.getCell(BRAND_GENERATOR).getStringCellValue());
        }
        MetaJsonRecord workerStation = null;
        if (validRow(row,WORK_STATION_NAME)) {
          workerStation = mapWorkerStations.get(row.getCell(WORK_STATION_NAME).getStringCellValue());
        }
        MetaJsonRecord installationType = null;
        if (validRow(row,INSTALLATION_TYPE_GENERATOR)) {
          installationType = mapInstallationTypes.get(row.getCell(INSTALLATION_TYPE_GENERATOR).getStringCellValue());
        }
        MetaJsonRecord generatorRX = null;
        if (validRow(row,INSTALLATION_TYPE_GENERATOR)) {
          generatorRX = database.saveGenerator(row, modality, service, location, brand, workerStation, installationType);
        }
        if(generatorRX != null){
          database.saveQualityControl(row, service, location, modality, generatorRX,installationType);
          database.saveEquipmentTechnicalControl(row,service, location, modality, installationType, generatorRX);
        }
    }
  }
  private boolean validRow(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
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
}
