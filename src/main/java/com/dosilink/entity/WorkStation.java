package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

public class WorkStation extends Model {
    private static final int WORK_STATION_NAME =7;
    private String name;

    public WorkStation(Row row) {
      if(validRowString(row,WORK_STATION_NAME)){
          this.name = row.getCell(WORK_STATION_NAME).getStringCellValue();
      }
    }

    private boolean validRowString(Row row, int NUM_COLUMN) {
        return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }
    public MetaJsonRecord toMetaJsonRecord() {
        MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
        metaJsonRecord.setJsonModel("workerStation");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }
    private String getAttrs() {
        return "{\"name\": \"" + name + "\",\"deleted\": \"false\"}";
    }
}
