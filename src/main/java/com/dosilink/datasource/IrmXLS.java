package com.dosilink.datasource;

import com.axelor.meta.db.MetaJsonRecord;
import com.dosilink.database.Database;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.HashMap;
import java.util.List;

public class IrmXLS {
    private static final int STRUCTURE_CLIENT_SHEET = 0;
    private static final int IRM_SHEET = 4;
    private static final int SERVICE_NAME = 2;
    private static final int LOCATION_NAME = 6;
    private static final int BRAND_NAME = 9;
    private static final int SERVICE = 0;
    private static final int LOCATION = 1;
    private static final int BRAND = 5;
    private String sourceFile;
    private Database database = new Database();

    public IrmXLS(String sourceFile) {
        this.sourceFile = sourceFile;
    }
    public void saveIRMSheet () throws Exception{
        ExcelReader reader = new ExcelReader(sourceFile);
        Sheet clientStructureSheet = reader.readSheet(STRUCTURE_CLIENT_SHEET);
        List<MetaJsonRecord> services = database.findService();
        HashMap<String,MetaJsonRecord> mapServices = createMapFromXls(clientStructureSheet,services,SERVICE_NAME);
        System.out.println(mapServices);
        List<MetaJsonRecord> locations = database.findLocations();
        HashMap<String,MetaJsonRecord> mapLocations = createMapFromXls(clientStructureSheet,locations,LOCATION_NAME);
        System.out.println(mapLocations);
        List<MetaJsonRecord> brands = database.findBrand();
        HashMap<String,MetaJsonRecord> mapBrands = createMapFromXls(clientStructureSheet,brands,BRAND_NAME);
        System.out.println(mapBrands);
        Sheet irmSheet = reader.readSheet(IRM_SHEET);
        for(Row row : irmSheet){
            if(row.getRowNum()<2 || !validRow(row,SERVICE)){
                continue;
            }
                MetaJsonRecord service = null;
                if (validRow(row,SERVICE)) {
                    service = mapServices.get(row.getCell(SERVICE).getStringCellValue());
                    System.out.println(service);
                }
                MetaJsonRecord location = null;
                if (validRow(row,LOCATION)){
                    location = mapLocations.get(row.getCell(LOCATION).getStringCellValue());
                    System.out.println(location);
                }
                MetaJsonRecord brand = null;
                if (validRow(row,BRAND)){
                    brand = mapBrands.get(row.getCell(BRAND).getStringCellValue());
                    System.out.println(brand);
                }
                MetaJsonRecord irm = database.saveIRM(row,service,location,brand);
                System.out.println(irm);

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
