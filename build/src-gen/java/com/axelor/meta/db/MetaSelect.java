package com.axelor.meta.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.Widget;
import com.axelor.studio.db.AppBuilder;
import com.google.common.base.MoreObjects;

/**
 * This object stores the selects.
 */
@Entity
@Cacheable
@Table(name = "META_SELECT", indexes = { @Index(columnList = "name"), @Index(columnList = "app_builder") })
public class MetaSelect extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "META_SELECT_SEQ")
	@SequenceGenerator(name = "META_SELECT_SEQ", sequenceName = "META_SELECT_SEQ", allocationSize = 1)
	private Long id;

	@HashKey
	@Column(unique = true)
	private String xmlId;

	@NotNull
	private String name;

	@NotNull
	private Integer priority = 20;

	private String module;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "select", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MetaSelectItem> items;

	@Widget(title = "App builder")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AppBuilder appBuilder;

	@Widget(title = "Custom")
	private Boolean isCustom = Boolean.FALSE;

	public MetaSelect() {
	}

	public MetaSelect(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getXmlId() {
		return xmlId;
	}

	public void setXmlId(String xmlId) {
		this.xmlId = xmlId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPriority() {
		return priority == null ? 0 : priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<MetaSelectItem> getItems() {
		return items;
	}

	public void setItems(List<MetaSelectItem> items) {
		this.items = items;
	}

	/**
	 * Add the given {@link MetaSelectItem} item to the {@code items}.
	 *
	 * <p>
	 * It sets {@code item.select = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addItem(MetaSelectItem item) {
		if (getItems() == null) {
			setItems(new ArrayList<>());
		}
		getItems().add(item);
		item.setSelect(this);
	}

	/**
	 * Remove the given {@link MetaSelectItem} item from the {@code items}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeItem(MetaSelectItem item) {
		if (getItems() == null) {
			return;
		}
		getItems().remove(item);
	}

	/**
	 * Clear the {@code items} collection.
	 *
	 * <p>
	 * If you have to query {@link MetaSelectItem} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearItems() {
		if (getItems() != null) {
			getItems().clear();
		}
	}

	public AppBuilder getAppBuilder() {
		return appBuilder;
	}

	public void setAppBuilder(AppBuilder appBuilder) {
		this.appBuilder = appBuilder;
	}

	public Boolean getIsCustom() {
		return isCustom == null ? Boolean.FALSE : isCustom;
	}

	public void setIsCustom(Boolean isCustom) {
		this.isCustom = isCustom;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof MetaSelect)) return false;

		final MetaSelect other = (MetaSelect) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getXmlId(), other.getXmlId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(-1447052415, this.getXmlId());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("xmlId", getXmlId())
			.add("name", getName())
			.add("priority", getPriority())
			.add("module", getModule())
			.add("isCustom", getIsCustom())
			.omitNullValues()
			.toString();
	}
}
