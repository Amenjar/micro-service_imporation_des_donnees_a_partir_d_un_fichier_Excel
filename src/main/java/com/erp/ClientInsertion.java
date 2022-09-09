package com.erp;

import com.axelor.app.AppSettings;
import com.axelor.apps.base.db.*;
import com.axelor.apps.base.db.repo.*;
import com.axelor.apps.base.service.PartnerServiceImpl;
import com.axelor.apps.message.db.EmailAddress;
import com.axelor.apps.message.db.repo.EmailAddressRepository;
import com.axelor.auth.db.User;
import com.axelor.db.JPA;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClientInsertion
{

    public static final int DATA_FIRST_ROW = 1;

    public static final int CLIENT_NAME = 0;

    public static final int PHONE_NUMBER = 7;

    public static final int WEB_SITE = 8;

    public static final int COUNTRY = 5;

    public static final int CODE = 3;

    public static final int STREET = 2;

    public static final int CITY = 4;

    public static final int CONTACT_FIRSTNAME = 10;

    public static final int CONTACT_NAME = 11;

    public static final int TITLE = 9;

    public static final int FUNCTION = 12;

    public static final int SERVICE = 13;

    public static final int CONTACT_PHIXED_PHONE = 16;

    public static final int CONTACT_EMAIL = 14;

    public static final int COMMERCIAL_NAME = 19;

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void run(String fileName) throws IOException, AxelorException, SQLException
    {
        String dataFolder = AppSettings.get().get("file.upload.dir");
        FileInputStream fis = new FileInputStream(dataFolder + "/ERP/Fichier_test.xlsx");
        // logger.info("Reading data from {}", dataFolder + "/" + tenant + "/" + fileName);
        Sheet sheet = new XSSFWorkbook(fis).getSheetAt(0);
        parseData(sheet);
    }

    List<Integer> error_line_number = new ArrayList<>();
    int i = 0;

    private void parseData(Sheet sheet)
    {
        List<Integer> errorLines = new ArrayList<>(); //Arrays.asList(1, 2, 9, 10, 11, 14, 15, 16, 17, 18, 19, 22, 23, 24, 43, 45, 52, 54, 55, 56, 58, 60, 62, 63, 64, 65, 66, 68, 69, 70, 72, 76, 77, 78, 81, 83, 84, 85, 89, 91, 92, 93, 94, 95, 96, 97, 98, 99);
        for (Row row : sheet)
        {
            if (row.getRowNum() < DATA_FIRST_ROW || errorLines.contains(row.getRowNum()))
            {
                continue;
            }
            JPA.runInTransaction(() -> savePartnerAndClient(row));
        }
        System.out.println("Number of lines with exceptions: " + i);
        System.out.println("rows with exceptions " + Arrays.toString(error_line_number.toArray()));
    }

    private void savePartnerAndClient(Row row)
    {
        try
        {
            System.out.println(row.getRowNum());
            String clientName = row.getCell(CLIENT_NAME).getStringCellValue();
            Partner clientPartner = Beans.get(PartnerRepository.class).findByName(clientName);
            Company company = Beans.get(CompanyRepository.class).find(1L);
            if (clientPartner == null)
            {
                clientPartner = savePartner(createClientPartner(row, company));
            }
            String contactName = row.getCell(CONTACT_NAME).getStringCellValue();
            if (!contactName.isEmpty())
            {
                Partner contactPartner = Beans.get(PartnerRepository.class).findByName(contactName);
                if (contactPartner == null)
                {
                    contactPartner = savePartner(createContactPartner(row, clientPartner, company));
                }
                clientPartner.addContactPartnerSetItem(contactPartner);
            }
        }
        catch (Throwable e)
        {
            logger.error(e.getMessage());
            error_line_number.add(row.getRowNum());
            i++;
        }
    }

    public Partner savePartner(Partner partner) throws AxelorException
    {
        Beans.get(PartnerServiceImpl.class).onSave(partner);
        return Beans.get(PartnerRepository.class).save(partner);
    }

    public Partner createClientPartner(Row row, Company company)
    {
        Partner partner = new Partner();
        partner.setPartnerTypeSelect(1);
        partner.setIsProspect(true);
        partner.addCompanySetItem(company);
        partner.setCurrency(Beans.get(CurrencyRepository.class).find(46L));
        partner.setName(row.getCell(CLIENT_NAME).getStringCellValue());
        partner.setFixedPhone(row.getCell(PHONE_NUMBER).getStringCellValue());
        partner.setWebSite(row.getCell(WEB_SITE).getStringCellValue());
        partner.setPartnerAttrs("{\"isContract\": false}");
        partner.setPartnerAddressList(getAddressList(row, partner));
        partner.setMainAddress(partner.getPartnerAddressList().get(0).getAddress());
        return partner;
    }

    public Partner createContactPartner(Row row, Partner clientPartner, Company company)
    {
        Partner partner = new Partner();
        partner.setMainPartner(clientPartner);
        partner.setIsContact(true);
        partner.setTitleSelect(row.getCell(TITLE).getStringCellValue().equals("MADAME") ? 1 : 2);
        partner.setFirstName(row.getCell(CONTACT_FIRSTNAME).getStringCellValue());
        partner.setName(row.getCell(CONTACT_NAME).getStringCellValue());
        partner.setFixedPhone(row.getCell(CONTACT_PHIXED_PHONE).getStringCellValue());
        partner.setEmailAddress(createEmail(row.getCell(CONTACT_EMAIL).getStringCellValue()));
        String attrs = row.getCell(FUNCTION).getStringCellValue().isEmpty() ? "" : ("{\"function\": " + "[{\"id\": " + getFunction(row.getCell(FUNCTION).getStringCellValue()) + "}]" + (row.getCell(SERVICE).getStringCellValue().isEmpty() ? "}" : ", "));
        attrs += row.getCell(SERVICE).getStringCellValue().isEmpty() ? "" : (attrs.isEmpty() ? "{" : "" + "\"service\": \"" + row.getCell(SERVICE).getStringCellValue() + "\"}");
        if (!attrs.isEmpty())
        {
            partner.setContactAttrs(attrs);
        }
        partner.addCompanySetItem(company);
        partner.setCurrency(Beans.get(CurrencyRepository.class).find(46L));
        partner.setUser(getUser(row.getCell(COMMERCIAL_NAME).getStringCellValue()));
        return partner;
    }

    private User getUser(String userName)
    {
        return Beans.get(UserRepositoryExtension.class).findByName(userName);
    }

    private long getFunction(String name)
    {
        return Beans.get(FunctionRepositoryExtension.class).findByName(name).getId();
    }

    private EmailAddress createEmail(String email)
    {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress(email);
        return Beans.get(EmailAddressRepository.class).save(emailAddress);
    }

    private List<PartnerAddress> getAddressList(Row row, Partner partner)
    {
        PartnerAddress partnerAddress = new PartnerAddress();
        partnerAddress.setPartner(partner);
        partnerAddress.setAddress(getAddress(row));
        partnerAddress.setIsInvoicingAddr(true);
        partnerAddress.setIsDeliveryAddr(true);
        partnerAddress.setIsDefaultAddr(true);
        return Collections.singletonList(partnerAddress);
    }

    private Address getAddress(Row row)
    {
        Address address = new Address();
        address.setStreet(createStreet(row));
        address.setAddressL4(row.getCell(STREET).getStringCellValue());
        address.setAddressL6(address.getStreet().getCity().getZip());
        address.setAddressL7Country(getCountry(row.getCell(COUNTRY).getStringCellValue()));
        address.setCity(address.getStreet().getCity());
        Cell codeCell = row.getCell(CODE);
        codeCell.setCellType(Cell.CELL_TYPE_STRING);
        address.setZip(codeCell.getStringCellValue());
        Beans.get(AddressRepository.class).save(address);
        return address;
    }

    private Street createStreet(Row row)
    {
        Street street = Beans.get(StreetRepository.class).findByName(row.getCell(STREET).getStringCellValue());
        if (street == null)
        {
            street = new Street();
            street.setName(row.getCell(STREET).getStringCellValue());
            street.setCity(getCity(row.getCell(CITY).getStringCellValue()));
            street = Beans.get(StreetRepository.class).save(street);
        }
        return street;
    }

    private Country getCountry(String name)
    {
        return Beans.get(CountryRepository.class).findByName(name);
    }

    private City getCity(String name)
    {
        City city = Beans.get(CityRepositoryExtension.class).findByName(name);
        return city != null ? city : new City(name);
    }

}
