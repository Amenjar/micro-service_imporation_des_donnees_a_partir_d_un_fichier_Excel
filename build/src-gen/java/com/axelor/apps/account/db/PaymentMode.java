package com.axelor.apps.account.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.apps.bankpayment.db.BankOrderFileFormat;
import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.Widget;
import com.google.common.base.MoreObjects;

@Entity
@Cacheable
@Table(name = "ACCOUNT_PAYMENT_MODE", indexes = { @Index(columnList = "code"), @Index(columnList = "bank_order_file_format") })
public class PaymentMode extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_PAYMENT_MODE_SEQ")
	@SequenceGenerator(name = "ACCOUNT_PAYMENT_MODE_SEQ", sequenceName = "ACCOUNT_PAYMENT_MODE_SEQ", allocationSize = 1)
	private Long id;

	@HashKey
	@Widget(title = "Label", translatable = true)
	@NotNull
	@Column(unique = true)
	private String name;

	@Widget(title = "Code")
	@NotNull
	private String code;

	@Widget(title = "Type", selection = "iaccount.payment.mode.type.select")
	private Integer typeSelect = 1;

	@Widget(title = "In / Out", selection = "iaccount.payment.mode.in.out.select")
	private Integer inOutSelect = 1;

	@Widget(title = "Accounting settings")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentMode", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AccountManagement> accountManagementList;

	private Boolean validatePaymentByDepositSlipPublication = Boolean.FALSE;

	@Widget(title = "Generate Bank order")
	private Boolean generateBankOrder = Boolean.FALSE;

	@Widget(title = "Confirm auto. the bank orders")
	private Boolean autoConfirmBankOrder = Boolean.FALSE;

	@Widget(title = "Transmit automatically to Bank")
	private Boolean automaticTransmission = Boolean.FALSE;

	@Widget(title = "Order Type", selection = "bankpayment.bank.order.type.select")
	private Integer orderTypeSelect = 0;

	private String bankOrderExportFolderPath;

	@Widget(title = "File format")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private BankOrderFileFormat bankOrderFileFormat;

	@Widget(title = "Generate account move from the bank order")
	private Boolean generateMoveAutoFromBankOrder = Boolean.FALSE;

	@Widget(title = "Consolidate bank order lines per partner")
	private Boolean consoBankOrderLinePerPartner = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public PaymentMode() {
	}

	public PaymentMode(String name, String code) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getTypeSelect() {
		return typeSelect == null ? 0 : typeSelect;
	}

	public void setTypeSelect(Integer typeSelect) {
		this.typeSelect = typeSelect;
	}

	public Integer getInOutSelect() {
		return inOutSelect == null ? 0 : inOutSelect;
	}

	public void setInOutSelect(Integer inOutSelect) {
		this.inOutSelect = inOutSelect;
	}

	public List<AccountManagement> getAccountManagementList() {
		return accountManagementList;
	}

	public void setAccountManagementList(List<AccountManagement> accountManagementList) {
		this.accountManagementList = accountManagementList;
	}

	/**
	 * Add the given {@link AccountManagement} item to the {@code accountManagementList}.
	 *
	 * <p>
	 * It sets {@code item.paymentMode = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addAccountManagementListItem(AccountManagement item) {
		if (getAccountManagementList() == null) {
			setAccountManagementList(new ArrayList<>());
		}
		getAccountManagementList().add(item);
		item.setPaymentMode(this);
	}

	/**
	 * Remove the given {@link AccountManagement} item from the {@code accountManagementList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeAccountManagementListItem(AccountManagement item) {
		if (getAccountManagementList() == null) {
			return;
		}
		getAccountManagementList().remove(item);
	}

	/**
	 * Clear the {@code accountManagementList} collection.
	 *
	 * <p>
	 * If you have to query {@link AccountManagement} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearAccountManagementList() {
		if (getAccountManagementList() != null) {
			getAccountManagementList().clear();
		}
	}

	public Boolean getValidatePaymentByDepositSlipPublication() {
		return validatePaymentByDepositSlipPublication == null ? Boolean.FALSE : validatePaymentByDepositSlipPublication;
	}

	public void setValidatePaymentByDepositSlipPublication(Boolean validatePaymentByDepositSlipPublication) {
		this.validatePaymentByDepositSlipPublication = validatePaymentByDepositSlipPublication;
	}

	public Boolean getGenerateBankOrder() {
		return generateBankOrder == null ? Boolean.FALSE : generateBankOrder;
	}

	public void setGenerateBankOrder(Boolean generateBankOrder) {
		this.generateBankOrder = generateBankOrder;
	}

	public Boolean getAutoConfirmBankOrder() {
		return autoConfirmBankOrder == null ? Boolean.FALSE : autoConfirmBankOrder;
	}

	public void setAutoConfirmBankOrder(Boolean autoConfirmBankOrder) {
		this.autoConfirmBankOrder = autoConfirmBankOrder;
	}

	public Boolean getAutomaticTransmission() {
		return automaticTransmission == null ? Boolean.FALSE : automaticTransmission;
	}

	public void setAutomaticTransmission(Boolean automaticTransmission) {
		this.automaticTransmission = automaticTransmission;
	}

	public Integer getOrderTypeSelect() {
		return orderTypeSelect == null ? 0 : orderTypeSelect;
	}

	public void setOrderTypeSelect(Integer orderTypeSelect) {
		this.orderTypeSelect = orderTypeSelect;
	}

	public String getBankOrderExportFolderPath() {
		return bankOrderExportFolderPath;
	}

	public void setBankOrderExportFolderPath(String bankOrderExportFolderPath) {
		this.bankOrderExportFolderPath = bankOrderExportFolderPath;
	}

	public BankOrderFileFormat getBankOrderFileFormat() {
		return bankOrderFileFormat;
	}

	public void setBankOrderFileFormat(BankOrderFileFormat bankOrderFileFormat) {
		this.bankOrderFileFormat = bankOrderFileFormat;
	}

	public Boolean getGenerateMoveAutoFromBankOrder() {
		return generateMoveAutoFromBankOrder == null ? Boolean.FALSE : generateMoveAutoFromBankOrder;
	}

	public void setGenerateMoveAutoFromBankOrder(Boolean generateMoveAutoFromBankOrder) {
		this.generateMoveAutoFromBankOrder = generateMoveAutoFromBankOrder;
	}

	public Boolean getConsoBankOrderLinePerPartner() {
		return consoBankOrderLinePerPartner == null ? Boolean.FALSE : consoBankOrderLinePerPartner;
	}

	public void setConsoBankOrderLinePerPartner(Boolean consoBankOrderLinePerPartner) {
		this.consoBankOrderLinePerPartner = consoBankOrderLinePerPartner;
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
		if (!(obj instanceof PaymentMode)) return false;

		final PaymentMode other = (PaymentMode) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getName(), other.getName())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(127276201, this.getName());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("name", getName())
			.add("code", getCode())
			.add("typeSelect", getTypeSelect())
			.add("inOutSelect", getInOutSelect())
			.add("validatePaymentByDepositSlipPublication", getValidatePaymentByDepositSlipPublication())
			.add("generateBankOrder", getGenerateBankOrder())
			.add("autoConfirmBankOrder", getAutoConfirmBankOrder())
			.add("automaticTransmission", getAutomaticTransmission())
			.add("orderTypeSelect", getOrderTypeSelect())
			.add("bankOrderExportFolderPath", getBankOrderExportFolderPath())
			.add("generateMoveAutoFromBankOrder", getGenerateMoveAutoFromBankOrder())
			.omitNullValues()
			.toString();
	}
}
