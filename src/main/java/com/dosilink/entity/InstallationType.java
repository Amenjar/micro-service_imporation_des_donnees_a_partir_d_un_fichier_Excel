package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

public class InstallationType extends Model {
  private static final int INSTALLATION_TYPE_NAME = 8;
  // private static final int INSTALLATION_TYPE_MODALITY = 11;
  // private static final int INSTALLATION_TYPE_QUALITY_PROCESS = 12;
  // private static final int INSTALLATION_TYPE_CODE = 13;

  private String name;
  private MetaJsonRecord modality;
  private String qualityProcess;
  private String code;
  private final boolean deleted = false;

  public InstallationType(Row row) {
    if (validRow(row, INSTALLATION_TYPE_NAME)) {
      this.name = row.getCell(INSTALLATION_TYPE_NAME).getStringCellValue();
    }
    // this.modality = row.getCell(INSTALLATION_TYPE_MODALITY).getStringCellValue();
    // this.qualityProcess = row.getCell(INSTALLATION_TYPE_QUALITY_PROCESS).getStringCellValue();
    // this.code = row.getCell(INSTALLATION_TYPE_CODE).getStringCellValue();

  }

  @Override
  public String toString() {
    return "InstallationType{" + "name='" + name + '\'' + ", deleted=" + deleted + '}';
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MetaJsonRecord getModality() {
    return modality;
  }

  public void setModality(MetaJsonRecord modality) {
    this.modality = modality;
  }

  public String getQualityProcess() {
    return qualityProcess;
  }

  public void setQualityProcess(String qualityProcess) {
    this.qualityProcess = qualityProcess;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(Long id) {}

  private boolean validRow(Row row, int NUM_COLUMN) {
    return row.getCell(NUM_COLUMN) != null;
  }

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
    metaJsonRecord.setJsonModel("InstallationType");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }

  private String getAttrs() {
    return "{\"name\":\"" + name + "\",\"deleted\": \"false\"}";
  }
}
