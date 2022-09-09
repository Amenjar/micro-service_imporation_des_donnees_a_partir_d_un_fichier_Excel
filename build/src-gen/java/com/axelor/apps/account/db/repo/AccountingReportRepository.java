package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.AccountingReport;
import com.axelor.db.JpaRepository;

public class AccountingReportRepository extends JpaRepository<AccountingReport> {

	public AccountingReportRepository() {
		super(AccountingReport.class);
	}

	// TYPE SELECT
	public static final int REPORT_GENERAL_LEDGER = 1;
	public static final int REPORT_GENERAL_BALANCE = 2;
	public static final int REPORT_BALANCE = 3;
	public static final int REPORT_AGED_BALANCE = 4;
	public static final int REPORT_CHEQUE_DEPOSIT = 5;
	public static final int REPORT_PARNER_BALANCE = 6;
	public static final int REPORT_PARNER_GENERAL_LEDGER = 7;
	public static final int REPORT_CASH_PAYMENTS = 10;
	public static final int REPORT_JOURNAL = 11;
	public static final int REPORT_VAT_STATEMENT_INVOICE = 12;
	public static final int REPORT_PAYMENT_DIFFERENCES = 13;
	public static final int REPORT_GENERAL_LEDGER2 = 14;
	public static final int REPORT_VAT_STATEMENT_RECEIVED = 15;
	public static final int REPORT_ANALYTIC_BALANCE = 2000;
	public static final int REPORT_ANALYTIC_GENERAL_LEDGER = 2001;
	public static final int REPORT_ACQUISITIONS = 16;
	public static final int REPORT_GROSS_VALUES_AND_DEPRECIATION = 17;

	// EXPORT TYPE SELECT
	public static final int EXPORT_ADMINISTRATION = 1000;
	public static final int EXPORT_PAYROLL_JOURNAL_ENTRY = 1001;
	public static final int EXPORT_SALES = 1006;
	public static final int EXPORT_REFUNDS = 1007;
	public static final int EXPORT_TREASURY = 1008;
	public static final int EXPORT_PURCHASES = 1009;
	public static final int EXPORT_GENERAL_BALANCE = 1010;

	// STATUS SELECT
	public static final int STATUS_DRAFT = 1;
	public static final int STATUS_VALIDATED = 2;

	// TYPE SELECT
	public static final int REPORT_BANK_RECONCILIATION_STATEMENT = 18;
}

