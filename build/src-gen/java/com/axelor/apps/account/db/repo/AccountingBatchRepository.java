package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.AccountingBatch;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class AccountingBatchRepository extends JpaRepository<AccountingBatch> {

	public AccountingBatchRepository() {
		super(AccountingBatch.class);
	}

	public AccountingBatch findByCode(String code) {
		return Query.of(AccountingBatch.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	// ACTION TYPE
	public static final int ACTION_REIMBURSEMENT = 11;
	public static final int ACTION_DEBT_RECOVERY = 14;
	public static final int ACTION_DOUBTFUL_CUSTOMER = 16;
	public static final int ACTION_ACCOUNT_CUSTOMER = 17;
	public static final int ACTION_MOVE_LINE_EXPORT = 18;
	public static final int ACTION_CREDIT_TRANSFER = 19;
	public static final int ACTION_REALIZE_FIXED_ASSET_LINES = 20;
	public static final int ACTION_CLOSE_ANNUAL_ACCOUNTS_OF_PAST_YEAR = 21;
	public static final int ACTION_OPEN_ANNUAL_ACCOUNTS_OF_NEXT_YEAR = 22;

	// REIMBURSEMENT TYPE
	public static final int REIMBURSEMENT_TYPE_EXPORT = 1;
	public static final int REIMBURSEMENT_TYPE_IMPORT = 2;

	// REIMBURSEMENT EXPORT TYPE
	public static final int REIMBURSEMENT_EXPORT_TYPE_GENERATE = 1;
	public static final int REIMBURSEMNT_EXPORT_TYPE_EXPORT = 2;

	// INTERBANK PAYMENT ORDER TYPE
	public static final int INTERBANK_PAYMENT_ORDER_TYPE_IMPORT = 1;
	public static final int INTERBANK_PAYMENT_ORDER_TYPE_REJECT_IMPORT = 2;

	// DEBT RECOVERY TYPE
	public static final int DEBT_RECOVERY_TYPE = 1;

	// CREDIT TRANSFER TYPE
	public static final int CREDIT_TRANSFER_EXPENSE_PAYMENT = 1;
	public static final int CREDIT_TRANSFER_SUPPLIER_PAYMENT = 2;
	public static final int CREDIT_TRANSFER_CUSTOMER_REIMBURSEMENT = 3;

	// CUSTOMER REIMBURSEMENT TYPE
	public static final int CUSTOMER_REIMBURSEMENT_CUSTOMER_REFUND = 1;
	public static final int CUSTOMER_REIMBURSEMENT_PARTNER_CREDIT_BALANCE = 2;

	// ACTION TYPE
	public static final int ACTION_DIRECT_DEBIT = 12;
	public static final int ACTION_BANK_STATEMENT = 20;

	// DIRECT DEBIT DATA TYPE
	public static final int DIRECT_DEBIT_DATA_CUSTOMER_INVOICE = 1;
	public static final int DIRECT_DEBIT_DATA_PAYMENT_SCHEDULE = 2;
	public static final int DIRECT_DEBIT_DATA_MONTHLY_PAYMENT_SCHEDULE = 3;
}

