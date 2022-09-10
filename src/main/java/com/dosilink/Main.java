package com.dosilink;

import com.dosilink.datasource.*;

public class Main {
  public void executeMain(String sourceFile) {
    try {
      System.out.println("******************clientStructure sheet*****************");
      ClientStructureXLS clientStructure = new ClientStructureXLS(sourceFile);
      clientStructure.saveStructureClientSheet();
      System.out.println("********************Training sheet**********************");
      TrainingXLS trainingXLS = new TrainingXLS(sourceFile);
      trainingXLS.saveTrainingSheet();
      System.out.println("*******************MeasuringDevice sheet****************");
      MeasuringDeviceXLS measuringDeviceXLS = new MeasuringDeviceXLS(sourceFile);
      measuringDeviceXLS.saveMeasuringDeviceSheet();
      System.out.println("*******************IRM sheet****************");
      IrmXLS irmXLS = new IrmXLS(sourceFile);
      irmXLS.saveIRMSheet();
      System.out.println("*******************EPI/EPC sheet****************");
      ProtectionEquipmentXLS protectionEquipmentXLS = new ProtectionEquipmentXLS(sourceFile);
      protectionEquipmentXLS.saveProtectionEquipment();
      System.out.println("*******************RadiationWorker sheet****************");
      RadiationWorkerXLS radiationWorker = new RadiationWorkerXLS(sourceFile);
      radiationWorker.saveRadiationWorkerSheet();
      System.out.println("*******************GeneratorRX sheet****************");
      XRayGeneratorXLS xRayGeneratorXLS = new XRayGeneratorXLS(sourceFile);
      xRayGeneratorXLS.saveGeneratorSheet();
      System.out.println("*******************End WITH SUCCESS****************");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
