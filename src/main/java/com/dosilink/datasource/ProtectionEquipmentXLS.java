package com.dosilink.datasource;

import com.axelor.meta.db.MetaJsonRecord;
import com.dosilink.database.Database;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;

public class ProtectionEquipmentXLS {
    private static final int STRUCTURE_CLIENT_SHEET = 0;
    private static final int PROTECTION_EQUIPMENT_SHEET = 5;
    private static final int BRAND_NAME = 9;
    private static final int SERVICE_NAME = 2;
    private static final int LOCATION_NAME = 6;
    private static final int BRAND = 2;
    private static final int SERVICE = 4;
    private static final int LOCATION = 5;
    private Database database = new Database();
    private String sourceFile;

    public ProtectionEquipmentXLS(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void saveProtectionEquipment() throws Exception{
        ExcelReader reader = new ExcelReader(sourceFile);
        Sheet clientStructureSheet = reader.readSheet(STRUCTURE_CLIENT_SHEET);
        List<MetaJsonRecord> brands = database.findBrand();
        HashMap<String,MetaJsonRecord> mapBrands = createMapFromXls(clientStructureSheet,brands,BRAND_NAME);
        List<MetaJsonRecord> services = database.findService();
        HashMap<String,MetaJsonRecord> mapServices = createMapFromXls(clientStructureSheet,services,SERVICE_NAME);
        List<MetaJsonRecord> locations = database.findLocations();
        HashMap<String,MetaJsonRecord> mapLocations = createMapFromXls(clientStructureSheet,locations,LOCATION_NAME);
        Sheet protectionEquipmentSheet = reader.readSheet(PROTECTION_EQUIPMENT_SHEET);
        for(Row row : protectionEquipmentSheet){
            if (row.getRowNum()<2 || !validRow(row,BRAND)){
                continue;
            }
                MetaJsonRecord brand = null;
                if (validRow(row,BRAND)) {
                    brand = mapBrands.get(row.getCell(BRAND).getStringCellValue());
                    System.out.println(brand);
                }
                MetaJsonRecord service = null;
                if (validRow(row,SERVICE)){
                    service = mapServices.get(row.getCell(SERVICE).getStringCellValue());
                    System.out.println(service);
                }
                MetaJsonRecord location = null;
                if (validRow(row,LOCATION)){
                    location = mapLocations.get(row.getCell(LOCATION).getStringCellValue());
                    System.out.println(location);
                }
                MetaJsonRecord protectionEquipment = database.saveProtectionEquipment(row,brand,service,location);
                System.out.println(protectionEquipment);

        }

    }

    private HashMap<String, MetaJsonRecord> createMapFromXls (Sheet xlsSheet, List<MetaJsonRecord> metaJsonRecords, int NUM_COLUMN){
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
