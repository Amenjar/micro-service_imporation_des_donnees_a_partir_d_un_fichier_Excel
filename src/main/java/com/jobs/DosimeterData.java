package com.jobs;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC;
import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING;

import com.axelor.app.AppSettings;
import com.axelor.db.tenants.TenantResolver;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wslite.json.JSONArray;
import wslite.json.JSONException;
import wslite.json.JSONObject;

public class DosimeterData {

  public static final int BODY_COLUMN = 2;

  public static final int EXTREMITY_COLUMN = 3;

  public static final int CRYSTALLINE_COLUMN = 4;

  public static final int NAME_COLUMN = 1;

  public static final int BODY = 0;

  public static final int WRIST = 2;

  public static final int CRYSTALLINE = 3;

  public static final int DATA_FIRST_ROW = 4;

  public static final String EMPTY_CELL_CONTENT = "-";

  private final EntityManager em;

  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public DosimeterData(EntityManager em) {
    this.em = em;
  }

  public List<InsertStatus> run(String fileName) throws IOException {
    logger.info("Importing dosimeter data from file {}", fileName);
    List<InsertStatus> statuses = new ArrayList<>();
    Sheet sheet = readFile(fileName, statuses);
    if (!statuses.isEmpty()) {
      logger.warn("Import can not be performed.");
      return statuses;
    }

    List<RadiationWorkerDosimetry> workerDosimetryData = parseData(sheet);
    updatePassiveDosimetry(workerDosimetryData);
    List<InsertStatus> results = getStatuses(workerDosimetryData);
    logger.info("{} workers have been updated.", results.size());
    return results;
  }

  private List<InsertStatus> getStatuses(List<RadiationWorkerDosimetry> workerDosimetryData) {
    return workerDosimetryData.stream()
        .map(dosimetry -> dosimetry.status)
        .collect(Collectors.toList());
  }

  private List<RadiationWorkerDosimetry> parseData(Sheet sheet) {
    List<RadiationWorkerDosimetry> workerDosimetry = new ArrayList<>();
    for (Row row : sheet) {
      if (row.getRowNum() < DATA_FIRST_ROW) {
        continue;
      }
      RadiationWorkerDosimetry radiationWorkerDosimetry = new RadiationWorkerDosimetry();
      workerDosimetry.add(radiationWorkerDosimetry);
      try {
        findDosimetersByRadiationWorkerFullName(
            row.getCell(NAME_COLUMN).getStringCellValue(), radiationWorkerDosimetry);
      } catch (NoResultException e) {
        radiationWorkerDosimetry.updateStatus(
            "Radiation worker is not found.", 1 + row.getRowNum());
        logger.error("Radiation worker at row {} is not found .", 1 + row.getRowNum());
        continue;
      } catch (Exception e) {
        radiationWorkerDosimetry.updateStatus(
            "Impossible to update dosimeter for radiation worker.", 1 + row.getRowNum());
        logger.error(
            "Impossible to update dosimeter for radiation worker at row {}.", 1 + row.getRowNum());
        e.printStackTrace();
        continue;
      }
      radiationWorkerDosimetry.setDoseValues(
          row.getCell(BODY_COLUMN), row.getCell(EXTREMITY_COLUMN), row.getCell(CRYSTALLINE_COLUMN));
    }
    return workerDosimetry;
  }

  private Sheet readFile(String fileName, List<InsertStatus> statuses) throws IOException {
    String dataFolder = AppSettings.get().get("file.upload.dir");
    String tenant = TenantResolver.currentTenantIdentifier();
    FileInputStream fis = new FileInputStream(dataFolder + "/" + tenant + "/" + fileName);
    logger.info("Reading data from {}", dataFolder + "/" + tenant + "/" + fileName);
    Sheet sheet = new HSSFWorkbook(fis).getSheetAt(0);
    if (sheet.getRow(1) == null
        || sheet.getRow(1).getCell(1) == null
        || sheet.getRow(1).getCell(1).getCellType() != CELL_TYPE_STRING
        || !sheet.getRow(1).getCell(1).getStringCellValue().equals("RÉSULTATS DOSIMÉTRIQUES")
        || sheet.getRow(1).getLastCellNum() != 7) {
      InsertStatus fileIssue = new InsertStatus();
      fileIssue.addReason("File format is not recognized");
      statuses.add(fileIssue);
      logger.error("File {} format is not recognized.", fileName);
    }
    return sheet;
  }

  public void findDosimetersByRadiationWorkerFullName(
      String name, RadiationWorkerDosimetry radiationWorker) throws JSONException {
    Object[] selectResult =
        (Object[])
            em.createNativeQuery(
                    "select id, attrs->>'passiveDosimetry'  "
                        + "from meta_json_record "
                        + "where :name like concat('%', name, '%') "
                        + "and :name like concat('%', attrs ->> 'firstName', '%') "
                        + "and json_model = 'radiationWorker'")
                .setParameter("name", name)
                .getSingleResult();

    radiationWorker.setDosimetry((BigInteger) selectResult[0], (String) selectResult[1]);
  }

  private void updatePassiveDosimetry(List<RadiationWorkerDosimetry> radiationWorkerDosimetryList) {
    logger.info("Updating workers passive dosimetry.");
    for (RadiationWorkerDosimetry dosimetry : radiationWorkerDosimetryList) {
      if (!dosimetry.status.fine) {
        continue;
      }
      List<String> errors = new ArrayList<>();
      updateDose(dosimetry.getBodyDoseId(), dosimetry.getBodyDose(), errors);
      logger.info("Passive dosimeter with id: {} has been updated", dosimetry.getBodyDoseId());
      updateDose(dosimetry.getExtremityDoseId(), dosimetry.getExtremityDose(), errors);
      logger.info("Passive dosimeter with id: {} has been updated", dosimetry.getExtremityDoseId());
      updateDose(dosimetry.getCrystallineDoseId(), dosimetry.getCrystallineDose(), errors);
      logger.info(
          "Passive dosimeter with id: {} has been updated", dosimetry.getCrystallineDoseId());
      if (!errors.isEmpty()) {
        dosimetry.status.fine = false;
        dosimetry.status.addReasons(errors);
        logger.warn(
            "{} errors occurred when trying to update worker with id {}.",
            errors.size(),
            dosimetry.radiationWorkerId);
      }
    }
    logger.info("{} Workers passive dosimetry updated.", radiationWorkerDosimetryList.size());
  }

  private void updateDose(long id, Double dose, List<String> errors) {
    if (dose == null) {
      return;
    }
    try {
      em.createQuery(
              "update MetaJsonRecord set attrs=jsonb_set(attrs, '{dose}', cast(:dose as json)) where id = :id")
          .setParameter("id", id)
          .setParameter("dose", dose.toString())
          .executeUpdate();
    } catch (Exception e) {
      logger.error("Unable to set dose {} to dosimeter with id {}", dose, id);
      errors.add(String.format("Unable to set dose %s to dosimeter with id %s", dose, id));
    }
  }

  private List<Long> getFromJsonString(String attrs) throws JSONException {
    return new JSONArray(attrs)
        .stream().mapToLong(this::getIdFromJson).boxed().collect(Collectors.toList());
  }

  private Long getIdFromJson(Object idObject) {
    try {
      return ((JSONObject) idObject).getLong("id");
    } catch (JSONException e) {
      logger.error("Can't get passive dosimeter ids");
      e.printStackTrace();
      return -1L;
    }
  }

  public class RadiationWorkerDosimetry {
    long radiationWorkerId;

    Long bodyDoseId;

    Double bodyDose;

    Long extremityDoseId;

    Double extremityDose;

    Long crystallineDoseId;

    Double crystallineDose;

    private final InsertStatus status = new InsertStatus();

    RadiationWorkerDosimetry() {
      status.fine = true;
    }

    public void setDoseValues(Cell body, Cell extremity, Cell crystalline) {
      status.rowIndex = body.getRowIndex() + 1;
      setBodyDose(body);
      setExtremityDose(extremity);
      setCrystallineDose(crystalline);
    }

    private void setBodyDose(Cell cell) {
      if (!isCellValide(cell, "body")) {
        return;
      }
      bodyDose = cell.getNumericCellValue();
    }

    private void setExtremityDose(Cell cell) {
      if (!isCellValide(cell, "extremity")) {
        return;
      }
      extremityDose = cell.getNumericCellValue();
    }

    private void setCrystallineDose(Cell cell) {
      if (!isCellValide(cell, "crystalline")) {
        return;
      }
      crystallineDose = cell.getNumericCellValue();
    }

    private boolean isCellValide(Cell cell, String doseType) {
      if (cell.getCellType() == CELL_TYPE_STRING
          && cell.getStringCellValue().equals(EMPTY_CELL_CONTENT)) {
        return false;
      }
      if (cell.getCellType() != CELL_TYPE_NUMERIC) {
        logger.warn(
            "Dose {} value {} at line {} is incorrect",
            doseType,
            cell.getStringCellValue(),
            1 + cell.getRowIndex());
        status.addReason(String.format("Impossible to read %s dose.", doseType));
        status.fine = false;
        return false;
      }
      return true;
    }

    public Double getBodyDose() {
      return bodyDose;
    }

    public Double getExtremityDose() {
      return extremityDose;
    }

    public Double getCrystallineDose() {
      return crystallineDose;
    }

    public Long getBodyDoseId() {
      return bodyDoseId;
    }

    public Long getExtremityDoseId() {
      return extremityDoseId;
    }

    public Long getCrystallineDoseId() {
      return crystallineDoseId;
    }

    public void setDosimetry(BigInteger radiationWorkerId, String doseIds) throws JSONException {
      this.radiationWorkerId = radiationWorkerId.longValue();
      List<Long> dosimeterList = getFromJsonString(doseIds);
      bodyDoseId = dosimeterList.get(BODY);
      extremityDoseId = dosimeterList.get(WRIST);
      crystallineDoseId = dosimeterList.get(CRYSTALLINE);
    }

    public void updateStatus(String error, Integer rowIndex) {
      status.rowIndex = rowIndex;
      status.fine = false;
      status.addReason(error);
    }
  }

  public static class InsertStatus {
    Integer rowIndex;

    boolean fine;

    List<String> reasons = new ArrayList<>();

    public void addReason(String reason) {
      reasons.add(reason);
    }

    public InsertStatus() {}

    public Integer getRowIndex() {
      return rowIndex;
    }

    public boolean getFine() {
      return fine;
    }

    public List<String> getReasons() {
      return this.reasons;
    }

    public void addReasons(List<String> reasons) {
      this.reasons.addAll(reasons);
    }
  }
}
