package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Batch;
import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Currency;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Period;
import com.axelor.apps.base.db.Year;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_ACCOUNTING_REPORT", uniqueConstraints = { @UniqueConstraint(columnNames = { "ref", "company" }) }, indexes = { @Index(columnList = "ref"), @Index(columnList = "company"), @Index(columnList = "currency"), @Index(columnList = "period"), @Index(columnList = "journal"), @Index(columnList = "payment_mode"), @Index(columnList = "year"), @Index(columnList = "analytic_journal"), @Index(columnList = "analytic_distribution_template") })
public class AccountingReport extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ACCOUNTING_REPORT_SEQ")
	@SequenceGenerator(name = "ACCOUNT_ACCOUNTING_REPORT_SEQ", sequenceName = "ACCOUNT_ACCOUNTING_REPORT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Ref.")
	@NameColumn
	private String ref;

	@Widget(title = "From")
	private LocalDate dateFrom;

	@Widget(title = "To")
	private LocalDate dateTo;

	@Widget(title = "Company")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Currency")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Period")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Period period;

	@Widget(title = "Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Journal journal;

	@Widget(title = "Payment Mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Accounting.Accounts")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accountSet;

	@Widget(title = "Export type", selection = "iadministration.export.type.select")
	private String exportTypeSelect;

	@Widget(title = "Printing output", selection = "accounting.report.type.select")
	@NotNull
	private Integer typeSelect = 0;

	@Widget(title = "Partners")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Partner> partnerSet;

	@Widget(title = "Fiscal year")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Year year;

	@Widget(title = "Closing date")
	@NotNull
	@Column(name = "date_val")
	private LocalDate date;

	@Widget(title = "Last print date", readonly = true)
	private ZonedDateTime publicationDateTime;

	@Widget(title = "Global")
	private Boolean global = Boolean.FALSE;

	@Widget(title = "Subtotal by partner")
	private Boolean globalByPartner = Boolean.FALSE;

	@Widget(title = "Subtotal by date")
	private Boolean globalByDate = Boolean.FALSE;

	@Widget(title = "Detailed")
	private Boolean detailed = Boolean.FALSE;

	@Widget(title = "Status", readonly = true, selection = "iaccount.accounting.report.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Total Debit", readonly = true)
	private BigDecimal totalDebit = BigDecimal.ZERO;

	@Widget(title = "Total Credit", readonly = true)
	private BigDecimal totalCredit = BigDecimal.ZERO;

	@Widget(title = "Balance", readonly = true)
	private BigDecimal balance = BigDecimal.ZERO;

	@Widget(title = "Batchs")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Batch> batchSet;

	@Widget(title = "Display closing accounting moves")
	private Boolean displayClosingAccountingMoves = Boolean.FALSE;

	@Widget(title = "Display opening accounting moves")
	private Boolean displayOpeningAccountingMoves = Boolean.TRUE;

	@Widget(title = "Display only not completely lettered move lines")
	private Boolean displayOnlyNotCompletelyLetteredMoveLines = Boolean.FALSE;

	@Widget(title = "Display cumulative balance on printing")
	private Boolean displayCumulativeBalance = Boolean.FALSE;

	@Widget(title = "Display column with accounting number")
	private Boolean displayCodeAccountColumnOnPrinting = Boolean.FALSE;

	@Widget(title = "Display column with accounting name")
	private Boolean displayNameAccountColumnOnPrinting = Boolean.FALSE;

	@Widget(title = "Display moveline sequence")
	private Boolean displayMoveLineSequenceOnPrinting = Boolean.FALSE;

	@Widget(title = "Analytic Journal")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticJournal analyticJournal;

	@Widget(title = "Analytic axis")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<AnalyticAxis> analyticAxisSet;

	@Widget(title = "Analytic accounts")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<AnalyticAccount> analyticAccountSet;

	@Widget(title = "Account types")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<AccountType> accountTypeSet;

	@Widget(title = "Analytic Distribution Template")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AnalyticDistributionTemplate analyticDistributionTemplate;

	@Widget(title = "Subtotal by analytic distribution")
	private Boolean subtotalByAnalyticDistribution = Boolean.FALSE;

	@Widget(title = "Bank details")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<BankDetails> bankDetailsSet;

	@Widget(title = "Journals")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Journal> journalSet;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public AccountingReport() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public Journal getJournal() {
		return journal;
	}

	public void setJournal(Journal journal) {
		this.journal = journal;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
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

	public String getExportTypeSelect() {
		return exportTypeSelect;
	}

	public void setExportTypeSelect(String exportTypeSelect) {
		this.exportTypeSelect = exportTypeSelect;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public Set<Partner> getPartnerSet() {
		return partnerSet;
	}

	public void setPartnerSet(Set<Partner> partnerSet) {
		this.partnerSet = partnerSet;
	}

	/**
	 * Add the given {@link Partner} item to the {@code partnerSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addPartnerSetItem(Partner item) {
		if (getPartnerSet() == null) {
			setPartnerSet(new HashSet<>());
		}
		getPartnerSet().add(item);
	}

	/**
	 * Remove the given {@link Partner} item from the {@code partnerSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removePartnerSetItem(Partner item) {
		if (getPartnerSet() == null) {
			return;
		}
		getPartnerSet().remove(item);
	}

	/**
	 * Clear the {@code partnerSet} collection.
	 *
	 */
	public void clearPartnerSet() {
		if (getPartnerSet() != null) {
			getPartnerSet().clear();
		}
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public ZonedDateTime getPublicationDateTime() {
		return publicationDateTime;
	}

	public void setPublicationDateTime(ZonedDateTime publicationDateTime) {
		this.publicationDateTime = publicationDateTime;
	}

	public Boolean getGlobal() {
		return global == null ? Boolean.FALSE : global;
	}

	public void setGlobal(Boolean global) {
		this.global = global;
	}

	public Boolean getGlobalByPartner() {
		return globalByPartner == null ? Boolean.FALSE : globalByPartner;
	}

	public void setGlobalByPartner(Boolean globalByPartner) {
		this.globalByPartner = globalByPartner;
	}

	public Boolean getGlobalByDate() {
		return globalByDate == null ? Boolean.FALSE : globalByDate;
	}

	public void setGlobalByDate(Boolean globalByDate) {
		this.globalByDate = globalByDate;
	}

	public Boolean getDetailed() {
		return detailed == null ? Boolean.FALSE : detailed;
	}

	public void setDetailed(Boolean detailed) {
		this.detailed = detailed;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public BigDecimal getTotalDebit() {
		return totalDebit == null ? BigDecimal.ZERO : totalDebit;
	}

	public void setTotalDebit(BigDecimal totalDebit) {
		this.totalDebit = totalDebit;
	}

	public BigDecimal getTotalCredit() {
		return totalCredit == null ? BigDecimal.ZERO : totalCredit;
	}

	public void setTotalCredit(BigDecimal totalCredit) {
		this.totalCredit = totalCredit;
	}

	public BigDecimal getBalance() {
		return balance == null ? BigDecimal.ZERO : balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Set<Batch> getBatchSet() {
		return batchSet;
	}

	public void setBatchSet(Set<Batch> batchSet) {
		this.batchSet = batchSet;
	}

	/**
	 * Add the given {@link Batch} item to the {@code batchSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBatchSetItem(Batch item) {
		if (getBatchSet() == null) {
			setBatchSet(new HashSet<>());
		}
		getBatchSet().add(item);
	}

	/**
	 * Remove the given {@link Batch} item from the {@code batchSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBatchSetItem(Batch item) {
		if (getBatchSet() == null) {
			return;
		}
		getBatchSet().remove(item);
	}

	/**
	 * Clear the {@code batchSet} collection.
	 *
	 */
	public void clearBatchSet() {
		if (getBatchSet() != null) {
			getBatchSet().clear();
		}
	}

	public Boolean getDisplayClosingAccountingMoves() {
		return displayClosingAccountingMoves == null ? Boolean.FALSE : displayClosingAccountingMoves;
	}

	public void setDisplayClosingAccountingMoves(Boolean displayClosingAccountingMoves) {
		this.displayClosingAccountingMoves = displayClosingAccountingMoves;
	}

	public Boolean getDisplayOpeningAccountingMoves() {
		return displayOpeningAccountingMoves == null ? Boolean.FALSE : displayOpeningAccountingMoves;
	}

	public void setDisplayOpeningAccountingMoves(Boolean displayOpeningAccountingMoves) {
		this.displayOpeningAccountingMoves = displayOpeningAccountingMoves;
	}

	public Boolean getDisplayOnlyNotCompletelyLetteredMoveLines() {
		return displayOnlyNotCompletelyLetteredMoveLines == null ? Boolean.FALSE : displayOnlyNotCompletelyLetteredMoveLines;
	}

	public void setDisplayOnlyNotCompletelyLetteredMoveLines(Boolean displayOnlyNotCompletelyLetteredMoveLines) {
		this.displayOnlyNotCompletelyLetteredMoveLines = displayOnlyNotCompletelyLetteredMoveLines;
	}

	public Boolean getDisplayCumulativeBalance() {
		return displayCumulativeBalance == null ? Boolean.FALSE : displayCumulativeBalance;
	}

	public void setDisplayCumulativeBalance(Boolean displayCumulativeBalance) {
		this.displayCumulativeBalance = displayCumulativeBalance;
	}

	public Boolean getDisplayCodeAccountColumnOnPrinting() {
		return displayCodeAccountColumnOnPrinting == null ? Boolean.FALSE : displayCodeAccountColumnOnPrinting;
	}

	public void setDisplayCodeAccountColumnOnPrinting(Boolean displayCodeAccountColumnOnPrinting) {
		this.displayCodeAccountColumnOnPrinting = displayCodeAccountColumnOnPrinting;
	}

	public Boolean getDisplayNameAccountColumnOnPrinting() {
		return displayNameAccountColumnOnPrinting == null ? Boolean.FALSE : displayNameAccountColumnOnPrinting;
	}

	public void setDisplayNameAccountColumnOnPrinting(Boolean displayNameAccountColumnOnPrinting) {
		this.displayNameAccountColumnOnPrinting = displayNameAccountColumnOnPrinting;
	}

	public Boolean getDisplayMoveLineSequenceOnPrinting() {
		return displayMoveLineSequenceOnPrinting == null ? Boolean.FALSE : displayMoveLineSequenceOnPrinting;
	}

	public void setDisplayMoveLineSequenceOnPrinting(Boolean displayMoveLineSequenceOnPrinting) {
		this.displayMoveLineSequenceOnPrinting = displayMoveLineSequenceOnPrinting;
	}

	public AnalyticJournal getAnalyticJournal() {
		return analyticJournal;
	}

	public void setAnalyticJournal(AnalyticJournal analyticJournal) {
		this.analyticJournal = analyticJournal;
	}

	public Set<AnalyticAxis> getAnalyticAxisSet() {
		return analyticAxisSet;
	}

	public void setAnalyticAxisSet(Set<AnalyticAxis> analyticAxisSet) {
		this.analyticAxisSet = analyticAxisSet;
	}

	/**
	 * Add the given {@link AnalyticAxis} item to the {@code analyticAxisSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAnalyticAxisSetItem(AnalyticAxis item) {
		if (getAnalyticAxisSet() == null) {
			setAnalyticAxisSet(new HashSet<>());
		}
		getAnalyticAxisSet().add(item);
	}

	/**
	 * Remove the given {@link AnalyticAxis} item from the {@code analyticAxisSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAnalyticAxisSetItem(AnalyticAxis item) {
		if (getAnalyticAxisSet() == null) {
			return;
		}
		getAnalyticAxisSet().remove(item);
	}

	/**
	 * Clear the {@code analyticAxisSet} collection.
	 *
	 */
	public void clearAnalyticAxisSet() {
		if (getAnalyticAxisSet() != null) {
			getAnalyticAxisSet().clear();
		}
	}

	public Set<AnalyticAccount> getAnalyticAccountSet() {
		return analyticAccountSet;
	}

	public void setAnalyticAccountSet(Set<AnalyticAccount> analyticAccountSet) {
		this.analyticAccountSet = analyticAccountSet;
	}

	/**
	 * Add the given {@link AnalyticAccount} item to the {@code analyticAccountSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAnalyticAccountSetItem(AnalyticAccount item) {
		if (getAnalyticAccountSet() == null) {
			setAnalyticAccountSet(new HashSet<>());
		}
		getAnalyticAccountSet().add(item);
	}

	/**
	 * Remove the given {@link AnalyticAccount} item from the {@code analyticAccountSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAnalyticAccountSetItem(AnalyticAccount item) {
		if (getAnalyticAccountSet() == null) {
			return;
		}
		getAnalyticAccountSet().remove(item);
	}

	/**
	 * Clear the {@code analyticAccountSet} collection.
	 *
	 */
	public void clearAnalyticAccountSet() {
		if (getAnalyticAccountSet() != null) {
			getAnalyticAccountSet().clear();
		}
	}

	public Set<AccountType> getAccountTypeSet() {
		return accountTypeSet;
	}

	public void setAccountTypeSet(Set<AccountType> accountTypeSet) {
		this.accountTypeSet = accountTypeSet;
	}

	/**
	 * Add the given {@link AccountType} item to the {@code accountTypeSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAccountTypeSetItem(AccountType item) {
		if (getAccountTypeSet() == null) {
			setAccountTypeSet(new HashSet<>());
		}
		getAccountTypeSet().add(item);
	}

	/**
	 * Remove the given {@link AccountType} item from the {@code accountTypeSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAccountTypeSetItem(AccountType item) {
		if (getAccountTypeSet() == null) {
			return;
		}
		getAccountTypeSet().remove(item);
	}

	/**
	 * Clear the {@code accountTypeSet} collection.
	 *
	 */
	public void clearAccountTypeSet() {
		if (getAccountTypeSet() != null) {
			getAccountTypeSet().clear();
		}
	}

	public AnalyticDistributionTemplate getAnalyticDistributionTemplate() {
		return analyticDistributionTemplate;
	}

	public void setAnalyticDistributionTemplate(AnalyticDistributionTemplate analyticDistributionTemplate) {
		this.analyticDistributionTemplate = analyticDistributionTemplate;
	}

	public Boolean getSubtotalByAnalyticDistribution() {
		return subtotalByAnalyticDistribution == null ? Boolean.FALSE : subtotalByAnalyticDistribution;
	}

	public void setSubtotalByAnalyticDistribution(Boolean subtotalByAnalyticDistribution) {
		this.subtotalByAnalyticDistribution = subtotalByAnalyticDistribution;
	}

	public Set<BankDetails> getBankDetailsSet() {
		return bankDetailsSet;
	}

	public void setBankDetailsSet(Set<BankDetails> bankDetailsSet) {
		this.bankDetailsSet = bankDetailsSet;
	}

	/**
	 * Add the given {@link BankDetails} item to the {@code bankDetailsSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addBankDetailsSetItem(BankDetails item) {
		if (getBankDetailsSet() == null) {
			setBankDetailsSet(new HashSet<>());
		}
		getBankDetailsSet().add(item);
	}

	/**
	 * Remove the given {@link BankDetails} item from the {@code bankDetailsSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeBankDetailsSetItem(BankDetails item) {
		if (getBankDetailsSet() == null) {
			return;
		}
		getBankDetailsSet().remove(item);
	}

	/**
	 * Clear the {@code bankDetailsSet} collection.
	 *
	 */
	public void clearBankDetailsSet() {
		if (getBankDetailsSet() != null) {
			getBankDetailsSet().clear();
		}
	}

	public Set<Journal> getJournalSet() {
		return journalSet;
	}

	public void setJournalSet(Set<Journal> journalSet) {
		this.journalSet = journalSet;
	}

	/**
	 * Add the given {@link Journal} item to the {@code journalSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addJournalSetItem(Journal item) {
		if (getJournalSet() == null) {
			setJournalSet(new HashSet<>());
		}
		getJournalSet().add(item);
	}

	/**
	 * Remove the given {@link Journal} item from the {@code journalSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeJournalSetItem(Journal item) {
		if (getJournalSet() == null) {
			return;
		}
		getJournalSet().remove(item);
	}

	/**
	 * Clear the {@code journalSet} collection.
	 *
	 */
	public void clearJournalSet() {
		if (getJournalSet() != null) {
			getJournalSet().clear();
		}
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
		if (!(obj instanceof AccountingReport)) return false;

		final AccountingReport other = (AccountingReport) obj;
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
			.add("ref", getRef())
			.add("dateFrom", getDateFrom())
			.add("dateTo", getDateTo())
			.add("exportTypeSelect", getExportTypeSelect())
			.add("typeSelect", getTypeSelect())
			.add("date", getDate())
			.add("publicationDateTime", getPublicationDateTime())
			.add("global", getGlobal())
			.add("globalByPartner", getGlobalByPartner())
			.add("globalByDate", getGlobalByDate())
			.add("detailed", getDetailed())
			.omitNullValues()
			.toString();
	}
}
