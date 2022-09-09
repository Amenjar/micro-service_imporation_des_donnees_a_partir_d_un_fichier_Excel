package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;

public class Localisation extends Model {
  private static final int LOCALISATION_NAME = 6;
  private static final int LOCALISATION_SERVICE = 2;
  private String name;
  private MetaJsonRecord service;
  private final boolean deleted = false;

  public Localisation(Row row, MetaJsonRecord service) {
    if (validRowString(row, LOCALISATION_NAME)) {
      this.name = row.getCell(LOCALISATION_NAME).getStringCellValue();
      this.service = service;
    }
  }

  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }

  @Override
  public String toString() {
    return "Localisation{"
        + "name='"
        + name
        + '\''
        + ", service='"
        + service
        + '\''
        + ", deleted="
        + deleted
        + '}';
  }

  public MetaJsonRecord getService() {
    return service;
  }

  public void setService(MetaJsonRecord service) {
    this.service = service;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(Long id) {}

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
    Localisation other = (Localisation) o;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, deleted);
  }

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
    metaJsonRecord.setJsonModel("location");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }

  private String getAttrs() {
    return "{\"name\":\""
        + name
        + "\",\"deleted\": \"false\",\"site\":{\"id\":\""
        + service.getId()
        + "\",\"name\":\""
        + service.getName()
        + "\"},\"deleted\":\"false\"}";
  }
}
