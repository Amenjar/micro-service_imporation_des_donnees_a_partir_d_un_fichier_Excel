package com.axelor.apps.account.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Batch;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Period;
import com.axelor.apps.base.db.Year;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_ACCOUNTING_BATCH", indexes = { @Index(columnList = "company"), @Index(columnList = "bank_details"), @Index(columnList = "currency"), @Index(columnList = "payment_mode"), @Index(columnList = "period"), @Index(columnList = "year") })
public class AccountingBatch extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ACCOUNTING_BATCH_SEQ")
	@SequenceGenerator(name = "ACCOUNT_ACCOUNTING_BATCH_SEQ", sequenceName = "ACCOUNT_ACCOUNTING_BATCH_SEQ", allocationSize = 1)
	private Long id;

	@HashKey
	@Widget(title = "Code")
	@NameColumn
	@Column(unique = true)
	private String code;

	@Widget(title = "Action", selection = "iaccounting.batch.action.select")
	@NotNull
	private Integer actionSelect = 0;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "BBAN/IBAN to use")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails bankDetails;

	@Widget(title = "Currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Payment Mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	private LocalDate dueDate;

	private Boolean includeOtherBankAccounts = Boolean.FALSE;

	@Widget(title = "Reimbursement batch type", selection = "ireimbursement.batch.type.select")
	private Integer reimbursementTypeSelect = 0;

	@Widget(title = "Treatment/Operation type", selection = "ireimbursement.batch.export.type.select")
	private Integer reimbursementExportTypeSelect = 0;

	@Widget(title = "Debt recovery batch type", selection = "idebt.recovery.batch.type.select")
	private Integer debtRecoveryTypeSelect = 0;

	@Widget(title = "Customer balance")
	private Boolean updateCustAccountOk = Boolean.FALSE;

	@Widget(title = "Due balance")
	private Boolean updateDueCustAccountOk = Boolean.FALSE;

	@Widget(title = "Due balance recoverable")
	private Boolean updateDueDebtRecoveryCustAccountOk = Boolean.FALSE;

	@Widget(title = "Export type", selection = "accounting.report.type.select.export")
	private Integer moveLineExportTypeSelect = 0;

	@Widget(title = "Start date")
	private LocalDate startDate;

	@Widget(title = "End date")
	private LocalDate endDate;

	@Widget(title = "Description")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@Widget(title = "Batchs")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountingBatch", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Batch> batchList;

	@Widget(title = "Credit Transfer batch type", selection = "iaccount.account.batch.transfer.type.select")
	private Integer creditTransferTypeSelect = 0;

	@Widget(title = "Period")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Period period;

	@Widget(title = "Customer reimbursement type", selection = "iaccount.account.batch.transfer.customer.reimbursement.type.select")
	@NotNull
	private Integer customerReimbursementTypeSelect = 1;

	@Widget(title = "Open the year accounts")
	private Boolean openYear = Boolean.FALSE;

	@Widget(title = "Close the year accounts")
	private Boolean closeYear = Boolean.FALSE;

	@Widget(title = "Year")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Year year;

	@Widget(title = "Accounting.Accounts")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accountSet;

	@Widget(title = "Allocate per partner")
	private Boolean allocatePerPartner = Boolean.FALSE;

	@Widget(title = "Move description")
	private String moveDescription;

	@Widget(title = "Update all realized fixed asset lines")
	private Boolean updateAllRealizedFixedAssetLines = Boolean.FALSE;

	@Widget(title = "Direct debit data type", selection = "iaccount.account.batch.direct.debit.data.type.select")
	private Integer directDebitDataTypeSelect = 1;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AccountingBatch() {
	}

	public AccountingBatch(String code) {
		this.code = code;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getActionSelect() {
		return actionSelect == null ? 0 : actionSelect;
	}

	public void setActionSelect(Integer actionSelect) {
		this.actionSelect = actionSelect;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public BankDetails getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(BankDetails bankDetails) {
		this.bankDetails = bankDetails;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Boolean getIncludeOtherBankAccounts() {
		return includeOtherBankAccounts == null ? Boolean.FALSE : includeOtherBankAccounts;
	}

	public void setIncludeOtherBankAccounts(Boolean includeOtherBankAccounts) {
		this.includeOtherBankAccounts = includeOtherBankAccounts;
	}

	public Integer getReimbursementTypeSelect() {
		return reimbursementTypeSelect == null ? 0 : reimbursementTypeSelect;
	}

	public void setReimbursementTypeSelect(Integer reimbursementTypeSelect) {
		this.reimbursementTypeSelect = reimbursementTypeSelect;
	}

	public Integer getReimbursementExportTypeSelect() {
		return reimbursementExportTypeSelect == null ? 0 : reimbursementExportTypeSelect;
	}

	public void setReimbursementExportTypeSelect(Integer reimbursementExportTypeSelect) {
		this.reimbursementExportTypeSelect = reimbursementExportTypeSelect;
	}

	public Integer getDebtRecoveryTypeSelect() {
		return debtRecoveryTypeSelect == null ? 0 : debtRecoveryTypeSelect;
	}

	public void setDebtRecoveryTypeSelect(Integer debtRecoveryTypeSelect) {
		this.debtRecoveryTypeSelect = debtRecoveryTypeSelect;
	}

	public Boolean getUpdateCustAccountOk() {
		return updateCustAccountOk == null ? Boolean.FALSE : updateCustAccountOk;
	}

	public void setUpdateCustAccountOk(Boolean updateCustAccountOk) {
		this.updateCustAccountOk = updateCustAccountOk;
	}

	public Boolean getUpdateDueCustAccountOk() {
		return updateDueCustAccountOk == null ? Boolean.FALSE : updateDueCustAccountOk;
	}

	public void setUpdateDueCustAccountOk(Boolean updateDueCustAccountOk) {
		this.updateDueCustAccountOk = updateDueCustAccountOk;
	}

	public Boolean getUpdateDueDebtRecoveryCustAccountOk() {
		return updateDueDebtRecoveryCustAccountOk == null ? Boolean.FALSE : updateDueDebtRecoveryCustAccountOk;
	}

	public void setUpdateDueDebtRecoveryCustAccountOk(Boolean updateDueDebtRecoveryCustAccountOk) {
		this.updateDueDebtRecoveryCustAccountOk = updateDueDebtRecoveryCustAccountOk;
	}

	public Integer getMoveLineExportTypeSelect() {
		return moveLineExportTypeSelect == null ? 0 : moveLineExportTypeSelect;
	}

	public void setMoveLineExportTypeSelect(Integer moveLineExportTypeSelect) {
		this.moveLineExportTypeSelect = moveLineExportTypeSelect;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Batch> getBatchList() {
		return batchList;
	}

	public void setBatchList(List<Batch> batchList) {
		this.batchList = batchList;
	}

	/**
	 * Add the given {@link Batch} item to the {@code batchList}.
	 *
	 * <p>
	 * It sets {@code item.accountingBatch = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBatchListItem(Batch item) {
		if (getBatchList() == null) {
			setBatchList(new ArrayList<>());
		}
		getBatchList().add(item);
		item.setAccountingBatch(this);
	}

	/**
	 * Remove the given {@link Batch} item from the {@code batchList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBatchListItem(Batch item) {
		if (getBatchList() == null) {
			return;
		}
		getBatchList().remove(item);
	}

	/**
	 * Clear the {@code batchList} collection.
	 *
	 * <p>
	 * If you have to query {@link Batch} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearBatchList() {
		if (getBatchList() != null) {
			getBatchList().clear();
		}
	}

	public Integer getCreditTransferTypeSelect() {
		return creditTransferTypeSelect == null ? 0 : creditTransferTypeSelect;
	}

	public void setCreditTransferTypeSelect(Integer creditTransferTypeSelect) {
		this.creditTransferTypeSelect = creditTransferTypeSelect;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Integer getCustomerReimbursementTypeSelect() {
		return customerReimbursementTypeSelect == null ? 0 : customerReimbursementTypeSelect;
	}

	public void setCustomerReimbursementTypeSelect(Integer customerReimbursementTypeSelect) {
		this.customerReimbursementTypeSelect = customerReimbursementTypeSelect;
	}

	public Boolean getOpenYear() {
		return openYear == null ? Boolean.FALSE : openYear;
	}

	public void setOpenYear(Boolean openYear) {
		this.openYear = openYear;
	}

	public Boolean getCloseYear() {
		return closeYear == null ? Boolean.FALSE : closeYear;
	}

	public void setCloseYear(Boolean closeYear) {
		this.closeYear = closeYear;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Set<Account> getAccountSet() {
		return accountSet;
	}

	public void setAccountSet(Set<Account> accountSet) {
		this.accountSet = accountSet;
	}

	/**
	 * Add the given {@link Account} item to the {@code accountSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAccountSetItem(Account item) {
		if (getAccountSet() == null) {
			setAccountSet(new HashSet<>());
		}
		getAccountSet().add(item);
	}

	/**
	 * Remove the given {@link Account} item from the {@code accountSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAccountSetItem(Account item) {
		if (getAccountSet() == null) {
			return;
		}
		getAccountSet().remove(item);
	}

	/**
	 * Clear the {@code accountSet} collection.
	 *
	 */
	public void clearAccountSet() {
		if (getAccountSet() != null) {
			getAccountSet().clear();
		}
	}

	public Boolean getAllocatePerPartner() {
		return allocatePerPartner == null ? Boolean.FALSE : allocatePerPartner;
	}

	public void setAllocatePerPartner(Boolean allocatePerPartner) {
		this.allocatePerPartner = allocatePerPartner;
	}

	public String getMoveDescription() {
		return moveDescription;
	}

	public void setMoveDescription(String moveDescription) {
		this.moveDescription = moveDescription;
	}

	public Boolean getUpdateAllRealizedFixedAssetLines() {
		return updateAllRealizedFixedAssetLines == null ? Boolean.FALSE : updateAllRealizedFixedAssetLines;
	}

	public void setUpdateAllRealizedFixedAssetLines(Boolean updateAllRealizedFixedAssetLines) {
		this.updateAllRealizedFixedAssetLines = updateAllRealizedFixedAssetLines;
	}

	public Integer getDirectDebitDataTypeSelect() {
		return directDebitDataTypeSelect == null ? 0 : directDebitDataTypeSelect;
	}

	public void setDirectDebitDataTypeSelect(Integer directDebitDataTypeSelect) {
		this.directDebitDataTypeSelect = directDebitDataTypeSelect;
	}

	public String getAttrs() {
		return attrs;
	}

	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof AccountingBatch)) return false;

		final AccountingBatch other = (AccountingBatch) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getCode(), other.getCode())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(-115363451, this.getCode());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("code", getCode())
			.add("actionSelect", getActionSelect())
			.add("dueDate", getDueDate())
			.add("includeOtherBankAccounts", getIncludeOtherBankAccounts())
			.add("reimbursementTypeSelect", getReimbursementTypeSelect())
			.add("reimbursementExportTypeSelect", getReimbursementExportTypeSelect())
			.add("debtRecoveryTypeSelect", getDebtRecoveryTypeSelect())
			.add("updateCustAccountOk", getUpdateCustAccountOk())
			.add("updateDueCustAccountOk", getUpdateDueCustAccountOk())
			.add("updateDueDebtRecoveryCustAccountOk", getUpdateDueDebtRecoveryCustAccountOk())
			.add("moveLineExportTypeSelect", getMoveLineExportTypeSelect())
			.omitNullValues()
			.toString();
	}
}
