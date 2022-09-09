package com.jobs;

import com.axelor.app.AppSettings;
import com.axelor.db.tenants.TenantConnectionProvider;
import com.axelor.inject.Beans;
import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientContractJob implements Job {
  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private final Map<String, Connection> connectionMap = new HashMap<>();

  @Override
  public void run() {
    try {
      logger.info("ClientContractJob is running.");
      checkContractExpiry();
    } catch (Exception e) {
      logger.error("Failure to check contract expiry dates.", e);
      MailSender.sendMail(0, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
    }
  }

  private void checkContractExpiry() {
    AppSettings appSettings = AppSettings.get();
    List<String> tenants = getTenants(appSettings);
    Date now = new Date();
    for (String tenant : tenants) {
      Date contractEndDate = getContractEndDate(appSettings, tenant);
      long timeToExpiry = contractEndDate.getTime() - now.getTime();
      int daysToExpiry = (int) (timeToExpiry / (1000 * 3600 * 24));
      logger.info("{} days left to expiry for client {}", daysToExpiry, tenant);
      alertForExpiry(tenant, daysToExpiry);
    }
  }

  private void alertForExpiry(String tenant, int daysToExpiry) {
    long clientId = getClientId(tenant);
    if (daysToExpiry == 180 || daysToExpiry == 90) {
      logger.info(
          "{} days left to expiry for client {}. Sending email to {} ...",
          daysToExpiry,
          tenant,
          "aladin.jemli"); // todo replace 'mailReceiver' by the mail's receiver
      MailSender.sendMail(
          clientId, "BiomediqaClient", "BiomediqaClient", "alertBeforeContractExpiry");
    } else if (daysToExpiry == 0) {
      logger.info(
          "{} contract has been expired. Blocking users and sending email to {} ...",
          tenant,
          "mailReceiver");
      MailSender.sendMail(
          clientId, "BiomediqaClient", "BiomediqaClient", "contract-expired-users-blocked");
      blockUsers(clientId, tenant);
    }
  }

  private void blockUsers(long clientId, String tenant) {
    if (connectionMap.get(tenant) == null) {
      TenantConnectionProvider connectionProvider = Beans.get(TenantConnectionProvider.class);
      connectionProvider.injectServices(null);
      try {
        connectionMap.put(tenant, connectionProvider.getConnection(tenant));
      } catch (SQLException throwables) {
        MailSender.sendMail(
            clientId, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
        throw new RuntimeException("Unable to get database connection.");
      }
    }
    Statement stmt = null;
    try {
      stmt = connectionMap.get(tenant).createStatement();
      int blockedUsers = stmt.executeUpdate("update auth_user set blocked = true where id > 1");
      connectionMap.get(tenant).commit();
      logger.info("{} users of {} have been blocked.", blockedUsers, tenant);
    } catch (Exception throwable) {
      throwable.printStackTrace();
      MailSender.sendMail(
          clientId, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
    } finally {
      assert stmt != null;
      try {
        stmt.close();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
  }

  private long getClientId(String tenant) {
    if (connectionMap.get("Biomediqa") == null) {
      TenantConnectionProvider connectionProvider = Beans.get(TenantConnectionProvider.class);
      connectionProvider.injectServices(null);
      try {
        connectionMap.put("Biomediqa", connectionProvider.getConnection("Biomediqa"));
      } catch (SQLException throwables) {
        MailSender.sendMail(0, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
        throw new RuntimeException("Unable to get database connection.");
      }
    }
    long clientId = 0L;
    PreparedStatement pstmt = null;
    try {
      pstmt =
          connectionMap
              .get("Biomediqa")
              .prepareStatement(
                  "select id from meta_json_record where json_model = 'BiomediqaClient' and REPLACE(name, ' ', '') = ?");
      pstmt.setString(1, tenant);
      ResultSet resultSet = pstmt.executeQuery();
      resultSet.next();
      clientId = resultSet.getLong("id");
      connectionMap.get("Biomediqa").commit();
    } catch (Exception throwable) {

      throwable.printStackTrace();
      MailSender.sendMail(
          clientId, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
    } finally {
      assert pstmt != null;
      try {
        pstmt.close();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
    return clientId;
  }

  private Date getContractEndDate(AppSettings appSettings, String tenant) {
    String contractEndDateKey = "context." + tenant + ".contractEndDate";
    String contractEndDateValue = appSettings.get(contractEndDateKey);
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return format.parse(contractEndDateValue);
    } catch (ParseException e) {
      MailSender.sendMail(1, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
      logger.error("Impossible to parse contract end date for client {}.", tenant);
      return Date.from(
          LocalDate.now()
              .minusDays(1)
              .atStartOfDay(ZoneId.systemDefault())
              .toInstant()); // random past date
    }
  }

  private List<String> getTenants(AppSettings appSettings) {
    String tenantRegex = "^db\\.[a-zA-Z0-9]+\\.name$";
    Pattern tenantPattern = Pattern.compile(tenantRegex);
    return appSettings.getProperties().entrySet().stream()
        .filter(entry -> tenantPattern.matcher((String) entry.getKey()).find())
        .map(entry -> ((String) entry.getValue()).replaceAll("\\s+", ""))
        .collect(Collectors.toList());
  }
}
