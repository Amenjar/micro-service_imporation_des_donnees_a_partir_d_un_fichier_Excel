package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;
import java.time.LocalDate;
import java.time.ZoneId;

public class IRM extends Model {
    private static final int WORKER_STATION = 3;
    private static final int MACHINE_STATE = 2;
    private static final int PUISSANCE_IRM = 4;
    private static final int MODEL = 6;
    private static final int SERIAL_NUMBER = 7;
    private static final int DATE_MISE_EN_SERVICE = 8;
    private MetaJsonRecord service;
    private MetaJsonRecord location;
    private MetaJsonRecord brand;
    private String machineState;
    private String workerStation;
    private Number puissanceIRM;
    private String model;
    private Number serialNumber;

    private LocalDate dateMiseEnService;

    public IRM(Row row , MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord brand) {
        if(validRowString(row,MACHINE_STATE)){
            this.machineState = row.getCell(MACHINE_STATE).getStringCellValue();
        }
        if(validRowString(row,WORKER_STATION)){
            this.workerStation = row.getCell(WORKER_STATION).getStringCellValue();
        }
        if(validRow(row,PUISSANCE_IRM)){
            this.puissanceIRM = row.getCell(PUISSANCE_IRM).getNumericCellValue();
        }
        if(validRowString(row,MODEL)){
            this.model = row.getCell(MODEL).getStringCellValue();
        }
        if(validRow(row,SERIAL_NUMBER)){
            this.serialNumber = row.getCell(SERIAL_NUMBER).getNumericCellValue();
        }
        if (validRow(row,DATE_MISE_EN_SERVICE)){
            this.dateMiseEnService = row.getCell(DATE_MISE_EN_SERVICE).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        this.service = service;
        this.location = location;
        this.brand = brand;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }
    public MetaJsonRecord toMetaJsonRecord() {
        MetaJsonRecord metaJsonRecord = new MetaJsonRecord(model);
        metaJsonRecord.setJsonModel("IRM");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }
    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
    }
    private String getAttrs() {
        return "{\"step\": \"2\"," +
                " \"model\": \""+model+"\"," +
                " \"power\": 10," +
                " \"status\": \"<span class=\\\"label hilite-success\\\" style=\\\"display: inline-block; line-height: initial; width: 65px !important;\\\" x-translate>Saisi</span>\"," +
                " \"service\": {\"id\":"+service.getId()+", \"name\": \""+service.getName()+"\", \"$version\": 9, \"$attachments\": 0}," +
                " \"location\": {\"id\":"+location.getId()+", \"name\": \""+location.getName()+"\", \"$version\": 1, \"$attachments\": 0}," +
                " \"machineState\": \""+machineState+"\"," +
                " \"manufacturer\": {\"id\":"+brand.getId()+", \"name\": \""+brand.getName()+"\", \"$version\": 3, \"$attachments\": 0}," +
                " \"serialNumber\": \""+serialNumber+"\", " +
                "\"commissioningDate\": \""+dateMiseEnService+"\"}";
    }
    private boolean validRowString(Row row, int NUM_COLUMN) {
        return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
    }
}
