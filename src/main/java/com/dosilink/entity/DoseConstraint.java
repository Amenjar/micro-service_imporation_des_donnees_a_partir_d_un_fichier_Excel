package com.dosilink.entity;

import com.axelor.meta.db.MetaJsonRecord;

public class DoseConstraint {
  private String dose;

  public DoseConstraint(String name) {
    this.dose = name;
  }
  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(dose);
    metaJsonRecord.setJsonModel("doseConstraint");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }
  private String getAttrs() {
    return "{\"dose\": \"" + dose + "\"}";
  }
}
