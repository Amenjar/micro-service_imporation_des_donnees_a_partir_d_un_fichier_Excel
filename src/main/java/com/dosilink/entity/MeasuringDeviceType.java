package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;

public class MeasuringDeviceType extends Model {
  private static final int MEASURING_DEVICE_TYPE = 12;
  private String nomenclature;
  private final boolean deleted = false;

  public MeasuringDeviceType(Row row) {
    if (validRowString(row, MEASURING_DEVICE_TYPE)) {
      this.nomenclature = row.getCell(MEASURING_DEVICE_TYPE).getStringCellValue();
    }
  }

  @Override
  public String toString() {
    return "MeasuringDeviceType{"
        + "nomenclature='"
        + nomenclature
        + '\''
        + ", deleted="
        + deleted
        + '}';
  }

  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }

  public String getNomenclature() {
    return nomenclature;
  }

  public void setNomenclature(String nomenclature) {
    this.nomenclature = nomenclature;
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
    MeasuringDeviceType other = (MeasuringDeviceType) o;
    return Objects.equals(nomenclature, other.nomenclature);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nomenclature);
  }

  @Override
  public void setId(Long id) {}

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(nomenclature);
    metaJsonRecord.setJsonModel("MeasuringDeviceType");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }

  private String getAttrs() {
    return "{\"nomenclature\": \"" + nomenclature + "\", \"deleted\": \"false\"}";
  }
}
