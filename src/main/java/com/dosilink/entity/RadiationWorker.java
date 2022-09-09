package com.dosilink.entity;


import com.axelor.apps.base.db.Partner;
import com.axelor.apps.hr.db.Employee;
import com.axelor.apps.hr.db.EmploymentContract;
import com.axelor.apps.message.db.EmailAddress;
import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


public class RadiationWorker extends Model {

    private static final int  RADIATION_WORKER_SISERINumber = 10;
    private static final int  RADIATION_WORKER_exposureToIonizingRadiation = 8;
    private static final int  RADIATION_WORKER_magneticFieldExposures = 9;
    private static final int RADIOLOGICAL_CLASSIFICATION = 13;

    private Employee employee;
    private String name;
    private String defaultStatus = "À Enregistrer";

    private String ionizingRadiationStep;
    private String magneticFieldStep;
    private boolean exposureToIonizingRadiation;
    private boolean magneticFieldExposures;
    private MetaJsonRecord functionGrade;
    private MetaJsonRecord serviceAffectation;
    private MetaJsonRecord[] passiveDosimetry;
    private String firstName;
    private String fullName;
    private Number SISERINumber;
    private EmailAddress email;
    private LocalDate birthDate;
    private MetaJsonRecord sourceLocalisation;
    private MetaJsonRecord[] doseConstraint;
    private MetaJsonRecord[] individualEvaluation;
    private MetaJsonRecord workStation;
    private boolean minWorker;
    private Integer civility;
    private String activeAttribution = "Non concernée";
    private String passiveAttribution = "Non attribuée";
    private String patientRadiationProtectionTraining = "Non concerné";
    private String staffRadiationProtectionTraining = "Non concerné";
    private String activeDosimeterUseTraining = "Non concerné";
    private String magnetoprotectionTraining = "Non concerné";
    private String medicalDevicesTraining = "Non concerné";
    private String status = defaultStatus;
    private String trainingStatus = defaultStatus;
    private String medicalFollowUpStatus = defaultStatus;
    private String dosimetricFollowUpStatus = defaultStatus;
    private String oldDosimetricFollowUpStatus = defaultStatus;
    private Date estimatedDepartureDate;
    private String radiologicalClassification;
    private int step;
    private int firstStep;
    private String finished = "false";
    private boolean deleted = false;
    private Date pregnancyStartDate;
    private Date dateNotificationEmployer;
    private EmploymentContract contract;


    public RadiationWorker(Employee employee, Row row, MetaJsonRecord functionGrade, MetaJsonRecord serviceAffectation, Partner partner, MetaJsonRecord[] doseConstraint, MetaJsonRecord[] individualEvaluation, MetaJsonRecord[] passiveDosimetry) {
        this.employee = employee;
        this.functionGrade = functionGrade;
        this.serviceAffectation = serviceAffectation;
        this.email = partner.getEmailAddress();
        this.name = partner.getName();
        this.firstName = partner.getFirstName();
        this.fullName = partner.getFullName();
        this.birthDate = employee.getBirthDate();
        this.civility = partner.getTitleSelect();
        if (validRow(row, RADIATION_WORKER_SISERINumber)) {
            this.SISERINumber = row.getCell(RADIATION_WORKER_SISERINumber).getNumericCellValue();
        }
        if (validRowString(row, RADIATION_WORKER_exposureToIonizingRadiation) && validRow(row, RADIATION_WORKER_magneticFieldExposures)) {
            switch (row.getCell(RADIATION_WORKER_exposureToIonizingRadiation).getStringCellValue()) {
                case "Oui":
                    this.exposureToIonizingRadiation = true;
                    break;
                case "Non":
                    this.exposureToIonizingRadiation = false;
            }
            switch (row.getCell(RADIATION_WORKER_magneticFieldExposures).getStringCellValue()) {
                case "Oui":
                    this.magneticFieldExposures = true;
                    break;
                case "Non":
                    this.magneticFieldExposures = false;
            }

        }
        if(validRowString(row,RADIOLOGICAL_CLASSIFICATION)){
            switch (row.getCell(RADIOLOGICAL_CLASSIFICATION).getStringCellValue()){
                case "Non classé":
                    this.radiologicalClassification = "1";
                    break;
                case "B":
                    this.radiologicalClassification = "2";
                    break;
                case "A":
                    this.radiologicalClassification = "3";
            }
        }
        if(this.exposureToIonizingRadiation==true){
            this.step = 1;
            this.firstStep = 1;
        }else{
            this.step = 2;
            this.firstStep = 2;
        }
        this.minWorker = isMinWorker();
        this.contract = employee.getMainEmploymentContract();
        this.individualEvaluation = individualEvaluation;
        this.doseConstraint = doseConstraint;
        this.passiveDosimetry = passiveDosimetry;
    }
    private boolean isMinWorker(){
        LocalDateTime dateNow = LocalDateTime.now();
        int age = dateNow.getYear() - birthDate.getYear();
        if(age<18){
            this.minWorker = true;
        }
        return false;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {
    }

    public MetaJsonRecord toMetaJsonRecord() {
        System.out.println(this.name);
        MetaJsonRecord metaJsonRecord = new MetaJsonRecord(this.name);
        metaJsonRecord.setJsonModel("radiationWorker");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }

    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
    }

    private String getAttrs() {
        return " {\"name\": \"" + this.name + "\", "
                + "\"step\": \""+this.step+"\", "
                + " \"email\": \"" + this.email.getAddress() + "\", "
                + " \"status\": \"À Enregistrer\", "
                + "\"deleted\": \"" + this.deleted + "\", "
                + "\"civility\": " + this.civility + ", "
                + "\"radiologicalClassification\": " + this.radiologicalClassification + ", "
                + "\"birthDate\": \"" + this.employee.getBirthDate() + "\", "
                + "\"employee\": {\"id\": " + employee.getId() + ", \"name\": \"" + employee.getName() + "\"}, "
                + "\"finished\": \"" + this.finished + "\", "
                + "\"fullName\": \"" + this.fullName + "\", "
                + "\"firstName\": \"" + this.firstName + "\", "
                + "\"firstStep\": \""+this.firstStep+"\", "
                + "\"isEmployee\": true, "
                + "\"reinforced\": true, "
                + "\"minorWorker\": "+this.minWorker+ ", "
                + "\"SISERINumber\": \"" +this.SISERINumber + "\" "
                + ",\"functionGrade\": {\"id\": " + functionGrade.getId() + ", \"name\": \"" + functionGrade.getName() + "\"}, "
                + "\"contractNature\": {\"id\": " + contract.getId() + ", \"name\": \"" + contract.getContractType().getName() + "\"}, "
                + "\"doseConstraint\": [{\"id\": " + doseConstraint[0].getId() + "}, {\"id\": " + doseConstraint[1].getId() + "}, {\"id\": " + doseConstraint[2].getId() + "}], "
                + "\"frequentedArea\": \"1\", "
                + "\"trainingStatus\": \""+this.trainingStatus+"\", "
                + "\"medicalFollowUpStatus\": \""+this.medicalFollowUpStatus+"\", "
                + "\"dosimetricFollowUpStatus\": \""+this.dosimetricFollowUpStatus+"\", "
                + "\"oldDosimetricFollowUpStatus\": \""+this.oldDosimetricFollowUpStatus+"\", "
                + "\"statusLabel\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\","
                + "\"trainingStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\","
                + "\"medicalFollowUpStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\","
                + "\"dosimetricFollowUpStatusHtml\": \"<span class=\\\"label label-default\\\" style=\\\"background-color: #858585; margin: 2px 0 !important; display: inline-table; line-height: initial; width: 82px !important;\\\">À enregistrer</span>\","
                + "\"presenceType\":\""+""+"\" ,"
                + "\"weeklyDay\":\""+""+"\" ,"
                + "\"estimatedDepartureDate\": \""+""+"\" ,"
                + "\"endContractDate\": \"" + contract.getEndContractDetails() + "\", " 
                + "\"passiveDosimetry\": [{\"id\": " + passiveDosimetry[0].getId() + "}, {\"id\": " + passiveDosimetry[1].getId() + "}, {\"id\": " + passiveDosimetry[2].getId() + "}, {\"id\": " + passiveDosimetry[3].getId() + "}], " 
                + " \"activeAttribution\": \"Non concernée\", " 
                + "\"startContractDate\": \"" + contract.getStartDate() + "\", "
                + "\"endContractDate\": \"" + contract.getEndDate() + "\", "
                + "\"passiveAttribution\": \"Non attribuée\", " 
                + "\"serviceAffectation\": {\"id\": " + serviceAffectation.getId() + ", \"name\": \"" + serviceAffectation.getName() + "\"}, " 
                + "\"individualEvaluation\": [{\"id\": " + individualEvaluation[0].getId() + "}, {\"id\": " + individualEvaluation[1].getId() + "}], " 
                + "\"medicalFollowUpStatus\": \"À Enregistrer\", "
                + "\"magneticFieldExposures\": \"" + this.magneticFieldExposures + "\", " 
                + "\"medicalDevicesTraining\": \"Non concerné\", "
                + "\"magnetoprotectionTraining\": \"Non concerné\", " 
                + "\"activeDosimeterUseTraining\": \"Non concerné\", " 
                + "\"exposureToIonizingRadiation\":\"" + this.exposureToIonizingRadiation + "\", " 
                + "\"staffRadiationProtectionTraining\": \"Non concerné\", " 
                + "\"patientRadiationProtectionTraining\": \"Non concerné\""
                + "}";
    }
    private boolean validRowString(Row row, int NUM_COLUMN) {
        return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
    }
}
