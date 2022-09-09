package com.dosilink.datasource;

import com.dosilink.database.Database;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.mail.event.MailEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientStructureXLS {
  private final String sourceFile;
  private static final int STRUCTURE_CLIENT_SHEET = 0;
  private static final int SITE_NAME = 0;
  private static final int SERVICE_NAME = 2;
  private static final int LOCALISATION_NAME = 6;
  private static final int FUNCTIONGRADE_NAME = 5;
  private static final int MEASURING_DEVICE_TYPE = 12;
  private static final int INSTALLATION_TYPE_NAME = 8;
  private static final int BRAND_SUPPLIER_NAME = 9;
  private static final int BRAND_SUPPLIER_NAME_EPI = 11;


  private Database database = new Database();


  public ClientStructureXLS(String sourceFile) {
    this.sourceFile = sourceFile;
  }

  public void saveStructureClientSheet() throws Exception {
    ExcelReader read = new ExcelReader(sourceFile);
    Sheet structureClientSheet = read.readSheet(STRUCTURE_CLIENT_SHEET);
    for (Row row : structureClientSheet) {
      if (row.getRowNum() < 2) {
        continue;
      }

     MetaJsonRecord site = null;
      if (validRow(row, SITE_NAME)) {
        site = database.saveSite(row);
      }

      MetaJsonRecord service = null;
      if (validRow(row, SERVICE_NAME) && site != null) {
        service = database.saveService(row, site);
      }

      MetaJsonRecord location = null;
      if (validRow(row, LOCALISATION_NAME) && service != null) {
        location = database.saveLocalisation(row, service);
      }

      System.out.println(site);
     System.out.println(service);
      System.out.println(location);

      if (validRow(row, FUNCTIONGRADE_NAME)) {
        MetaJsonRecord functionGrade = database.saveFunctionGrade(row);
        System.out.println(functionGrade);
      }
      if (validRow(row, MEASURING_DEVICE_TYPE)) {
        MetaJsonRecord measuringDeviceType = database.saveMeasuringDeviceType(row);
        System.out.println(measuringDeviceType);
      }
     /* if (validRow(row, INSTALLATION_TYPE_NAME)) {
        MetaJsonRecord installationType = database.saveInstallationType(row);
        System.out.println(installationType);
      }*/
      if (validRow(row, BRAND_SUPPLIER_NAME)) {
        MetaJsonRecord brandOrSupplier = database.saveBrandOrSupplier(row);
        System.out.println(brandOrSupplier);
      }
    }
      List<MetaJsonRecord> brands = database.findBrand();
      System.out.println(brands);
      HashMap<String,MetaJsonRecord> mapBrands = createMapFromXls(structureClientSheet,brands,BRAND_SUPPLIER_NAME);
      System.out.println(mapBrands);
    for(Row row : structureClientSheet){
        if (row.getRowNum()<2){
            continue;
        }
        if(validRow(row,BRAND_SUPPLIER_NAME_EPI)) {
            System.out.println(validBrand(row));
           if(validBrand(row)){
               MetaJsonRecord updateBrand = mapBrands.get(row.getCell(BRAND_SUPPLIER_NAME_EPI).getStringCellValue());
               System.out.println(updateBrand);
               updateBrand.setAttrs("{\"name\": \"" + updateBrand.getName() + "\",\"type\": \"Brand\", \"domain\": \"MRI, Protection equipment\",\"deleted\": \"false\"}");
               System.out.println(updateBrand);
           }else{
               MetaJsonRecord brandEpi = database.saveBrandOrSupplierEpi(row);
               System.out.println(brandEpi);
           }

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
    private boolean validBrand(Row row){
        List<MetaJsonRecord> brands = database.findBrand();
        int i =0;
        boolean test = false;
        while (!test && i<brands.size() ){
            if(row.getCell(BRAND_SUPPLIER_NAME_EPI).getStringCellValue().equals(brands.get(i).getName())){
                test = true;
            }
            else {
                i++;
            }
        }
        return test;
    }

    private boolean validRow(Row row, int NUM_COLUMN) {
        return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
    }
}
