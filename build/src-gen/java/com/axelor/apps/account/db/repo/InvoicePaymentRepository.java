package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.InvoicePayment;
import com.axelor.apps.account.db.Reconcile;
import com.axelor.apps.bankpayment.db.BankOrder;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class InvoicePaymentRepository extends JpaRepository<InvoicePayment> {

	public InvoicePaymentRepository() {
		super(InvoicePayment.class);
	}

	public Query<InvoicePayment> findByReconcile(Reconcile reconcile) {
		return Query.of(InvoicePayment.class)
				.filter("self.reconcile = :reconcile")
				.bind("reconcile", reconcile);
	}

	public Query<InvoicePayment> findByBankOrder(BankOrder bankOrder) {
		return Query.of(InvoicePayment.class)
				.filter("self.bankOrder = :bankOrder")
				.bind("bankOrder", bankOrder);
	}

	// STATUS SELECT
	public static final int STATUS_DRAFT = 0;
	public static final int STATUS_VALIDATED = 1;
	public static final int STATUS_CANCELED = 2;
	public static final int STATUS_PENDING = 3;

	  	// TYPE SELECT
	public static final int TYPE_ADVANCEPAYMENT = 1;
	public static final int TYPE_PAYMENT = 2;
	public static final int TYPE_REFUND_INVOICE = 3;
	public static final int TYPE_INVOICE = 4;
	public static final int TYPE_OTHER = 5;
	public static final int TYPE_ADV_PAYMENT_IMPUTATION = 6;
}

