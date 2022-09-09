package com.jobs;

import com.axelor.db.tenants.TenantConnectionProvider;
import com.axelor.exception.AxelorException;
import com.axelor.inject.Beans;
import com.axelor.studio.service.builder.ActionEmailBuilderService;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {
  private static final Map<String, Connection> connectionMap = new HashMap<>();
  private static final Logger logger =
      LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  static void sendMail(long clientId, String model, String tag, String templateName) {
    Runnable task = () -> doSendMail(clientId, model, tag, templateName);
    Thread thread = new Thread(task);
    thread.start();
  }

  static void doSendMail(long clientId, String model, String tag, String templateName) {
    long templateId = getTemplateId(templateName);
    ActionEmailBuilderService actionEmailBuilderService =
        Beans.get(ActionEmailBuilderService.class);

    try {
      actionEmailBuilderService.sendEmail(clientId, model, tag, templateId, 0);
    } catch (ClassNotFoundException
        | InstantiationException
        | IllegalAccessException
        | AxelorException
        | IOException
        | MessagingException e) {
      logger.error("Failure to send mail with template id {}.", templateId);
      e.printStackTrace();
    }
  }

  private static long getTemplateId(String name) {
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
    long templateId = 0L;
    PreparedStatement pstmt = null;
    try {
      pstmt =
          connectionMap
              .get("Biomediqa")
              .prepareStatement("select id from MESSAGE_TEMPLATE where name = ?");
      pstmt.setString(1, name);
      ResultSet resultSet = pstmt.executeQuery();
      resultSet.next();
      templateId = resultSet.getLong("id");
      connectionMap.get("Biomediqa").commit();
    } catch (Exception throwable) {
      throwable.printStackTrace();
      MailSender.sendMail(
          templateId, "BiomediqaClient", "BiomediqaClient", "failure-at-job-execution");
    } finally {
      assert pstmt != null;
      try {
        pstmt.close();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    }
    return templateId;
  }
}
