package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import java.util.Objects;
import org.apache.poi.ss.usermodel.Row;

public class FunctionGrade extends Model {

  private static final int FUNCTIONGRADE_NAME = 5;
  private String name;
  private final boolean deleted = false;

  public FunctionGrade(Row row) {
    if (validRowString(row, FUNCTIONGRADE_NAME)) {
      this.name = row.getCell(FUNCTIONGRADE_NAME).getStringCellValue();
    }
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "FunctionGrade{" + "name='" + name + '\'' + ", deleted=" + deleted + '}';
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (getClass() != o.getClass()) {
      return false;
    }
    FunctionGrade other = (FunctionGrade) o;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public void setId(Long id) {}

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
    metaJsonRecord.setJsonModel("functionGrade");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }
  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }

  private String getAttrs() {
    return "{\"name\": \"" + name + "\",\"deleted\": \"false\"}";
  }
}
