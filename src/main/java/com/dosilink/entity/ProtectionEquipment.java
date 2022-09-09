package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;

public class ProtectionEquipment extends Model {
    private static final int TYPE = 0;
    private static final int DISPOSITIF = 1;
    private static final int SERIAL_NUMBER = 3;
    private static final int EPAISSEUR = 6;
    private static final int ETAT = 7;
    private static final int DATE_DERNIER_VERIFICATION = 8;
    private static final int DATE_AQUISITION = 9;
    private String type;
    private String dispositif;
    private MetaJsonRecord brand;
    private Number serialNumber;
    private MetaJsonRecord service;
    private MetaJsonRecord location;
    private Number epaisseur;
    private String etat;
    private LocalDate dateDernierVerfication;
    private LocalDate dateAquisition;

    public ProtectionEquipment(Row row, MetaJsonRecord brand, MetaJsonRecord service , MetaJsonRecord location) {
        if (validRow(row,TYPE)){
            switch (row.getCell(TYPE).getStringCellValue()){
                case "EPI":
                    this.type = "1";
                    break;
                case "EPC":
                    this.type = "2";
            }

        }
        if (validRow(row,DISPOSITIF)){
            this.dispositif = row.getCell(DISPOSITIF).getStringCellValue();
        }
        if (validRow(row,SERIAL_NUMBER)){
            this.serialNumber = row.getCell(SERIAL_NUMBER).getNumericCellValue();
        }
        if (validRow(row,EPAISSEUR)){
            this.epaisseur = row.getCell(EPAISSEUR).getNumericCellValue();
        }
        if (validRow(row,ETAT)){
            this.etat = row.getCell(ETAT).getStringCellValue();
        }
        if (validRow(row,DATE_DERNIER_VERIFICATION)){
            this.dateDernierVerfication = row.getCell(DATE_DERNIER_VERIFICATION).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        if (validRow(row,DATE_AQUISITION)){
            this.dateAquisition = row.getCell(DATE_AQUISITION).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        this.brand = brand;
        this.service = service;
        this.location = location;
    }
    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
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
        metaJsonRecord.setJsonModel("ProtectionEquipment");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }

    private String getAttrs() {
        return " {\"step\": \"1\"," +
                " \"unit\": \"Année\"," +
                " \"brand\": {\"id\":"+brand.getId()+", \"name\": \""+brand.getName()+"\", \"$version\": 0, \"$attachments\": 0}," +
                " \"device\": \"5\"," +
                " \"deleted\": \"false\"," +
                " \"service\": {\"id\":"+service.getId()+", \"name\": \""+service.getName()+"\", \"$version\": 0}," +
                " \"category\": \"1\"," +
                " \"location\": {\"id\":"+location.getId()+", \"name\":\""+location.getName()+"\"}," +
                "\"serialNumber\": " + "\"" +serialNumber + "\"" + "," +
                " \"equipmentType\": \""+type+"\"," +
                " \"generalStatus\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\"," +
                " \"leadThickness\": \""+epaisseur+"\"," +
                " \"stateEquipment\": \""+etat+"\", " +
                " \"initialVerificationDate\":\""+dateDernierVerfication+"\", " +
                "\"yearAcquisitionPPE\": \""+dateAquisition+"\"}";
    }
}
