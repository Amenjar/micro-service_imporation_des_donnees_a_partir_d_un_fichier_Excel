package com.jobs;

import com.axelor.inject.Beans;
import java.lang.invoke.MethodHandles;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientOrderJob implements Job {

  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void run() {
    logger.info("ClientOrderJob is running.");
    List orders = getOrderId();
    for (Object order : orders) {
      MailSender.sendMail(
          ((BigInteger) order).longValue(),
          "com.axelor.apps.sale.db.SaleOrder",
          "SaleOrder",
          "devis/commandes alarme");
    }
  }

  private List getOrderId() {
    EntityManagerFactory entityManagerFactory =
        Beans.get(javax.persistence.EntityManagerFactory.class);
    EntityManager em = entityManagerFactory.createEntityManager();
    List orderId =
        em.createNativeQuery(
                "select id "
                    + // todo try not to user nativequery.
                    "from sale_sale_order "
                    + "where"
                    + "("
                    + "  (Date(end_of_validity_date) = current_date "
                    + "  or Date(end_of_validity_date) = (SELECT CURRENT_DATE + INTERVAL '30 day')"
                    + ") and salesperson_user is not null"
                    + ")")
            .getResultList();
    em.close();
    return orderId;
  }
}
