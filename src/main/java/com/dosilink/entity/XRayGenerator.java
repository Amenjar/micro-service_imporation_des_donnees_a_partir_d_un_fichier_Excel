package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class XRayGenerator extends Model {
  private static final int MODEL = 4;
  private static final int TECHNOLOGY = 8;

  private static final int SERIAL_NUMBER = 5;
  private static final int MACHINE_STATE = 6;
  private static final int COMMOSSION_ING_DATE = 10;
  private MetaJsonRecord modality ;
  private MetaJsonRecord service;
  private MetaJsonRecord location;
  private MetaJsonRecord brand;
  private String model;
  private Number serialNumber;
  private String machineState;
  private MetaJsonRecord workstations;
  private String technology;
  private MetaJsonRecord installationType;
  private LocalDate commissioningDate;
  private String listSteps;



  public XRayGenerator(Row row, MetaJsonRecord modality, MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord brand, MetaJsonRecord workstations, MetaJsonRecord installationType) {
    if(validRowString(row,MODEL)){
      this.model = row.getCell(MODEL).getStringCellValue();
    }
    if(validRow(row,SERIAL_NUMBER)){
      this.serialNumber = row.getCell(SERIAL_NUMBER).getNumericCellValue();
    }
    if(validRowString(row,MACHINE_STATE)){
      this.machineState = row.getCell(MACHINE_STATE).getStringCellValue();
    }
    if(validRowString(row,TECHNOLOGY)){
      switch (row.getCell(TECHNOLOGY).getStringCellValue()){
        case "Numérique directe":
          this.technology = "ND";
          break;
        case "Numérique indirecte":
          this.technology = "NID";
          break;
        case "Analogique":
          this.technology = "Analog";
      }
    }
    if(validRow(row,COMMOSSION_ING_DATE)){
      this.commissioningDate = row.getCell(COMMOSSION_ING_DATE).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    this.modality = modality;
    this.installationType = installationType;
    if(modality.getName() != "Accélérateur de particules" && modality.getName() != "Imageur RX de centrage" && modality.getName() != "Scanner de radiothérapie" && modality.getName() != "Arceau mobile" && modality.getName() != "Cône Beam" && modality.getName() != "Installation de Radiologie Rétroalvéolaire" && modality.getName() != "Lithotripteur" && modality.getName() != "Mini C-Arm" && modality.getName() != "Mobile de graphie" && modality.getName() != "Panoramique dentaire" && modality.getName() != "Salle d’Os" && modality.getName() != "Salle d’os + Télécrâne" && modality.getName() != "Salle interventionnelle fixe biplan" && modality.getName() != "Salle interventionnelle fixe monoplan" && modality.getName() != "Scanner bi-tube" && modality.getName() != "Scanner monotube" && modality.getName() != "Table télécommandée" && modality.getName() != "Table télécommandée + suspension"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage";
    }else if(modality.getName() == "Médecine nucléaire"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage";
    }else if(modality.getName() == "Radiothérapie" && installationType.getName() == "Accélérateur de particules"){
      this.listSteps = "RadiotherapyAccelerator,AcceleratorGenerator,XRTube,DigitalImage";
    }else if(modality.getName() == "Radiothérapie" && installationType.getName() == "Imageur RX de centrage" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiothérapie" && installationType.getName() == "Imageur RX de centrage" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiothérapie" && installationType.getName() == "Imageur RX de centrage" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiothérapie" && installationType.getName() == "Scanner dosimétrique"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Table télécommandée" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Salle d’Os" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Salle d’os + Télécrâne" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Mobile de graphie" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Table télécommandée + suspension" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Table télécommandée" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,ImageIntensif,AntiDiffusingGrid,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Salle d’Os" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Salle d’os + Télécrâne" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Mobile de graphie" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Table télécommandée + suspension" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Table télécommandée" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,ImageIntensif,AntiDiffusingGrid,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Salle d’Os" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Salle d’os + Télécrâne" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Mobile de graphie" && technology == "Analog") {
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiologie Conventionnelle" && installationType.getName() == "Table télécommandée + suspension" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiologie interventionnelle" && installationType == null){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie interventionnelle" && installationType != null && installationType.getName() == "Arceau mobile"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie interventionnelle" && installationType != null && installationType.getName() == "Lithotripteur"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie interventionnelle" && installationType != null && installationType.getName() == "Mini C-Arm"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie interventionnelle" && installationType != null && installationType.getName() == "Salle interventionnelle fixe biplan"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie interventionnelle" && installationType != null && installationType.getName() == "Salle interventionnelle fixe monoplan"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType == null){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Cône Beam" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Installation de Radiologie Rétroalvéolaire" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Panoramique dentaire" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,AntiDiffusingGrid,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Cône Beam" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Installation de Radiologie Rétroalvéolaire" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Panoramique dentaire" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Cône Beam" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Installation de Radiologie Rétroalvéolaire" && technology == "Analog"){
      this.listSteps = "ProductionDevice', 'XRGenerator', 'XRTube', 'AntiDiffusingGrid', 'DevelopingMachine', 'UsedFilms', 'RegisterOperations";
    }else if(modality.getName() == "Radiologie dentaire" && installationType != null && installationType.getName() == "Panoramique dentaire" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,AntiDiffusingGrid,DevelopingMachine,UsedFilms,RegisterOperations";
    }else if(modality.getName() == "Mammographie" && technology == "ND"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,DigitalImage,UsedFilms,DiagnosticConsoles,Negatoscopes,Reprographers,RegisterOperations";
    }else if(modality.getName() == "Mammographie" && technology == "NID"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,UsedFilms,ERLMPlate,DiagnosticConsoles,Negatoscopes,Reprographers,ERLMPlate,IndirectPlateReadingSystem,RegisterOperations";
    }else if(modality.getName() == "Mammographie" && technology == "Analog"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube,UsedFilms,DiagnosticConsoles,Negatoscopes,Reprographers,Cassette,CassetteScreens,UsedChemistry,RegisterOperations";
    }else if(modality.getName() == "Scanner"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube";
    }else if(modality.getName() == "Ostéodensitométrie"){
      this.listSteps = "ProductionDevice,XRGenerator,XRTube";
    }
    this.service = service;
    this.location = location;
    this.brand = brand;
    this.workstations = workstations;
  }


  private boolean validRow(Row row, int NUM_COLUMN) {
    return row.getCell(NUM_COLUMN) != null;
  }
  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(Long id) {}

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(model);
    metaJsonRecord.setJsonModel("Equipment");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }
  private String getAttrs() {
    return " {\"name\": \""+model+"\","+
            " \"step\": \""+""+"\"," +
            " \"brand\": {\"id\":"+brand.getId()+", \"name\": \""+brand.getName()+"\", \"$version\": 3}," +
            " \"model\": \""+model+"\"," +
            " \"service\": {\"id\":\""+service.getId()+"\", \"name\": \""+service.getName()+"\", \"$version\": 8}," +
            " \"brandXRG\": {\"id\":"+brand.getId()+", \"name\": \""+brand.getName()+"\", \"$version\": 1}," +
            " \"isDirect\": false," +
            " \"isScreen\": false," +
            " \"location\": {\"id\":\""+location.getId()+"\", \"name\": \""+location.getName()+"\", \"$version\": 1, \"$attachments\": 0}," +
            " \"modality\": {\"id\":\""+modality.getId()+"\", \"name\": \""+modality.getName()+"\", \"$version\": 2}," +
            " \"modelXRG\": \""+model+"\"," +
            " \"nextStep\": \""+""+"\"," +
            " \"ASNNumber\":\""+serialNumber+"\"," +
            " \"ASNregime\": \"Autorisation\"," +
            " \"isCasette\": false," +
            " \"isConsole\": false," +
            " \"listSteps\": \""+listSteps+"\"," +
            " \"isAntiGrid\": false," +
            " \"isIndirect\": false," +
            " \"isReciever\": false," +
            " \"isRegister\": true," +
            " \"statusHtml\": \"<span class=\\\"label label-success\\\" style=\\\"display: inline-table; line-height: initial; width: 65px !important;\\\" x-translate>Saisi</span>\"," +
            " \"technology\": \""+technology+"\"," +
            " \"CODEPNumber\": \""+serialNumber+"\"," +
            " \"isUsedFilms\": false," +
            " \"ASNDocuments\": [{\"id\": 28027}]," +
            " \"isReprograph\": false," +
            " \"machineState\": \""+machineState+"\"," +
            " \"serialNumber\": \""+serialNumber+"\"," +
            " \"workstations\": [{\"id\":"+workstations.getId()+"}]," +
            " \"isDevelopping\": false," +
            " \"isIntensifier\": false," +
            " \"isNegatoscope\": false," +
            " \"iSUsedChemisty\": false," +
            " \"qualityConrolId\": 27932," +
            " \"installationType\": {\"id\":\""+installationType.getId()+"\", \"name\": \""+installationType.getName()+"\", \"$version\": 1}," +
            " \"commissioningDate\": \""+commissioningDate+"\"," +
            " \"informationStatus\": \"⬤\"," +
            " \"serialNumberSystemXRG\": \"tt\"," +
            " \"retrievedIdentification\": {\"id\": 284, \"version\": 0, \"fileName\": \"Dositrace_brochure.pdf\", \"filePath\": \"Dositrace_brochure.pdf\", \"fileSize\": 1557721, \"fileType\": \"application/pdf\", \"importId\": null, \"selected\": false, \"sizeText\": \"1.56 MB\", \"createdBy\": {\"id\": 1, \"code\": \"admin\", \"$version\": 77, \"fullName\": \"Admin\"}, \"createdOn\": \"2022-04-15T08:05:57.681Z\", \"updatedBy\": null, \"updatedOn\": null, \"description\": null, \"importOrigin\": null}," +
            " \"equipmentTechnicalControlId\": 27933," +
            " \"retrievedIdentificationSheet\": \"false\"}";
  }
  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }
}
