package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

public class Training extends Model {

  private static final int TRAINING_NAME = 1;

  private static final int TRAINING_PERIODICITY = 5;

  private static final int TRAINING_Periodique = 4;

  private static final int TRAINING_COMMENT = 6;

  private MetaJsonRecord type;
  private String name;
  private MetaJsonRecord functionGrad;
  private String periodicity;
  private String xRayGenerator;
  private String modality;
  private String format;
  private String content;
  private String educationalGoals;
  private Number expectedSkills;
  private String program;
  private String comment;
  private final boolean deleted = false;

  public Training(Row row, MetaJsonRecord type, MetaJsonRecord functionGrad) {
    if (validRowString(row, TRAINING_NAME)) {
      this.name = row.getCell(TRAINING_NAME).getStringCellValue();
    }
    if (validRowString(row, TRAINING_PERIODICITY)) {
      this.periodicity = row.getCell(TRAINING_PERIODICITY).getStringCellValue();
    }
    if (validRow(row, TRAINING_Periodique)) {
      this.expectedSkills = row.getCell(TRAINING_Periodique).getNumericCellValue();
    }
    if (validRowString(row, TRAINING_COMMENT)) {
      this.comment = row.getCell(TRAINING_COMMENT).getStringCellValue();
    }
    this.type = type;
    this.functionGrad = functionGrad;
  }


  public MetaJsonRecord getType() {
    return type;
  }

  public void setType(MetaJsonRecord type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MetaJsonRecord getFunctionGrad() {
    return functionGrad;
  }

  public void setFunctionGrad(MetaJsonRecord functionGrad) {
    this.functionGrad = functionGrad;
  }

  public String getPeriodicity() {
    return periodicity;
  }

  public void setPeriodicity(String periodicity) {
    this.periodicity = periodicity;
  }

  public String getxRayGenerator() {
    return xRayGenerator;
  }

  public void setxRayGenerator(String xRayGenerator) {
    this.xRayGenerator = xRayGenerator;
  }

  public String getModality() {
    return modality;
  }

  public void setModality(String modality) {
    this.modality = modality;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getEducationalGoals() {
    return educationalGoals;
  }

  public void setEducationalGoals(String educationalGoals) {
    this.educationalGoals = educationalGoals;
  }

  public Number getExpectedSkills() {
    return expectedSkills;
  }

  public void setExpectedSkills(Number expectedSkills) {
    this.expectedSkills = expectedSkills;
  }

  public String getProgram() {
    return program;
  }

  public void setProgram(String program) {
    this.program = program;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setId(Long id) {}

  public MetaJsonRecord toMetaJsonRecord() {
    MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
    metaJsonRecord.setJsonModel("training");
    metaJsonRecord.setAttrs(getAttrs());
    return metaJsonRecord;
  }

  private boolean validRow(Row row, int NUM_COLUMN) {
    return row.getCell(NUM_COLUMN) != null;
  }

  private String getAttrs() {
    return "{\"name\": \""
        + name
        + "\", \"type\": {\"id\":\""
        + type.getId()
        + "\", \"name\": \""
        + type.getName()
        + "\", \"$version\": 5}, \"deleted\": \"false\", \"periodicity\": \""
        + periodicity
        + "\", \"functionGrad\": [{\"id\":\""
        + functionGrad.getId()
        + "\",\"name\":\""
        + type.getName()
        + "\"}], \"trainingStep\": \"1\"}";
  }
  private boolean validRowString(Row row, int NUM_COLUMN) {
    return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
  }
}
