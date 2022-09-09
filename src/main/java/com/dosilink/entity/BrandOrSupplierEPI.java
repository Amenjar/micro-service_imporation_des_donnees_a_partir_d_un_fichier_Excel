package com.dosilink.entity;

import com.axelor.db.Model;
import com.axelor.meta.db.MetaJsonRecord;
import org.apache.poi.ss.usermodel.Row;

import java.util.Objects;

public class BrandOrSupplierEPI extends Model {
    private static final int BRAND_SUPPLIER_NAME = 11;
    private static final int BRAND_SUPPLIER_DOMAIN = 16;
    private static final int BRAND_SUPPLIER_CONTACT_NAME = 17;
    private static final int BRAND_SUPPLIER_TYPE = 18;
    private static final int BRAND_SUPPLIER_PHONE_NUMBER = 19;
    private static final int BRAND_SUPPLIER_EMAIL = 20;
    private String name;
    private String domain;
    private String contactName;
    private String type;
    private String phoneNumber;
    private String email;
    private final boolean deleted = false;

    public BrandOrSupplierEPI(Row row) {
        if (validRowString(row, BRAND_SUPPLIER_NAME)) {
            this.name = row.getCell(BRAND_SUPPLIER_NAME).getStringCellValue();
        }
        // this.domain = row.getCell(BRAND_SUPPLIER_DOMAIN).getStringCellValue();
        // this.contactName = row.getCell(BRAND_SUPPLIER_CONTACT_NAME).getStringCellValue();
        // this.type = row.getCell(BRAND_SUPPLIER_TYPE).getStringCellValue();
        //  this.phoneNumber = row.getCell(BRAND_SUPPLIER_PHONE_NUMBER).getStringCellValue();
        //  this.email = row.getCell(BRAND_SUPPLIER_EMAIL).getStringCellValue();

    }

    @Override
    public String toString() {
        return "BrandOrSupplier{" + "name='" + name + '\'' + ", deleted=" + deleted + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Long getId() {
        return null;
    }

    private boolean validRowString(Row row, int NUM_COLUMN) {
        return !(row.getCell(NUM_COLUMN) == null || row.getCell(NUM_COLUMN).getStringCellValue().isEmpty());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public void setId(Long id) {}

    public MetaJsonRecord toMetaJsonRecord() {
        MetaJsonRecord metaJsonRecord = new MetaJsonRecord(name);
        metaJsonRecord.setJsonModel("Brand");
        metaJsonRecord.setAttrs(getAttrs());
        return metaJsonRecord;
    }

    private String getAttrs() {
        return "{\"name\": \"" + name + "\",\"type\": \"Brand\", \"domain\": \"Protection equipment\",\"deleted\": \"false\"}";
    }
}
