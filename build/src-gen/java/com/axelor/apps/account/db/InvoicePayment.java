package com.axelor.apps.account.db;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.bankpayment.db.BankOrder;
import com.axelor.apps.base.db.BankDetails;
import com.axelor.apps.base.db.Currency;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "ACCOUNT_INVOICE_PAYMENT", indexes = { @Index(columnList = "currency"), @Index(columnList = "payment_mode"), @Index(name = "idx_account_inv_payment_move", columnList = "move"), @Index(columnList = "reconcile"), @Index(columnList = "invoice"), @Index(columnList = "company_bank_details"), @Index(columnList = "imputed_by"), @Index(columnList = "bank_order") })
public class InvoicePayment extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_INVOICE_PAYMENT_SEQ")
	@SequenceGenerator(name = "ACCOUNT_INVOICE_PAYMENT_SEQ", sequenceName = "ACCOUNT_INVOICE_PAYMENT_SEQ", allocationSize = 1)
	private Long id;

	@Widget(title = "Amount")
	@NotNull
	@DecimalMin("0")
	private BigDecimal amount = new BigDecimal("0");

	@Widget(title = "Date")
	@NotNull
	private LocalDate paymentDate;

	@Widget(title = "Delay reason")
	private String delayReason;

	@Widget(title = "Currency")
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Currency currency;

	@Widget(title = "Payment mode")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private PaymentMode paymentMode;

	@Widget(title = "Date of bank deposit")
	private LocalDate bankDepositDate;

	@Widget(title = "Cheque number")
	private String chequeNumber;

	@Widget(title = "Move", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Move move;

	@Widget(title = "Reconcile", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Reconcile reconcile;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Invoice invoice;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankDetails companyBankDetails;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private InvoicePayment imputedBy;

	@Widget(title = "Type", selection = "invoice.payment.type.select")
	private Integer typeSelect = 0;

	@Widget(title = "Status", readonly = true, selection = "invoice.payment.status.select")
	private Integer statusSelect = 0;

	@Widget(title = "Reference")
	private String invoicePaymentRef;

	@Widget(title = "Description")
	private String description;

	@Widget(title = "Bank Order", readonly = true)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankOrder bankOrder;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public InvoicePayment() {
	}

	public InvoicePayment(BigDecimal amount, LocalDate paymentDate, Currency currency, PaymentMode paymentMode, Invoice invoice, Integer typeSelect, Integer statusSelect) {
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.currency = currency;
		this.paymentMode = paymentMode;
		this.invoice = invoice;
		this.typeSelect = typeSelect;
		this.statusSelect = statusSelect;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount == null ? BigDecimal.ZERO : amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
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

	public LocalDate getBankDepositDate() {
		return bankDepositDate;
	}

	public void setBankDepositDate(LocalDate bankDepositDate) {
		this.bankDepositDate = bankDepositDate;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public Move getMove() {
		return move;
	}

	public void setMove(Move move) {
		this.move = move;
	}

	public Reconcile getReconcile() {
		return reconcile;
	}

	public void setReconcile(Reconcile reconcile) {
		this.reconcile = reconcile;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public BankDetails getCompanyBankDetails() {
		return companyBankDetails;
	}

	public void setCompanyBankDetails(BankDetails companyBankDetails) {
		this.companyBankDetails = companyBankDetails;
	}

	public InvoicePayment getImputedBy() {
		return imputedBy;
	}

	public void setImputedBy(InvoicePayment imputedBy) {
		this.imputedBy = imputedBy;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public String getInvoicePaymentRef() {
		return invoicePaymentRef;
	}

	public void setInvoicePaymentRef(String invoicePaymentRef) {
		this.invoicePaymentRef = invoicePaymentRef;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BankOrder getBankOrder() {
		return bankOrder;
	}

	public void setBankOrder(BankOrder bankOrder) {
		this.bankOrder = bankOrder;
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
		if (!(obj instanceof InvoicePayment)) return false;

		final InvoicePayment other = (InvoicePayment) obj;
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
			.add("amount", getAmount())
			.add("paymentDate", getPaymentDate())
			.add("delayReason", getDelayReason())
			.add("bankDepositDate", getBankDepositDate())
			.add("chequeNumber", getChequeNumber())
			.add("typeSelect", getTypeSelect())
			.add("statusSelect", getStatusSelect())
			.add("invoicePaymentRef", getInvoicePaymentRef())
			.add("description", getDescription())
			.omitNullValues()
			.toString();
	}
}
