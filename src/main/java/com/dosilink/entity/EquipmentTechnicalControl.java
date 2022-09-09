package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;

public class EquipmentTechnicalControl extends Model {
    private static final int COMMOSSION_ING_DATE = 10;
    private MetaJsonRecord service;
    private MetaJsonRecord location;
    private MetaJsonRecord modality;
    private MetaJsonRecord installationType;
    private MetaJsonRecord xRayGenerator;
    private LocalDate commissioningDate;

    public EquipmentTechnicalControl(Row row,MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord modality, MetaJsonRecord installationType, MetaJsonRecord xRayGenerator) {
        if(validRow(row,COMMOSSION_ING_DATE)){
            this.commissioningDate = row.getCell(COMMOSSION_ING_DATE).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        this.service = service;
        this.location = location;
        this.modality = modality;
        this.installationType = installationType;
        this.xRayGenerator = xRayGenerator;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }
    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
    }

    public MetaJsonRecord toMetaJsonRecord() {
        MetaJsonRecord metaJsonRecord = new MetaJsonRecord();
        metaJsonRecord.setJsonModel("EquipmentTechnicalControl");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }

    private String getAttrs() {
        return "{\"deleted\": false," +
                " \"service\": {\"id\":"+service.getId()+", \"name\": \""+service.getName()+"\", \"$version\": 9}," +
                " \"location\": {\"id\":"+location.getId()+", \"name\": \""+location.getName()+"\", \"$version\": 1}," +
                " \"modality\": {\"id\":"+modality.getId()+", \"name\": \""+modality.getName()+"\", \"$version\": 2}," +
                " \"generalStatus\": \"À enregistrer\"," +
                " \"installationType\": {\"id\":"+installationType.getId()+", \"name\": \""+installationType.getName()+"\", \"$version\": 3}," +
                " \"medicalEquipment\": {\"id\":"+xRayGenerator.getId()+", \"name\": \""+xRayGenerator.getName()+"\", \"$version\": 2}," +
                " \"generalStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\"," +
                " \"medicalEquipmentId\":"+xRayGenerator.getId()+"," +
                " \"initialVerificationUnit\": \"Année\"," +
                " \"commissioningDate\": \""+commissioningDate+"\"," +
                " \"periodicVerificationUnit\": \"Année\"," +
                " \"initialVerificationStatus\": \"À enregistrer\"," +
                " \"initialVerificationRenewal\":\""+""+"\"," +
                " \"periodicVerificationStatus\": \"À enregistrer\"," +
                " \"initialVerificationStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\"," +
                " \"initialVerificationPeriodicity\": \"3\"," +
                " \"periodicVerificationStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\", " +
                "\"periodicVerificationPeriodicity\": \"1\"}";
    }
}
