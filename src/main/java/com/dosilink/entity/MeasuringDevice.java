package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;
import java.time.LocalDate;
import java.time.ZoneId;

public class MeasuringDevice extends Model {
    private static final int MODEL = 2;
    private static final int DATE_DERNIER_VERFICATION = 6;
    private static final int DATE_DERNIER_ETALONAGE = 7;
    private static final int SERIAL_NUMBER = 3;
    private MetaJsonRecord measuringDeviceType;
    private MetaJsonRecord brand;
    private String model;
    private Number serialNumber;
    private MetaJsonRecord service;
    private MetaJsonRecord location;
    private LocalDate dateDernierVerfication;
    private LocalDate dateDernierEtalonage;

    public MeasuringDevice(Row row, MetaJsonRecord measuringDeviceType, MetaJsonRecord brand, MetaJsonRecord service, MetaJsonRecord location) {
        if(validRowString(row,MODEL)){
            this.model = row.getCell(MODEL).getStringCellValue();
        }
        if(validRow(row,SERIAL_NUMBER)){
            this.serialNumber = row.getCell(SERIAL_NUMBER).getNumericCellValue();
        }
        if(validRow(row,DATE_DERNIER_VERFICATION)){
            this.dateDernierVerfication = row.getCell(DATE_DERNIER_VERFICATION).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (validRow(row,DATE_DERNIER_ETALONAGE)){
            this.dateDernierEtalonage = row.getCell(DATE_DERNIER_ETALONAGE).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        this.measuringDeviceType = measuringDeviceType;
        this.brand = brand;
        this.service = service;
        this.location = location;
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
        metaJsonRecord.setJsonModel("MeasuringEquipment");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }
    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
    }
    private String getAttrs() {
        return " {\"step\": \"end\"," +
                " \"model\": \""+model+"\"," +
                " \"status\": \"Disponible\"," +
                " \"deleted\": \"false\"," +
                " \"service\": {\"id\":"+service.getId()+", \"name\":\""+service.getName()+"\", \"$version\": 5}, "+
                " \"deviceType\": {\"id\":"+measuringDeviceType.getId()+", \"name\": \""+measuringDeviceType.getName()+"\", \"$version\": 1, \"$attachments\": 0}," +
                " \"statusHtml\": \"<span class=\\\"label label-success\\\" style=\\\"display: inline-table; line-height: initial; width: 80px !important;\\\">Disponible</span>\"," +
                " \"manufacturer\": {\"id\":"+brand.getId()+", \"name\": \""+brand.getName()+"\", \"$version\": 1}," +
                " \"serialnumber\": \""+serialNumber+"\" ,"+
                " \"calibrationUnit\": \"Year\"," +
                " \"controlsFollowUp\": [{\"id\": 28110}]," +
                " \"controlStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #5cb85c; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 100px !important;\\\" >Conforme</span>\"," +
                " \"periodicVerification\": \""+dateDernierVerfication+"\"," +
                " \"calibrationPeriodicity\": 3," +
                " \"periodicVerificationUnit\": \"Year\"," +
                " \"periodicVerificationPeriodicity\": 2}";
    }
    private boolean validRowString(Row row, int NUM_COLUMN) {
        return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
    }
}
