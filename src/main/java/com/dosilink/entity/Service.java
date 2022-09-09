package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;

public class Service extends Model {
  private static final int SERVICE_NAME = 2;
  private static final int SERVICE_SITE = 0;
  private static final int SERVICE_RESPONSIBLE = 4;

  private String name;
  private String responsible;
  private MetaJsonRecord site;
  private final boolean deleted = false;

  public Service(String name, String responsible, MetaJsonRecord site) {
    this.name = name;
    this.responsible = responsible;
    this.site = site;
  }

  public Service(Row row, MetaJsonRecord site) {
    if (validRowString(row, SERVICE_NAME)) {
      this.name = row.getCell(SERVICE_NAME).getStringCellValue();
      // this.responsible = row.getCell(SERVICE_RESPONSIBLE).getStringCellValue();
      this.site = site;
    }
  }

  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
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
    Service other = (Service) o;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.responsible = responsible;
  }

  public MetaJsonRecord getSite() {
    return site;
  }

  public void setSite(MetaJsonRecord site) {
    this.site = site;
  }

  @Override
  public String toString() {
    return "Service{"
        + "name='"
        + name
        + '\''
        + ", responsible='"
        + responsible
        + '\''
        + ", site='"
        + site
        + '\''
        + ", deleted="
        + deleted
        + '}';
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(Long id) {}

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
    metaJsonRecord.setJsonModel("serviceAffectation");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }

  private String getAttrs() {
    return "{\"name\":\""
        + name
        + "\",\"site\":{\"id\":\""
        + site.getId()
        + "\",\"name\":\""
        + site.getName()
        + "\",\"$version\":\"1\"},\"deleted\":\"false\"}";
  }
}
