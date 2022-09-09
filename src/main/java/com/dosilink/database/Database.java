package com.dosilink.database;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.repo.CompanyRepository;
import com.axelor.apps.base.db.repo.PartnerRepository;
import com.axelor.apps.hr.db.Employee;
import com.axelor.apps.hr.db.EmploymentContract;
import com.axelor.apps.hr.db.EmploymentContractType;
import com.axelor.apps.hr.db.repo.EmploymentContractTypeRepository;
import com.axelor.apps.message.db.EmailAddress;
import com.axelor.inject.Beans;
import com.axelor.meta.db.MetaJsonRecord;
import com.axelor.meta.db.repo.MetaJsonRecordRepository;
import com.dosilink.entity.*;
import org.apache.poi.ss.usermodel.Row;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {
    private static final int EMPLOYEE_CIVILITY = 0;
    private static final int EMPLOYEE_PRENOM = 1;
    private static final int EMPLOYEE_NAME = 2;
    private static final int EMPLOYEE_EMAIL = 3;
    private static final int EMPLOYEE_BIRTHDATE = 4;
    private static final int EMPLOYEE_TYPE_CONTRACT = 5;
    private static final int EMPLOYEE_DEBUT_CONTRACT = 6;
    private static final int EMPLOYEE_FIN_CONTRACT = 7;

    public MetaJsonRecord saveSite(Row row)  {
        Site site = new Site(row);
        return Beans.get(MetaJsonRecordRepository.class).save(site.toMetaJsonRecord());
    }

    public MetaJsonRecord saveService(Row row, MetaJsonRecord site)  {
        Service service = new Service(row, site);
        return Beans.get(MetaJsonRecordRepository.class).save(service.toMetaJsonRecord());
    }

    public MetaJsonRecord saveLocalisation(Row row, MetaJsonRecord service)  {
        Localisation localisation = new Localisation(row, service);
        return Beans.get(MetaJsonRecordRepository.class).save(localisation.toMetaJsonRecord());
    }

    public MetaJsonRecord saveInstallationType(Row row)  {
        InstallationType installationType = new InstallationType(row);
        return Beans.get(MetaJsonRecordRepository.class).save(installationType.toMetaJsonRecord());
    }


    public MetaJsonRecord saveMeasuringDeviceType(Row row)  {
        MeasuringDeviceType measuringDeviceType = new MeasuringDeviceType(row);
        return Beans.get(MetaJsonRecordRepository.class).save(measuringDeviceType.toMetaJsonRecord());
    }

    public MetaJsonRecord saveFunctionGrade(Row row) {
        FunctionGrade functionGrade = new FunctionGrade(row);
        return Beans.get(MetaJsonRecordRepository.class).save(functionGrade.toMetaJsonRecord());
    }

    public MetaJsonRecord saveBrandOrSupplier(Row row) {
        BrandOrSupplier brandOrSupplier = new BrandOrSupplier(row);
        return Beans.get(MetaJsonRecordRepository.class).save(brandOrSupplier.toMetaJsonRecord());
    }
    public MetaJsonRecord saveBrandOrSupplierEpi(Row row) {
        BrandOrSupplierEPI brandOrSupplierEPI = new BrandOrSupplierEPI(row);
        return Beans.get(MetaJsonRecordRepository.class).save(brandOrSupplierEPI.toMetaJsonRecord());
    }
    public MetaJsonRecord saveWorkerStation(Row row) {
        WorkStation workStation = new WorkStation(row);
        return Beans.get(MetaJsonRecordRepository.class).save(workStation.toMetaJsonRecord());
    }
    public MetaJsonRecord saveMeasuringDevice(Row row, MetaJsonRecord measuringDeviceType, MetaJsonRecord brand, MetaJsonRecord service, MetaJsonRecord location){
        MeasuringDevice measuringDevice = new MeasuringDevice(row,measuringDeviceType,brand,service,location);
        return Beans.get(MetaJsonRecordRepository.class).save(measuringDevice.toMetaJsonRecord());
    }
    public MetaJsonRecord saveIRM(Row row, MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord brand){
        IRM irm = new IRM(row, service, location, brand);
        return Beans.get(MetaJsonRecordRepository.class).save(irm.toMetaJsonRecord());
    }
    public MetaJsonRecord saveProtectionEquipment(Row row, MetaJsonRecord brand, MetaJsonRecord service , MetaJsonRecord location){
        ProtectionEquipment protectionEquipment = new ProtectionEquipment(row, brand, service, location);
        return Beans.get(MetaJsonRecordRepository.class).save(protectionEquipment.toMetaJsonRecord());
    }
    public List<MetaJsonRecord> findMeasuringDeviceTypes(){
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository.all("MeasuringDeviceType").filter("self.attrs.deleted = 'false'").fetch();
    }
    public List<MetaJsonRecord> findTrainingTypes() {
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository
                .all("trainingType")
                .filter("self.attrs.deleted = 'false'")
                .fetch();
    }
    public List<MetaJsonRecord> findInstallationTypes() {
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository.all("InstallationType").filter("self.attrs.deleted = 'false'").fetch();
    }
    public MetaJsonRecord saveQualityControl(Row row, MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord modality, MetaJsonRecord xRayGenerator,MetaJsonRecord installationType){
        QualityControl qualityControl = new QualityControl(row,service,location,modality,xRayGenerator,installationType);
        return Beans.get(MetaJsonRecordRepository.class).save(qualityControl.toMetaJsonRecord());
    }
    public MetaJsonRecord saveEquipmentTechnicalControl(Row row, MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord modality, MetaJsonRecord installationType, MetaJsonRecord xRayGenerator){
        EquipmentTechnicalControl equipmentTechnicalControl = new EquipmentTechnicalControl(row, service, location, modality, installationType, xRayGenerator);
        return Beans.get(MetaJsonRecordRepository.class).save(equipmentTechnicalControl.toMetaJsonRecord());
    }
    public List<MetaJsonRecord> findModality(){
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return  metaJsonRecordRepository.all("Modality").filter("self.attrs.deleted = 'false'").fetch();
    }
    public List<MetaJsonRecord> findBrand(){
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository.all("Brand").filter("self.attrs.deleted = 'false'").fetch();
    }
    public List<MetaJsonRecord> findLocations(){
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository.all("location").filter("self.attrs.deleted = 'false'").fetch();
    }
    public List<MetaJsonRecord> saveDoseConstraint() {
        DoseConstraint doseConstraint = new DoseConstraint("Corps entier");
        DoseConstraint doseConstraint1 = new DoseConstraint("Extrémités");
        DoseConstraint doseConstraint2 = new DoseConstraint("Cristallin");
        MetaJsonRecord metaDoseConstraint = Beans.get(MetaJsonRecordRepository.class).save(doseConstraint.toMetaJsonRecord());
        MetaJsonRecord metaDoseConstraint1 = Beans.get(MetaJsonRecordRepository.class).save(doseConstraint1.toMetaJsonRecord());
        MetaJsonRecord metaDoseConstraint2 = Beans.get(MetaJsonRecordRepository.class).save(doseConstraint2.toMetaJsonRecord());
        List<MetaJsonRecord> doseConstraints = new ArrayList<>();
        doseConstraints.add(metaDoseConstraint);
        doseConstraints.add(metaDoseConstraint1);
        doseConstraints.add(metaDoseConstraint2);
        return  doseConstraints;
    }
    public List<MetaJsonRecord> savePassiveDosimetry() {
        PassiveDosimetry passiveDosimetry = new PassiveDosimetry("Poitrine");
        PassiveDosimetry passiveDosimetry1 = new PassiveDosimetry("Bague");
        PassiveDosimetry passiveDosimetry2 = new PassiveDosimetry("Poignet");
        PassiveDosimetry passiveDosimetry3 = new PassiveDosimetry("Cristallin");
        MetaJsonRecord metaPassiveDosimetry = Beans.get(MetaJsonRecordRepository.class).save(passiveDosimetry.toMetaJsonRecord());
        MetaJsonRecord metaPassiveDosimetry1 = Beans.get(MetaJsonRecordRepository.class).save(passiveDosimetry1.toMetaJsonRecord());
        MetaJsonRecord metaPassiveDosimetry2 = Beans.get(MetaJsonRecordRepository.class).save(passiveDosimetry2.toMetaJsonRecord());
        MetaJsonRecord metaPassiveDosimetry3 = Beans.get(MetaJsonRecordRepository.class).save(passiveDosimetry3.toMetaJsonRecord());
        List<MetaJsonRecord> passiveDosimetries = new ArrayList<>();
        passiveDosimetries.add(metaPassiveDosimetry);
        passiveDosimetries.add(metaPassiveDosimetry1);
        passiveDosimetries.add(metaPassiveDosimetry2);
        passiveDosimetries.add(metaPassiveDosimetry3);
        return  passiveDosimetries;
    }
    public List<MetaJsonRecord> saveIndividualEvaluation(){
        IndividualEvaluation individualEvaluation = new IndividualEvaluation("Avec EPI/EPC");
        IndividualEvaluation individualEvaluation1 = new IndividualEvaluation("Sans EPI/EPC");
        MetaJsonRecord metaIndividualEvaluation = Beans.get(MetaJsonRecordRepository.class).save(individualEvaluation.toMetaJsonRecord());
        MetaJsonRecord metaIndividualEvaluation1 = Beans.get(MetaJsonRecordRepository.class).save(individualEvaluation1.toMetaJsonRecord());
        List<MetaJsonRecord> individualEvaluations = new ArrayList<>();
        individualEvaluations.add(metaIndividualEvaluation);
        individualEvaluations.add(metaIndividualEvaluation1);
        return individualEvaluations;
    }
    public List<MetaJsonRecord> findWorkerStation() {
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return  metaJsonRecordRepository.all("workerStation").filter("self.attrs.deleted = 'false'").fetch();
    }
    public List<MetaJsonRecord> findFunctionGrade() {
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository
                .all("functionGrade")
                .filter("self.attrs.deleted = 'false'")
                .fetch();
    }
    public List<MetaJsonRecord> findService() {
        MetaJsonRecordRepository metaJsonRecordRepository = Beans.get(MetaJsonRecordRepository.class);
        return metaJsonRecordRepository
                .all("serviceAffectation")
                .filter("self.attrs.deleted = 'false'")
                .fetch();
    }

    public MetaJsonRecord saveTraining(Row row, MetaJsonRecord trainingType, MetaJsonRecord functionGrade) {
        Training training = new Training(row, trainingType, functionGrade);
        return Beans.get(MetaJsonRecordRepository.class).save(training.toMetaJsonRecord());
    }
    public MetaJsonRecord saveGenerator(Row row, MetaJsonRecord modality, MetaJsonRecord service, MetaJsonRecord location, MetaJsonRecord brand, MetaJsonRecord workstations, MetaJsonRecord installationType){
        XRayGenerator xRayGenerator = new XRayGenerator(row, modality, service, location, brand, workstations, installationType);
        return Beans.get(MetaJsonRecordRepository.class).save(xRayGenerator.toMetaJsonRecord());
    }
    public Partner createPartner(Row row){
        Partner partner = new Partner();
        partner.setPartnerTypeSelect(2);
        partner.setIsContact(true);
        if(validRow(row,EMPLOYEE_CIVILITY)){
        switch (row.getCell(EMPLOYEE_CIVILITY).getStringCellValue()){
            case "M.":
                partner.setTitleSelect(1);
                break;
            case "Mme":
                partner.setTitleSelect(2);
                break;
            case "Dr":
                partner.setTitleSelect(3);
                break;
            case "Prof.":
                partner.setTitleSelect(4);
                break;
        }}
        if(validRow(row,EMPLOYEE_NAME)) {
            partner.setName(row.getCell(EMPLOYEE_NAME).getStringCellValue());
            partner.setFullName(row.getCell(EMPLOYEE_NAME).getStringCellValue());
        }
        if(validRow(row,EMPLOYEE_PRENOM)) {
            partner.setFirstName(row.getCell(EMPLOYEE_PRENOM).getStringCellValue());
        }
        if (validRow(row,EMPLOYEE_EMAIL)) {
            EmailAddress emailAddress = new EmailAddress(row.getCell(EMPLOYEE_EMAIL).getStringCellValue());
            partner.setEmailAddress(emailAddress);
        }
        PartnerRepository partnerRepository = Beans.get(PartnerRepository.class);
        partnerRepository.save(partner);

        return partner;
    }
    private EmploymentContractType findTypeContract(Row row){
        EmploymentContractTypeRepository employmentContractTypeRepositoryRepository = Beans.get(EmploymentContractTypeRepository.class);
        if (validRow(row,EMPLOYEE_TYPE_CONTRACT)) {
            EmploymentContractType employmentContract = employmentContractTypeRepositoryRepository.findByName(row.getCell(EMPLOYEE_TYPE_CONTRACT).getStringCellValue());
            return employmentContract;
        }
        return null;
    }
    private EmploymentContract createEmploymentContract(Row row){
        CompanyRepository companyRepository = Beans.get(CompanyRepository.class);
        Company company = companyRepository.findByName("BIOMEDIQA");
        EmploymentContract employmentContract = new EmploymentContract();
        employmentContract.setPayCompany(company);
        employmentContract.setContractType(findTypeContract(row));
        if(validRow(row,EMPLOYEE_DEBUT_CONTRACT)) {
            Date startDate = row.getCell(EMPLOYEE_DEBUT_CONTRACT).getDateCellValue();
            LocalDate dateDebut = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            employmentContract.setStartDate(dateDebut);
        }
        if(validRow(row,EMPLOYEE_FIN_CONTRACT)){
            Date endDate = row.getCell(EMPLOYEE_FIN_CONTRACT).getDateCellValue();
            LocalDate dateFin = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            employmentContract.setEndDate(dateFin);
        }else{
            employmentContract.setEndDate(null);
        }
        return employmentContract;
    }

    public Employee createEmployee(Row row ,MetaJsonRecord functionGrade,MetaJsonRecord service){
        Employee employee = new Employee();
        employee.setContactPartner(createPartner(row));
        if(validRow(row,EMPLOYEE_NAME)) {
            employee.setName(row.getCell(EMPLOYEE_NAME).getStringCellValue());
        }
        if(validRow(row,EMPLOYEE_BIRTHDATE)) {
            Date birthDate = row.getCell(EMPLOYEE_BIRTHDATE).getDateCellValue();
            LocalDate date = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            employee.setBirthDate(date);
        }
        employee.addEmploymentContractListItem(createEmploymentContract(row));
        employee.setMainEmploymentContract(createEmploymentContract(row));
        employee.setAttrs(" {\"deleted\": false, \"employeeService\": {\"id\":\""+ service.getId()+"\", \"name\": \""+service.getName()+"\", \"$version\": 0},\"employeeFunction\": {\"id\":\""+functionGrade.getId()+"\", \"name\":\""+functionGrade.getName()+"\", \"$version\": 0}, \"serviceAffectation\": {\"id\":\""+service.getId()+"\", \"name\": \""+service.getName()+"\"}}");
        return employee;
    }
    public void saveEmployee(Employee employee) {
        com.axelor.apps.hr.db.repo.EmployeeRepository employeeRepository = Beans.get(com.axelor.apps.hr.db.repo.EmployeeRepository.class);
           employeeRepository.save(employee);
    }

    public MetaJsonRecord saveRadiationWorker(Row row, MetaJsonRecord functionGrade, MetaJsonRecord service, Employee employee, Partner partner, MetaJsonRecord [] doseConstraint, MetaJsonRecord [] individualEvaluation, MetaJsonRecord [] passiveDosimetry)
    {
        RadiationWorker radiationWorker = new RadiationWorker(employee,row,functionGrade,service,partner,doseConstraint,individualEvaluation,passiveDosimetry);
        return Beans.get(MetaJsonRecordRepository.class).save(radiationWorker.toMetaJsonRecord());
    }
    private boolean validRow(Row row, int NUM_COLUMN) {
        return row.getCell(NUM_COLUMN) != null;
    }
}
