package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axelor.apps.base.db.Partner;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackEvent;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.TrackMessage;
import com.axelor.db.annotations.VirtualColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "ACCOUNT_MOVE_LINE", indexes = { @Index(columnList = "move"), @Index(columnList = "partner"), @Index(columnList = "account"), @Index(columnList = "invoice_reject"), @Index(columnList = "interbank_code_line"), @Index(columnList = "tax_line"), @Index(columnList = "source_tax_line"), @Index(columnList = "payment_schedule_line"), @Index(columnList = "account_clearance"), @Index(columnList = "management_object"), @Index(columnList = "analytic_distribution_template"), @Index(columnList = "reconcile_group"), @Index(columnList = "name") })
@Track(fields = { @TrackField(name = "date"), @TrackField(name = "partner"), @TrackField(name = "account"), @TrackField(name = "debit"), @TrackField(name = "credit"), @TrackField(name = "origin"), @TrackField(name = "description") }, messages = { @TrackMessage(message = "Move line created", condition = "true", on = TrackEvent.CREATE) })
public class MoveLine extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_MOVE_LINE_SEQ")
	@SequenceGenerator(name = "ACCOUNT_MOVE_LINE_SEQ", sequenceName = "ACCOUNT_MOVE_LINE_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Move")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Move move;

	@Widget(title = "Partner")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner partner;

	@Widget(title = "Accounting.Account")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Account account;

	@Widget(title = "Date")
	@Column(name = "date_val")
	private LocalDate date;

	@Widget(title = "Due Date")
	private LocalDate dueDate;

	@Widget(title = "Line Nbr.")
	private Integer counter = 0;

	@Widget(title = "Debit")
	private BigDecimal debit = BigDecimal.ZERO;

	@Widget(title = "Credit")
	private BigDecimal credit = BigDecimal.ZERO;

	@Widget(title = "Description")
	private String description;

	@Widget(title = "Origin")
	private String origin;

	@Widget(title = "Debit Reconcile list", readonly = false)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "debitMoveLine", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Reconcile> debitReconcileList;

	@Widget(title = "Credit Reconcile List", readonly = false)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "creditMoveLine", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Reconcile> creditReconcileList;

	@Widget(title = "MoveLine.amountReconciled", readonly = true)
	private BigDecimal amountPaid = new BigDecimal("0.0");

	@Widget(title = "Invoice rejected")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Invoice invoiceReject;

	@Widget(title = "Exported Direct Debit")
	private Boolean exportedDirectDebitOk = Boolean.FALSE;

	@Widget(title = "Rejection reason")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InterbankCodeLine interbankCodeLine;

	@Widget(title = "Reimbursement status", selection = "move.line.reimbursement.status.select")
	private Integer reimbursementStatusSelect = 0;

	@Widget(title = "Irrecoverable Shift Reason", readonly = true)
	private String passageReason;

	@Widget(title = "Transfer(ed) to Usher")
	private Boolean usherPassageOk = Boolean.FALSE;

	@Widget(title = "Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine taxLine;

	@Widget(title = "Source Tax")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private TaxLine sourceTaxLine;

	@Widget(title = "Currency Rate")
	@Digits(integer = 15, fraction = 5)
	private BigDecimal currencyRate = BigDecimal.ZERO;

	@Widget(title = "Amount in Acc. currency")
	private BigDecimal currencyAmount = BigDecimal.ZERO;

	@Widget(title = "Max Amount to reconcile")
	private BigDecimal maxAmountToReconcile = BigDecimal.ZERO;

	@Widget(title = "Payment Schedule line")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentScheduleLine paymentScheduleLine;

	@Widget(title = "Amount exported for direct debit")
	private BigDecimal amountExportedInDirectDebit = BigDecimal.ZERO;

	@Widget(title = "Account clearance")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AccountClearance accountClearance;

	@Widget(title = "Irrecoverable status", readonly = true, selection = "iaccount.account.schedule.irrecoverable.status.select")
	private Integer irrecoverableStatusSelect = 0;

	@Widget(title = "Irrecoverable shift reason")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ManagementObject managementObject;

	@Widget(title = "Analytic distribution lines")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moveLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AnalyticMoveLine> analyticMoveLineList;

	@Widget(title = "Analytic distribution template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(readonly = true)
	private String accountCode;

	@Widget(readonly = true)
	private String accountName;

	@Widget(readonly = true)
	private String partnerFullName;

	@Widget(readonly = true)
	private String partnerSeq;

	private Long partnerId = 0L;

	private Long accountId = 0L;

	@Widget(readonly = true)
	@Digits(integer = 17, fraction = 3)
	private BigDecimal taxRate = BigDecimal.ZERO;

	@Widget(readonly = true)
	private String taxCode;

	@Widget(title = "Tax amount", readonly = true)
	@Digits(integer = 18, fraction = 2)
	private BigDecimal taxAmount = BigDecimal.ZERO;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moveLine", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaxPaymentMoveLine> taxPaymentMoveLineList;

	@Widget(title = "Reconcile Group")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private ReconcileGroup reconcileGroup;

	@Widget(title = "Origin date")
	private LocalDate originDate;

	@Widget(title = "Name")
	@NameColumn
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private String name;

	@Widget(title = "To pay / To use")
	@VirtualColumn
	@Access(AccessType.PROPERTY)
	private BigDecimal amountRemaining = BigDecimal.ZERO;

	@Widget(title = "Bank reconciled amount")
	private BigDecimal bankReconciledAmount = BigDecimal.ZERO;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public MoveLine() {
	}

	public MoveLine(Move move, Partner partner, Account account, LocalDate date, LocalDate dueDate, Integer counter, BigDecimal debit, BigDecimal credit, String description, String origin, BigDecimal currencyRate, BigDecimal currencyAmount, LocalDate originDate) {
		this.move = move;
		this.partner = partner;
		this.account = account;
		this.date = date;
		this.dueDate = dueDate;
		this.counter = counter;
		this.debit = debit;
		this.credit = credit;
		this.description = description;
		this.origin = origin;
		this.currencyRate = currencyRate;
		this.currencyAmount = currencyAmount;
		this.originDate = originDate;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getCounter() {
		return counter == null ? 0 : counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public BigDecimal getDebit() {
		return debit == null ? BigDecimal.ZERO : debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public BigDecimal getCredit() {
		return credit == null ? BigDecimal.ZERO : credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public List<Reconcile> getDebitReconcileList() {
		return debitReconcileList;
	}

	public void setDebitReconcileList(List<Reconcile> debitReconcileList) {
		this.debitReconcileList = debitReconcileList;
	}

	/**
	 * Add the given {@link Reconcile} item to the {@code debitReconcileList}.
	 *
	 * <p>
	 * It sets {@code item.debitMoveLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addDebitReconcileListItem(Reconcile item) {
		if (getDebitReconcileList() == null) {
			setDebitReconcileList(new ArrayList<>());
		}
		getDebitReconcileList().add(item);
		item.setDebitMoveLine(this);
	}

	/**
	 * Remove the given {@link Reconcile} item from the {@code debitReconcileList}.
	 *
	 * <p>
	 * It sets {@code item.debitMoveLine = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeDebitReconcileListItem(Reconcile item) {
		if (getDebitReconcileList() == null) {
			return;
		}
		getDebitReconcileList().remove(item);
		item.setDebitMoveLine(null);
	}

	/**
	 * Clear the {@code debitReconcileList} collection.
	 *
	 * <p>
	 * It sets {@code item.debitMoveLine = null} to break the relationship.
	 * </p>
	 */
	public void clearDebitReconcileList() {
		if (getDebitReconcileList() != null) {
			for (Reconcile item : getDebitReconcileList()) {
        item.setDebitMoveLine(null);
      }
			getDebitReconcileList().clear();
		}
	}

	public List<Reconcile> getCreditReconcileList() {
		return creditReconcileList;
	}

	public void setCreditReconcileList(List<Reconcile> creditReconcileList) {
		this.creditReconcileList = creditReconcileList;
	}

	/**
	 * Add the given {@link Reconcile} item to the {@code creditReconcileList}.
	 *
	 * <p>
	 * It sets {@code item.creditMoveLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addCreditReconcileListItem(Reconcile item) {
		if (getCreditReconcileList() == null) {
			setCreditReconcileList(new ArrayList<>());
		}
		getCreditReconcileList().add(item);
		item.setCreditMoveLine(this);
	}

	/**
	 * Remove the given {@link Reconcile} item from the {@code creditReconcileList}.
	 *
	 * <p>
	 * It sets {@code item.creditMoveLine = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeCreditReconcileListItem(Reconcile item) {
		if (getCreditReconcileList() == null) {
			return;
		}
		getCreditReconcileList().remove(item);
		item.setCreditMoveLine(null);
	}

	/**
	 * Clear the {@code creditReconcileList} collection.
	 *
	 * <p>
	 * It sets {@code item.creditMoveLine = null} to break the relationship.
	 * </p>
	 */
	public void clearCreditReconcileList() {
		if (getCreditReconcileList() != null) {
			for (Reconcile item : getCreditReconcileList()) {
        item.setCreditMoveLine(null);
      }
			getCreditReconcileList().clear();
		}
	}

	public BigDecimal getAmountPaid() {
		return amountPaid == null ? BigDecimal.ZERO : amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Invoice getInvoiceReject() {
		return invoiceReject;
	}

	public void setInvoiceReject(Invoice invoiceReject) {
		this.invoiceReject = invoiceReject;
	}

	public Boolean getExportedDirectDebitOk() {
		return exportedDirectDebitOk == null ? Boolean.FALSE : exportedDirectDebitOk;
	}

	public void setExportedDirectDebitOk(Boolean exportedDirectDebitOk) {
		this.exportedDirectDebitOk = exportedDirectDebitOk;
	}

	public InterbankCodeLine getInterbankCodeLine() {
		return interbankCodeLine;
	}

	public void setInterbankCodeLine(InterbankCodeLine interbankCodeLine) {
		this.interbankCodeLine = interbankCodeLine;
	}

	public Integer getReimbursementStatusSelect() {
		return reimbursementStatusSelect == null ? 0 : reimbursementStatusSelect;
	}

	public void setReimbursementStatusSelect(Integer reimbursementStatusSelect) {
		this.reimbursementStatusSelect = reimbursementStatusSelect;
	}

	public String getPassageReason() {
		return passageReason;
	}

	public void setPassageReason(String passageReason) {
		this.passageReason = passageReason;
	}

	public Boolean getUsherPassageOk() {
		return usherPassageOk == null ? Boolean.FALSE : usherPassageOk;
	}

	public void setUsherPassageOk(Boolean usherPassageOk) {
		this.usherPassageOk = usherPassageOk;
	}

	public TaxLine getTaxLine() {
		return taxLine;
	}

	public void setTaxLine(TaxLine taxLine) {
		this.taxLine = taxLine;
	}

	public TaxLine getSourceTaxLine() {
		return sourceTaxLine;
	}

	public void setSourceTaxLine(TaxLine sourceTaxLine) {
		this.sourceTaxLine = sourceTaxLine;
	}

	public BigDecimal getCurrencyRate() {
		return currencyRate == null ? BigDecimal.ZERO : currencyRate;
	}

	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	public BigDecimal getCurrencyAmount() {
		return currencyAmount == null ? BigDecimal.ZERO : currencyAmount;
	}

	public void setCurrencyAmount(BigDecimal currencyAmount) {
		this.currencyAmount = currencyAmount;
	}

	public BigDecimal getMaxAmountToReconcile() {
		return maxAmountToReconcile == null ? BigDecimal.ZERO : maxAmountToReconcile;
	}

	public void setMaxAmountToReconcile(BigDecimal maxAmountToReconcile) {
		this.maxAmountToReconcile = maxAmountToReconcile;
	}

	public PaymentScheduleLine getPaymentScheduleLine() {
		return paymentScheduleLine;
	}

	public void setPaymentScheduleLine(PaymentScheduleLine paymentScheduleLine) {
		this.paymentScheduleLine = paymentScheduleLine;
	}

	public BigDecimal getAmountExportedInDirectDebit() {
		return amountExportedInDirectDebit == null ? BigDecimal.ZERO : amountExportedInDirectDebit;
	}

	public void setAmountExportedInDirectDebit(BigDecimal amountExportedInDirectDebit) {
		this.amountExportedInDirectDebit = amountExportedInDirectDebit;
	}

	public AccountClearance getAccountClearance() {
		return accountClearance;
	}

	public void setAccountClearance(AccountClearance accountClearance) {
		this.accountClearance = accountClearance;
	}

	public Integer getIrrecoverableStatusSelect() {
		return irrecoverableStatusSelect == null ? 0 : irrecoverableStatusSelect;
	}

	public void setIrrecoverableStatusSelect(Integer irrecoverableStatusSelect) {
		this.irrecoverableStatusSelect = irrecoverableStatusSelect;
	}

	public ManagementObject getManagementObject() {
		return managementObject;
	}

	public void setManagementObject(ManagementObject managementObject) {
		this.managementObject = managementObject;
	}

	public List<AnalyticMoveLine> getAnalyticMoveLineList() {
		return analyticMoveLineList;
	}

	public void setAnalyticMoveLineList(List<AnalyticMoveLine> analyticMoveLineList) {
		this.analyticMoveLineList = analyticMoveLineList;
	}

	/**
	 * Add the given {@link AnalyticMoveLine} item to the {@code analyticMoveLineList}.
	 *
	 * <p>
	 * It sets {@code item.moveLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAnalyticMoveLineListItem(AnalyticMoveLine item) {
		if (getAnalyticMoveLineList() == null) {
			setAnalyticMoveLineList(new ArrayList<>());
		}
		getAnalyticMoveLineList().add(item);
		item.setMoveLine(this);
	}

	/**
	 * Remove the given {@link AnalyticMoveLine} item from the {@code analyticMoveLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAnalyticMoveLineListItem(AnalyticMoveLine item) {
		if (getAnalyticMoveLineList() == null) {
			return;
		}
		getAnalyticMoveLineList().remove(item);
	}

	/**
	 * Clear the {@code analyticMoveLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link AnalyticMoveLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAnalyticMoveLineList() {
		if (getAnalyticMoveLineList() != null) {
			getAnalyticMoveLineList().clear();
		}
	}

	public AnalyticDistributionTemplate getAnalyticDistributionTemplate() {
		return analyticDistributionTemplate;
	}

	public void setAnalyticDistributionTemplate(AnalyticDistributionTemplate analyticDistributionTemplate) {
		this.analyticDistributionTemplate = analyticDistributionTemplate;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPartnerFullName() {
		return partnerFullName;
	}

	public void setPartnerFullName(String partnerFullName) {
		this.partnerFullName = partnerFullName;
	}

	public String getPartnerSeq() {
		return partnerSeq;
	}

	public void setPartnerSeq(String partnerSeq) {
		this.partnerSeq = partnerSeq;
	}

	public Long getPartnerId() {
		return partnerId == null ? 0L : partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	public Long getAccountId() {
		return accountId == null ? 0L : accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public BigDecimal getTaxRate() {
		return taxRate == null ? BigDecimal.ZERO : taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount == null ? BigDecimal.ZERO : taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public List<TaxPaymentMoveLine> getTaxPaymentMoveLineList() {
		return taxPaymentMoveLineList;
	}

	public void setTaxPaymentMoveLineList(List<TaxPaymentMoveLine> taxPaymentMoveLineList) {
		this.taxPaymentMoveLineList = taxPaymentMoveLineList;
	}

	/**
	 * Add the given {@link TaxPaymentMoveLine} item to the {@code taxPaymentMoveLineList}.
	 *
	 * <p>
	 * It sets {@code item.moveLine = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTaxPaymentMoveLineListItem(TaxPaymentMoveLine item) {
		if (getTaxPaymentMoveLineList() == null) {
			setTaxPaymentMoveLineList(new ArrayList<>());
		}
		getTaxPaymentMoveLineList().add(item);
		item.setMoveLine(this);
	}

	/**
	 * Remove the given {@link TaxPaymentMoveLine} item from the {@code taxPaymentMoveLineList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTaxPaymentMoveLineListItem(TaxPaymentMoveLine item) {
		if (getTaxPaymentMoveLineList() == null) {
			return;
		}
		getTaxPaymentMoveLineList().remove(item);
	}

	/**
	 * Clear the {@code taxPaymentMoveLineList} collection.
	 *
	 * <p>
	 * If you have to query {@link TaxPaymentMoveLine} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTaxPaymentMoveLineList() {
		if (getTaxPaymentMoveLineList() != null) {
			getTaxPaymentMoveLineList().clear();
		}
	}

	public ReconcileGroup getReconcileGroup() {
		return reconcileGroup;
	}

	public void setReconcileGroup(ReconcileGroup reconcileGroup) {
		this.reconcileGroup = reconcileGroup;
	}

	public LocalDate getOriginDate() {
		return originDate;
	}

	public void setOriginDate(LocalDate originDate) {
		this.originDate = originDate;
	}

	public String getName() {
		try {
			name = computeName();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getName()", e);
		}
		return name;
	}

	protected String computeName() {
		if (move != null && move.getReference() != null){
			return move.getReference() + "-" + Integer.toString(counter);
		}
		else {
			return Integer.toString(counter);
		 }
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmountRemaining() {
		try {
			amountRemaining = computeAmountRemaining();
		} catch (NullPointerException e) {
			Logger logger = LoggerFactory.getLogger(getClass());
			logger.error("NPE in function field: getAmountRemaining()", e);
		}
		return amountRemaining;
	}

	protected BigDecimal computeAmountRemaining() {
		if (account != null && account.getReconcileOk()){
			if (credit.compareTo(BigDecimal.ZERO) == 1) return credit.subtract(amountPaid);
			else if (debit.compareTo(BigDecimal.ZERO) == 1) return debit.subtract(amountPaid);
			else return BigDecimal.ZERO;
		}
		else return BigDecimal.ZERO;
	}

	public void setAmountRemaining(BigDecimal amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public BigDecimal getBankReconciledAmount() {
		return bankReconciledAmount == null ? BigDecimal.ZERO : bankReconciledAmount;
	}

	public void setBankReconciledAmount(BigDecimal bankReconciledAmount) {
		this.bankReconciledAmount = bankReconciledAmount;
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
		if (!(obj instanceof MoveLine)) return false;

		final MoveLine other = (MoveLine) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("date", getDate())
			.add("dueDate", getDueDate())
			.add("counter", getCounter())
			.add("debit", getDebit())
			.add("credit", getCredit())
			.add("description", getDescription())
			.add("origin", getOrigin())
			.add("amountPaid", getAmountPaid())
			.add("exportedDirectDebitOk", getExportedDirectDebitOk())
			.add("reimbursementStatusSelect", getReimbursementStatusSelect())
			.add("passageReason", getPassageReason())
			.omitNullValues()
			.toString();
	}
}
