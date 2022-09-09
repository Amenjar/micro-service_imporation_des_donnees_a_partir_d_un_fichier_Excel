package com.axelor.meta.db;

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.Widget;
import com.axelor.studio.db.AppBuilder;
import com.google.common.base.MoreObjects;

/**
 * This object stores the xml actions.
 */
@Entity
@Cacheable
@Table(name = "META_ACTION", indexes = { @Index(columnList = "name"), @Index(columnList = "app_builder") })
public class MetaAction extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "META_ACTION_SEQ")
	@SequenceGenerator(name = "META_ACTION_SEQ", sequenceName = "META_ACTION_SEQ", allocationSize = 1)
	private Long id;

	private Integer priority = 0;

	@HashKey
	@Column(unique = true)
	private String xmlId;

	@NotNull
	private String name;

	@Widget(selection = "action.type.selection")
	@NotNull
	private String type;

	@Widget(title = "Used as home action", help = "Specify whether this action can be used as home action.")
	private Boolean home = Boolean.FALSE;

	private String model;

	private String module;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	@NotNull
	private String xml;

	@Widget(title = "Sequence")
	private Integer sequence = 0;

	@Widget(title = "App builder")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AppBuilder appBuilder;

	@Widget(title = "Custom")
	private Boolean isCustom = Boolean.FALSE;

	public MetaAction() {
	}

	public MetaAction(String name) {
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

	public Integer getPriority() {
		return priority == null ? 0 : priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Specify whether this action can be used as home action.
	 *
	 * @return the property value
	 */
	public Boolean getHome() {
		return home == null ? Boolean.FALSE : home;
	}

	public void setHome(Boolean home) {
		this.home = home;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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
		if (!(obj instanceof MetaAction)) return false;

		final MetaAction other = (MetaAction) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getXmlId(), other.getXmlId())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(-1963981637, this.getXmlId());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("priority", getPriority())
			.add("xmlId", getXmlId())
			.add("name", getName())
			.add("type", getType())
			.add("home", getHome())
			.add("model", getModel())
			.add("module", getModule())
			.add("sequence", getSequence())
			.add("isCustom", getIsCustom())
			.omitNullValues()
			.toString();
	}
}
