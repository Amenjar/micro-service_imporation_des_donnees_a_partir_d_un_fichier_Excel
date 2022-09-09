package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;

public class QualityControl extends Model {
    private static final int SERIAL_NUMBER = 5;
    private static final int COMMOSSION_ING_DATE = 10;
    private static final int DATE_OF_LAST_MONTHLY_CQI = 15;
    private static final int DATE_OF_LAST_CQI = 14;
    private static final int DATE_OF_LAST_EQC = 13;

    private MetaJsonRecord service;
    private MetaJsonRecord location;
    private MetaJsonRecord modality;
    private MetaJsonRecord installationType;
    private Number serialNumber;
    private LocalDate commissioningDate;
    private LocalDate dateOfLastQuarterLyIQC;
    private LocalDate dailyIQCView;
    private MetaJsonRecord xRayGenerator;
    private LocalDate dateOfLastMonthlyIQC;
    private boolean hasDailyIQC;
    private boolean hasIQCAudit;
    private boolean hasAnnualEQC;
    private boolean hasAnnualIQC;
    private boolean hasInitialIQC;
    private boolean hasMonthlyIQC;
    private boolean hasQuarterlyIQC;
    private boolean hasSemiAnnualEQC;
    private boolean hasSemiAnnualIQC;
    private String qualityControlSteps;

    public QualityControl(Row row, MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord modality, MetaJsonRecord xRayGenerator,MetaJsonRecord installationType) {
        if (validRow(row,SERIAL_NUMBER)){
            this.serialNumber = row.getCell(SERIAL_NUMBER).getNumericCellValue();
        }
        if (validRow(row,COMMOSSION_ING_DATE)){
           this.commissioningDate = row.getCell(COMMOSSION_ING_DATE).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (validRow(row, DATE_OF_LAST_MONTHLY_CQI)){
            this.dateOfLastMonthlyIQC = row.getCell(DATE_OF_LAST_MONTHLY_CQI).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if(validRow(row,DATE_OF_LAST_CQI)){
            this.dailyIQCView = row.getCell(DATE_OF_LAST_CQI).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (validRow(row,DATE_OF_LAST_EQC)){
            this.dateOfLastQuarterLyIQC = row.getCell(DATE_OF_LAST_EQC).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        this.service = service;
        this.location = location;
        this.modality = modality;
        this.xRayGenerator = xRayGenerator;
        this.installationType = installationType;

    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }
    public MetaJsonRecord toMetaJsonRecord() {
        MetaJsonRecord metaJsonRecord = new MetaJsonRecord();
        metaJsonRecord.setJsonModel("Qualitycontrol");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }
    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
    }
    private String getAttrs() {
        return "{\"year\":\""+ LocalDate.now().getYear()+"\" ," +
                " \"month\":\""+ LocalDate.now().getMonth()+"\"," +
                " \"taskId\":\""+""+"\" ," +
                " \"deleted\": false," +
                " \"service\": {\"id\":"+service.getId()+", \"name\": \""+service.getName()+"\", \"$version\": 9}," +
                " \"location\": {\"id\":"+location.getId()+", \"name\": \""+location.getName()+"\", \"$version\": 3}," +
                " \"modality\": {\"id\":"+modality.getId()+", \"name\": \""+modality.getName()+"\", \"$version\": 2}," +
                " \"iqcStatus\": \"Non conforme\"," +
                " \"hasDailyIQC\": true," +
                " \"hasIQCAudit\": false," +
                " \"hasAnnualEQC\": false," +
                " \"hasAnnualIQC\": false," +
                " \"serialNumber\": \""+serialNumber+"\"," +
                " \"generalStatus\": \"Non conforme\"," +
                " \"hasInitialIQC\": true," +
                " \"hasMonthlyIQC\": true," +
                " \"dailyIQCStatus\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #d9534f; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">Non conforme</span>\"," +
                " \"monthlyIQCUnit\": \"Mois\"," +
                " \"hasFiveYearsEQC\": false," +
                " \"hasQuarterlyIQC\": true," +
                " \"hasSemiAnnualEQC\": false," +
                " \"hasSemiAnnualIQC\": false," +
                " \"dateOfLastQuarterLyIQC\":\""+this.dateOfLastQuarterLyIQC+"\", "+
                " \"dailyIQCView\":\""+this.dailyIQCView+"\", "+
                " \"initialIQCStatus\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #d9534f; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">Non conforme</span>\"," +
                " \"installationType\": {\"id\":"+installationType.getId()+", \"name\": \""+installationType.getName()+"\", \"$version\": 1}," +
                " \"monthlyIQCStatus\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #d9534f; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">Non conforme</span>\"," +
                " \"quarterlyIQCUnit\": \"Mois\"," +
                " \"commissioningDate\": \""+commissioningDate+"\"," +
                " \"generalStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #d9534f; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">Non conforme</span>\"," +
                " \"hasFourQuarterIQC\": false," +
                " \"generalStatusColor\": \"⬤\"," +
                " \"medicalEquipmentId\":"+xRayGenerator.getId()+"," +
                " \"monthlyIQCPlanning\":\""+""+"\" ," +
                " \"quarterlyIQCStatus\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #d9534f; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">Non conforme</span>\"," +
                " \"qualityControlSteps\": \"IQC/daily,IQC/initial,IQC/monthly,IQC/quarterly,\"," +
                " \"dateOfLastMonthlyIQC\": \""+dateOfLastMonthlyIQC+"\"," +
                " \"monthlyIQCPeriodicity\": \"1\"," +
                " \"quarterlyIQCPeriodicity\": \"3\"," +
                " \"hasIQCFollowingMaintenance\": false, " +
                "\"hasIQCFollowingAChangeOfXRayTube\": false}";
    }
}
