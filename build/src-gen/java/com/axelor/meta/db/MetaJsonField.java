package com.axelor.meta.db;

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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;

import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.Role;
import com.axelor.db.annotations.Track;
import com.axelor.db.annotations.TrackField;
import com.axelor.db.annotations.Widget;
import com.axelor.studio.db.AppBuilder;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "META_JSON_FIELD", indexes = { @Index(columnList = "name"), @Index(columnList = "json_model"), @Index(columnList = "target_json_model"), @Index(columnList = "app_builder"), @Index(columnList = "meta_select") })
@Track(fields = { @TrackField(name = "name"), @TrackField(name = "title"), @TrackField(name = "type"), @TrackField(name = "defaultValue"), @TrackField(name = "model"), @TrackField(name = "modelField"), @TrackField(name = "jsonModel"), @TrackField(name = "selection"), @TrackField(name = "widget"), @TrackField(name = "help"), @TrackField(name = "showIf"), @TrackField(name = "hideIf"), @TrackField(name = "requiredIf"), @TrackField(name = "readonlyIf"), @TrackField(name = "includeIf"), @TrackField(name = "contextField"), @TrackField(name = "contextFieldTarget"), @TrackField(name = "contextFieldTargetName"), @TrackField(name = "contextFieldValue"), @TrackField(name = "contextFieldTitle"), @TrackField(name = "hidden"), @TrackField(name = "required"), @TrackField(name = "readonly"), @TrackField(name = "nameField"), @TrackField(name = "visibleInGrid"), @TrackField(name = "minSize"), @TrackField(name = "maxSize"), @TrackField(name = "precision"), @TrackField(name = "scale"), @TrackField(name = "sequence"), @TrackField(name = "columnSequence"), @TrackField(name = "regex"), @TrackField(name = "valueExpr"), @TrackField(name = "targetModel"), @TrackField(name = "enumType"), @TrackField(name = "formView"), @TrackField(name = "gridView"), @TrackField(name = "domain"), @TrackField(name = "targetJsonModel"), @TrackField(name = "onChange"), @TrackField(name = "onClick"), @TrackField(name = "widgetAttrs") })
public class MetaJsonField extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "META_JSON_FIELD_SEQ")
	@SequenceGenerator(name = "META_JSON_FIELD_SEQ", sequenceName = "META_JSON_FIELD_SEQ", allocationSize = 1)
	private Long id;

	@NotNull
	private String name;

	private String title;

	@Widget(selection = "json.field.type")
	@NotNull
	@Column(name = "type_name")
	private String type;

	private String defaultValue;

	@NotNull
	@Column(name = "model_name")
	private String model;

	@NotNull
	@Column(name = "model_field")
	private String modelField;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaJsonModel jsonModel;

	private String selection;

	private String widget;

	private String help;

	@Size(max = 512)
	private String showIf;

	@Size(max = 512)
	private String hideIf;

	@Size(max = 512)
	private String requiredIf;

	@Size(max = 512)
	private String readonlyIf;

	@Size(max = 512)
	private String includeIf;

	private String contextField;

	private String contextFieldTarget;

	private String contextFieldTargetName;

	private String contextFieldValue;

	private String contextFieldTitle;

	@Column(name = "is_hidden")
	private Boolean hidden = Boolean.FALSE;

	@Column(name = "is_required")
	private Boolean required = Boolean.FALSE;

	@Column(name = "is_readonly")
	private Boolean readonly = Boolean.FALSE;

	private Boolean nameField = Boolean.FALSE;

	private Boolean visibleInGrid = Boolean.FALSE;

	@Column(name = "min_size")
	private Integer minSize = 0;

	@Column(name = "max_size")
	private Integer maxSize = 0;

	@Column(name = "decimal_precision")
	private Integer precision = 6;

	@Column(name = "decimal_scale")
	private Integer scale = 2;

	private Integer sequence = 0;

	private Integer columnSequence = 0;

	private String regex;

	@Widget(title = "Value Expression")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String valueExpr;

	private String targetModel;

	private String enumType;

	private String formView;

	private String gridView;

	private String domain;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaJsonModel targetJsonModel;

	private String onChange;

	private String onClick;

	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String widgetAttrs;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Role> roles;

	private Boolean isWkf = Boolean.FALSE;

	@Widget(title = "App builder")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AppBuilder appBuilder;

	@Widget(title = "Existing select")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private MetaSelect metaSelect;

	@Widget(title = "Selection")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String selectionText;

	private Boolean isSelectionField = Boolean.FALSE;

	public MetaJsonField() {
	}

	public MetaJsonField(String name, Boolean nameField) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModelField() {
		return modelField;
	}

	public void setModelField(String modelField) {
		this.modelField = modelField;
	}

	public MetaJsonModel getJsonModel() {
		return jsonModel;
	}

	public void setJsonModel(MetaJsonModel jsonModel) {
		this.jsonModel = jsonModel;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getWidget() {
		return widget;
	}

	public void setWidget(String widget) {
		this.widget = widget;
	}

	public String getHelp() {
		return help;
	}

	public void setHelp(String help) {
		this.help = help;
	}

	public String getShowIf() {
		return showIf;
	}

	public void setShowIf(String showIf) {
		this.showIf = showIf;
	}

	public String getHideIf() {
		return hideIf;
	}

	public void setHideIf(String hideIf) {
		this.hideIf = hideIf;
	}

	public String getRequiredIf() {
		return requiredIf;
	}

	public void setRequiredIf(String requiredIf) {
		this.requiredIf = requiredIf;
	}

	public String getReadonlyIf() {
		return readonlyIf;
	}

	public void setReadonlyIf(String readonlyIf) {
		this.readonlyIf = readonlyIf;
	}

	public String getIncludeIf() {
		return includeIf;
	}

	public void setIncludeIf(String includeIf) {
		this.includeIf = includeIf;
	}

	public String getContextField() {
		return contextField;
	}

	public void setContextField(String contextField) {
		this.contextField = contextField;
	}

	public String getContextFieldTarget() {
		return contextFieldTarget;
	}

	public void setContextFieldTarget(String contextFieldTarget) {
		this.contextFieldTarget = contextFieldTarget;
	}

	public String getContextFieldTargetName() {
		return contextFieldTargetName;
	}

	public void setContextFieldTargetName(String contextFieldTargetName) {
		this.contextFieldTargetName = contextFieldTargetName;
	}

	public String getContextFieldValue() {
		return contextFieldValue;
	}

	public void setContextFieldValue(String contextFieldValue) {
		this.contextFieldValue = contextFieldValue;
	}

	public String getContextFieldTitle() {
		return contextFieldTitle;
	}

	public void setContextFieldTitle(String contextFieldTitle) {
		this.contextFieldTitle = contextFieldTitle;
	}

	public Boolean getHidden() {
		return hidden == null ? Boolean.FALSE : hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public Boolean getRequired() {
		return required == null ? Boolean.FALSE : required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public Boolean getReadonly() {
		return readonly == null ? Boolean.FALSE : readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public Boolean getNameField() {
		return nameField == null ? Boolean.FALSE : nameField;
	}

	public void setNameField(Boolean nameField) {
		this.nameField = nameField;
	}

	public Boolean getVisibleInGrid() {
		return visibleInGrid == null ? Boolean.FALSE : visibleInGrid;
	}

	public void setVisibleInGrid(Boolean visibleInGrid) {
		this.visibleInGrid = visibleInGrid;
	}

	public Integer getMinSize() {
		return minSize == null ? 0 : minSize;
	}

	public void setMinSize(Integer minSize) {
		this.minSize = minSize;
	}

	public Integer getMaxSize() {
		return maxSize == null ? 0 : maxSize;
	}

	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	public Integer getPrecision() {
		return precision == null ? 0 : precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getScale() {
		return scale == null ? 0 : scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public Integer getSequence() {
		return sequence == null ? 0 : sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getColumnSequence() {
		return columnSequence == null ? 0 : columnSequence;
	}

	public void setColumnSequence(Integer columnSequence) {
		this.columnSequence = columnSequence;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getValueExpr() {
		return valueExpr;
	}

	public void setValueExpr(String valueExpr) {
		this.valueExpr = valueExpr;
	}

	public String getTargetModel() {
		return targetModel;
	}

	public void setTargetModel(String targetModel) {
		this.targetModel = targetModel;
	}

	public String getEnumType() {
		return enumType;
	}

	public void setEnumType(String enumType) {
		this.enumType = enumType;
	}

	public String getFormView() {
		return formView;
	}

	public void setFormView(String formView) {
		this.formView = formView;
	}

	public String getGridView() {
		return gridView;
	}

	public void setGridView(String gridView) {
		this.gridView = gridView;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public MetaJsonModel getTargetJsonModel() {
		return targetJsonModel;
	}

	public void setTargetJsonModel(MetaJsonModel targetJsonModel) {
		this.targetJsonModel = targetJsonModel;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getWidgetAttrs() {
		return widgetAttrs;
	}

	public void setWidgetAttrs(String widgetAttrs) {
		this.widgetAttrs = widgetAttrs;
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

	public Boolean getIsWkf() {
		return isWkf == null ? Boolean.FALSE : isWkf;
	}

	public void setIsWkf(Boolean isWkf) {
		this.isWkf = isWkf;
	}

	public AppBuilder getAppBuilder() {
		return appBuilder;
	}

	public void setAppBuilder(AppBuilder appBuilder) {
		this.appBuilder = appBuilder;
	}

	public MetaSelect getMetaSelect() {
		return metaSelect;
	}

	public void setMetaSelect(MetaSelect metaSelect) {
		this.metaSelect = metaSelect;
	}

	public String getSelectionText() {
		return selectionText;
	}

	public void setSelectionText(String selectionText) {
		this.selectionText = selectionText;
	}

	public Boolean getIsSelectionField() {
		return isSelectionField == null ? Boolean.FALSE : isSelectionField;
	}

	public void setIsSelectionField(Boolean isSelectionField) {
		this.isSelectionField = isSelectionField;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (this == obj) return true;
		if (!(obj instanceof MetaJsonField)) return false;

		final MetaJsonField other = (MetaJsonField) obj;
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
			.add("name", getName())
			.add("title", getTitle())
			.add("type", getType())
			.add("defaultValue", getDefaultValue())
			.add("model", getModel())
			.add("modelField", getModelField())
			.add("selection", getSelection())
			.add("widget", getWidget())
			.add("help", getHelp())
			.add("showIf", getShowIf())
			.add("hideIf", getHideIf())
			.omitNullValues()
			.toString();
	}
}
