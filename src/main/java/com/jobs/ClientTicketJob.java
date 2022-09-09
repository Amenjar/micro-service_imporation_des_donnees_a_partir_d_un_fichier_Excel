package com.jobs;

import com.axelor.inject.Beans;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTicketJob implements Job {

  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void run() {
    logger.info("ClientTicketJob is running.");
    List tickets = getTicketId();
    for (Object ticket : tickets) {
      MailSender.sendMail(
          ((BigInteger) ticket).longValue(),
          "com.axelor.apps.helpdesk.db.Ticket",
          "Ticket",
          "Support-client-alarme");
    }
  }

  private List getTicketId() {
    EntityManagerFactory entityManagerFactory =
        Beans.get(javax.persistence.EntityManagerFactory.class);
    EntityManager em = entityManagerFactory.createEntityManager();
    List ticketId =
        em.createNativeQuery(
                "select id from helpdesk_ticket where Date(deadline_datet) = current_date and assigned_to_user is not null ")
            .getResultList();
    em.close();
    return ticketId;
  }
}
