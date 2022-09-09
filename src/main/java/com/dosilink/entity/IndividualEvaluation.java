package com.dosilink.entity;

import com.axelor.meta.db.MetaJsonRecord;

public class IndividualEvaluation {
  private String protectionEquipment;

  public IndividualEvaluation(String name) {
    this.protectionEquipment = name;
  }
  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(protectionEquipment);
    metaJsonRecord.setJsonModel("individualEvaluation");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }
  private String getAttrs() {
    return "{\"protectionEquipment\": \"" + protectionEquipment + "\"}";
  }
}
