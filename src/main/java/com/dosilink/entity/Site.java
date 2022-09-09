package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;

public class Site extends Model {

  private static final int SITE_NAME = 0;
  private static final int SITE_ADDRESS = 1;
  private String name;
  private String address;

  private static final boolean deleted = false;

  public Site(Row row) {
    if (validRowString(row, SITE_NAME)) {
      this.name = row.getCell(SITE_NAME).getStringCellValue();
    }
    if (validRowString(row, SITE_ADDRESS)) {
      this.address = row.getCell(SITE_ADDRESS).getStringCellValue();
    }
  }

  public Site(String name, String address) {
    this.name = name;
    this.address = address;
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
    Site other = (Site) o;
    return Objects.equals(name, other.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, address);
  }

  @Override
  public String toString() {
    return "Site{" + "name='" + name + '\'' + ", address='" + address + '\'' + '}';
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(Long id) {}

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
    metaJsonRecord.setJsonModel("site");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }

  private String getAttrs() {
    return "{\"name\": \"" + name + "\",\"adress\": \"" + address + "\",\"deleted\": \"false\"}";
  }
  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }
}
