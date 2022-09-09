package com.axelor.meta.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.Role;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.studio.db.AppBuilder;
import com.axelor.studio.db.MenuBuilder;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "META_JSON_MODEL", indexes = { @Index(columnList = "title"), @Index(columnList = "menu"), @Index(columnList = "menu_parent"), @Index(columnList = "action"), @Index(columnList = "grid_view"), @Index(columnList = "form_view"), @Index(columnList = "app_builder"), @Index(columnList = "menu_builder") })
public class MetaJsonModel extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "META_JSON_MODEL_SEQ")
	@SequenceGenerator(name = "META_JSON_MODEL_SEQ", sequenceName = "META_JSON_MODEL_SEQ", allocationSize = 1)
	private Long id;

	@HashKey
	@NotNull
	@Column(unique = true)
	private String name;

	@NameColumn
	@NotNull
	private String title;

	private String onNew;

	private String onSave;

	private String nameField;

	private String formWidth;

	private String orderBy;

	private String groupBy;

	@Widget(copyable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaMenu menu;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaMenu menuParent;

	private String menuIcon;

	private String menuBackground;

	private Integer menuOrder = 0;

	private String menuTitle;

	private Boolean menuTop = Boolean.FALSE;

	@Widget(copyable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaAction action;

	@Widget(copyable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaView gridView;

	@Widget(copyable = false)
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaView formView;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "jsonModel", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("sequence, id")
	private List<MetaJsonField> fields;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles;

	@Widget(title = "App builder")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AppBuilder appBuilder;

	@Widget(title = "Show process tracking")
	private Boolean showProcessTracking = Boolean.FALSE;

	@Widget(title = "Generate menu")
	private Boolean isGenerateMenu = Boolean.FALSE;

	@Widget(title = "Menu builder")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MenuBuilder menuBuilder;

	public MetaJsonModel() {
	}

	public MetaJsonModel(String name, String nameField) {
		this.name = name;
		this.nameField = nameField;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOnNew() {
		return onNew;
	}

	public void setOnNew(String onNew) {
		this.onNew = onNew;
	}

	public String getOnSave() {
		return onSave;
	}

	public void setOnSave(String onSave) {
		this.onSave = onSave;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getFormWidth() {
		return formWidth;
	}

	public void setFormWidth(String formWidth) {
		this.formWidth = formWidth;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public MetaMenu getMenu() {
		return menu;
	}

	public void setMenu(MetaMenu menu) {
		this.menu = menu;
	}

	public MetaMenu getMenuParent() {
		return menuParent;
	}

	public void setMenuParent(MetaMenu menuParent) {
		this.menuParent = menuParent;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuBackground() {
		return menuBackground;
	}

	public void setMenuBackground(String menuBackground) {
		this.menuBackground = menuBackground;
	}

	public Integer getMenuOrder() {
		return menuOrder == null ? 0 : menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	public Boolean getMenuTop() {
		return menuTop == null ? Boolean.FALSE : menuTop;
	}

	public void setMenuTop(Boolean menuTop) {
		this.menuTop = menuTop;
	}

	public MetaAction getAction() {
		return action;
	}

	public void setAction(MetaAction action) {
		this.action = action;
	}

	public MetaView getGridView() {
		return gridView;
	}

	public void setGridView(MetaView gridView) {
		this.gridView = gridView;
	}

	public MetaView getFormView() {
		return formView;
	}

	public void setFormView(MetaView formView) {
		this.formView = formView;
	}

	public List<MetaJsonField> getFields() {
		return fields;
	}

	public void setFields(List<MetaJsonField> fields) {
		this.fields = fields;
	}

	/**
	 * Add the given {@link MetaJsonField} item to the {@code fields}.
	 *
	 * <p>
	 * It sets {@code item.jsonModel = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addField(MetaJsonField item) {
		if (getFields() == null) {
			setFields(new ArrayList<>());
		}
		getFields().add(item);
		item.setJsonModel(this);
	}

	/**
	 * Remove the given {@link MetaJsonField} item from the {@code fields}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeField(MetaJsonField item) {
		if (getFields() == null) {
			return;
		}
		getFields().remove(item);
	}

	/**
	 * Clear the {@code fields} collection.
	 *
	 * <p>
	 * If you have to query {@link MetaJsonField} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearFields() {
		if (getFields() != null) {
			getFields().clear();
		}
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * Add the given {@link Role} item to the {@code roles}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addRole(Role item) {
		if (getRoles() == null) {
			setRoles(new HashSet<>());
		}
		getRoles().add(item);
	}

	/**
	 * Remove the given {@link Role} item from the {@code roles}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeRole(Role item) {
		if (getRoles() == null) {
			return;
		}
		getRoles().remove(item);
	}

	/**
	 * Clear the {@code roles} collection.
	 *
	 */
	public void clearRoles() {
		if (getRoles() != null) {
			getRoles().clear();
		}
	}

	public AppBuilder getAppBuilder() {
		return appBuilder;
	}

	public void setAppBuilder(AppBuilder appBuilder) {
		this.appBuilder = appBuilder;
	}

	public Boolean getShowProcessTracking() {
		return showProcessTracking == null ? Boolean.FALSE : showProcessTracking;
	}

	public void setShowProcessTracking(Boolean showProcessTracking) {
		this.showProcessTracking = showProcessTracking;
	}

	public Boolean getIsGenerateMenu() {
		return isGenerateMenu == null ? Boolean.FALSE : isGenerateMenu;
	}

	public void setIsGenerateMenu(Boolean isGenerateMenu) {
		this.isGenerateMenu = isGenerateMenu;
	}

	public MenuBuilder getMenuBuilder() {
		return menuBuilder;
	}

	public void setMenuBuilder(MenuBuilder menuBuilder) {
		this.menuBuilder = menuBuilder;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof MetaJsonModel)) return false;

		final MetaJsonModel other = (MetaJsonModel) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getName(), other.getName())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(64822908, this.getName());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("name", getName())
			.add("title", getTitle())
			.add("onNew", getOnNew())
			.add("onSave", getOnSave())
			.add("nameField", getNameField())
			.add("formWidth", getFormWidth())
			.add("orderBy", getOrderBy())
			.add("groupBy", getGroupBy())
			.add("menuIcon", getMenuIcon())
			.add("menuBackground", getMenuBackground())
			.add("menuOrder", getMenuOrder())
			.omitNullValues()
			.toString();
	}
}
