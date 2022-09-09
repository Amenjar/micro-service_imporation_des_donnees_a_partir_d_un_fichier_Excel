package com.dosilink.entity;

import com.axelor.meta.db.MetaJsonRecord;

public class PassiveDosimetry {
  private String position;

  public PassiveDosimetry(String name) {
    this.position = name;
  }
  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(position);
    metaJsonRecord.setJsonModel("passiveDosimetry");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }
  private String getAttrs() {
    return "{\"position\": \"" + position + "\"}";
  }
}
