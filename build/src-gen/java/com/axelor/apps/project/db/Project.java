package com.axelor.apps.project.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import org.hibernate.annotations.Type;

import com.axelor.apps.base.db.Company;
import com.axelor.apps.base.db.Partner;
import com.axelor.apps.base.db.Product;
import com.axelor.apps.hr.db.TimesheetLine;
import com.axelor.auth.db.AuditableModel;
import com.axelor.auth.db.User;
import com.axelor.db.annotations.HashKey;
import com.axelor.db.annotations.NameColumn;
import com.axelor.db.annotations.Widget;
import com.axelor.team.db.Team;
import com.axelor.team.db.TeamTask;
import com.google.common.base.MoreObjects;

@Entity
@Table(name = "PROJECT_PROJECT", indexes = { @Index(columnList = "name"), @Index(columnList = "team"), @Index(columnList = "assigned_to"), @Index(columnList = "parent_project"), @Index(columnList = "client_partner"), @Index(columnList = "contact_partner"), @Index(columnList = "company"), @Index(columnList = "fullName") })
public class Project extends AuditableModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_PROJECT_SEQ")
	@SequenceGenerator(name = "PROJECT_PROJECT_SEQ", sequenceName = "PROJECT_PROJECT_SEQ", allocationSize = 1)
	private Long id;

	private String name;

	@Widget(multiline = true)
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "text")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Team team;

	@Widget(title = "Project Folders")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<ProjectFolder> projectFolderSet;

	@Widget(title = "Child Projects")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentProject", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Project> childProjectList;

	@Widget(title = "Assigned to")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private User assignedTo;

	@Widget(title = "Status", selection = "project.status.select")
	private Integer statusSelect = 1;

	@Widget(title = "Type of authorized categories")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<TeamTaskCategory> teamTaskCategorySet;

	@Widget(title = "Tasks")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TeamTask> teamTaskList;

	private Boolean synchronize = Boolean.FALSE;

	private Boolean extendsMembersFromParent = Boolean.FALSE;

	@Widget(title = "Parent project")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Project parentProject;

	@Widget(title = "Wiki")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Wiki> wikiList;

	@Widget(title = "Resource booking")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ResourceBooking> resourceBookingList;

	@HashKey
	@Widget(title = "Code")
	@Column(unique = true)
	private String code;

	@Widget(title = "Membres")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<User> membersUserSet;

	@Widget(title = "Customer")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner clientPartner;

	@Widget(title = "Customer Contact")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Partner contactPartner;

	@Widget(title = "From Date")
	private LocalDateTime fromDate;

	@Widget(title = "To Date")
	private LocalDateTime toDate;

	@Widget(title = "Due Date")
	private LocalDateTime dueDate;

	@Widget(title = "Estimated time (in days)")
	private BigDecimal estimatedTimeDays = BigDecimal.ZERO;

	@Widget(title = "Estimated time (in hours)")
	private BigDecimal estimatedTimeHrs = BigDecimal.ZERO;

	@Widget(title = "Time Spent")
	private BigDecimal timeSpent = BigDecimal.ZERO;

	@Widget(title = "Progress (%)")
	private BigDecimal progress = BigDecimal.ZERO;

	@Widget(title = "Sequence")
	private BigDecimal sequence = BigDecimal.ZERO;

	private Integer orderByState = 0;

	@Widget(massUpdate = true)
	private Boolean imputable = Boolean.TRUE;

	@Widget(title = "Finish tasks to start")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Project> finishToStartTaskSet;

	@Widget(title = "Start tasks to start")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Project> startToStartTaskSet;

	@Widget(title = "Finish tasks to finish")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Project> finishToFinishTaskSet;

	@Widget(title = "Start tasks to finish")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Project> startToFinishTaskSet;

	@Widget(title = "Company")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Company company;

	@Widget(title = "Type of authorized activities")
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Product> productSet;

	@Widget(title = "Exclude planning")
	private Boolean excludePlanning = Boolean.FALSE;

	@Widget(title = "Name")
	@NameColumn
	private String fullName;

	@Widget(title = "Project/Phase")
	private Boolean isProject = Boolean.TRUE;

	@Widget(title = "Show phases elements")
	private Boolean isShowPhasesElements = Boolean.TRUE;

	@Widget(selection = "project.project.project.type.select")
	private Integer projectTypeSelect = 1;

	@Basic
	@Type(type = "com.axelor.db.hibernate.type.ValueEnumType")
	private GenProjTypePerOrderLine genProjTypePerOrderLine = GenProjTypePerOrderLine.BUSINESS_PROJECT;

	@Widget(title = "Logged Time")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<TimesheetLine> timesheetLineList;

	@Widget(title = "Total planned hours")
	private BigDecimal totalPlannedHrs = BigDecimal.ZERO;

	@Widget(title = "Total real hours")
	private BigDecimal totalRealHrs = BigDecimal.ZERO;

	@Widget(title = "Exclude timesheet editor")
	private Boolean excludeTimesheetEditor = Boolean.FALSE;

	@Widget(title = "Attributes")
	@Basic(fetch = FetchType.LAZY)
	@Type(type = "json")
	private String attrs;

	public Project() {
	}

	public Project(String name, String code) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Set<ProjectFolder> getProjectFolderSet() {
		return projectFolderSet;
	}

	public void setProjectFolderSet(Set<ProjectFolder> projectFolderSet) {
		this.projectFolderSet = projectFolderSet;
	}

	/**
	 * Add the given {@link ProjectFolder} item to the {@code projectFolderSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addProjectFolderSetItem(ProjectFolder item) {
		if (getProjectFolderSet() == null) {
			setProjectFolderSet(new HashSet<>());
		}
		getProjectFolderSet().add(item);
	}

	/**
	 * Remove the given {@link ProjectFolder} item from the {@code projectFolderSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeProjectFolderSetItem(ProjectFolder item) {
		if (getProjectFolderSet() == null) {
			return;
		}
		getProjectFolderSet().remove(item);
	}

	/**
	 * Clear the {@code projectFolderSet} collection.
	 *
	 */
	public void clearProjectFolderSet() {
		if (getProjectFolderSet() != null) {
			getProjectFolderSet().clear();
		}
	}

	public List<Project> getChildProjectList() {
		return childProjectList;
	}

	public void setChildProjectList(List<Project> childProjectList) {
		this.childProjectList = childProjectList;
	}

	/**
	 * Add the given {@link Project} item to the {@code childProjectList}.
	 *
	 * <p>
	 * It sets {@code item.parentProject = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addChildProjectListItem(Project item) {
		if (getChildProjectList() == null) {
			setChildProjectList(new ArrayList<>());
		}
		getChildProjectList().add(item);
		item.setParentProject(this);
	}

	/**
	 * Remove the given {@link Project} item from the {@code childProjectList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeChildProjectListItem(Project item) {
		if (getChildProjectList() == null) {
			return;
		}
		getChildProjectList().remove(item);
	}

	/**
	 * Clear the {@code childProjectList} collection.
	 *
	 * <p>
	 * If you have to query {@link Project} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearChildProjectList() {
		if (getChildProjectList() != null) {
			getChildProjectList().clear();
		}
	}

	public User getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Integer getStatusSelect() {
		return statusSelect == null ? 0 : statusSelect;
	}

	public void setStatusSelect(Integer statusSelect) {
		this.statusSelect = statusSelect;
	}

	public Set<TeamTaskCategory> getTeamTaskCategorySet() {
		return teamTaskCategorySet;
	}

	public void setTeamTaskCategorySet(Set<TeamTaskCategory> teamTaskCategorySet) {
		this.teamTaskCategorySet = teamTaskCategorySet;
	}

	/**
	 * Add the given {@link TeamTaskCategory} item to the {@code teamTaskCategorySet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTeamTaskCategorySetItem(TeamTaskCategory item) {
		if (getTeamTaskCategorySet() == null) {
			setTeamTaskCategorySet(new HashSet<>());
		}
		getTeamTaskCategorySet().add(item);
	}

	/**
	 * Remove the given {@link TeamTaskCategory} item from the {@code teamTaskCategorySet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTeamTaskCategorySetItem(TeamTaskCategory item) {
		if (getTeamTaskCategorySet() == null) {
			return;
		}
		getTeamTaskCategorySet().remove(item);
	}

	/**
	 * Clear the {@code teamTaskCategorySet} collection.
	 *
	 */
	public void clearTeamTaskCategorySet() {
		if (getTeamTaskCategorySet() != null) {
			getTeamTaskCategorySet().clear();
		}
	}

	public List<TeamTask> getTeamTaskList() {
		return teamTaskList;
	}

	public void setTeamTaskList(List<TeamTask> teamTaskList) {
		this.teamTaskList = teamTaskList;
	}

	/**
	 * Add the given {@link TeamTask} item to the {@code teamTaskList}.
	 *
	 * <p>
	 * It sets {@code item.project = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTeamTaskListItem(TeamTask item) {
		if (getTeamTaskList() == null) {
			setTeamTaskList(new ArrayList<>());
		}
		getTeamTaskList().add(item);
		item.setProject(this);
	}

	/**
	 * Remove the given {@link TeamTask} item from the {@code teamTaskList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTeamTaskListItem(TeamTask item) {
		if (getTeamTaskList() == null) {
			return;
		}
		getTeamTaskList().remove(item);
	}

	/**
	 * Clear the {@code teamTaskList} collection.
	 *
	 * <p>
	 * If you have to query {@link TeamTask} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearTeamTaskList() {
		if (getTeamTaskList() != null) {
			getTeamTaskList().clear();
		}
	}

	public Boolean getSynchronize() {
		return synchronize == null ? Boolean.FALSE : synchronize;
	}

	public void setSynchronize(Boolean synchronize) {
		this.synchronize = synchronize;
	}

	public Boolean getExtendsMembersFromParent() {
		return extendsMembersFromParent == null ? Boolean.FALSE : extendsMembersFromParent;
	}

	public void setExtendsMembersFromParent(Boolean extendsMembersFromParent) {
		this.extendsMembersFromParent = extendsMembersFromParent;
	}

	public Project getParentProject() {
		return parentProject;
	}

	public void setParentProject(Project parentProject) {
		this.parentProject = parentProject;
	}

	public List<Wiki> getWikiList() {
		return wikiList;
	}

	public void setWikiList(List<Wiki> wikiList) {
		this.wikiList = wikiList;
	}

	/**
	 * Add the given {@link Wiki} item to the {@code wikiList}.
	 *
	 * <p>
	 * It sets {@code item.project = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addWikiListItem(Wiki item) {
		if (getWikiList() == null) {
			setWikiList(new ArrayList<>());
		}
		getWikiList().add(item);
		item.setProject(this);
	}

	/**
	 * Remove the given {@link Wiki} item from the {@code wikiList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeWikiListItem(Wiki item) {
		if (getWikiList() == null) {
			return;
		}
		getWikiList().remove(item);
	}

	/**
	 * Clear the {@code wikiList} collection.
	 *
	 * <p>
	 * If you have to query {@link Wiki} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearWikiList() {
		if (getWikiList() != null) {
			getWikiList().clear();
		}
	}

	public List<ResourceBooking> getResourceBookingList() {
		return resourceBookingList;
	}

	public void setResourceBookingList(List<ResourceBooking> resourceBookingList) {
		this.resourceBookingList = resourceBookingList;
	}

	/**
	 * Add the given {@link ResourceBooking} item to the {@code resourceBookingList}.
	 *
	 * <p>
	 * It sets {@code item.project = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addResourceBookingListItem(ResourceBooking item) {
		if (getResourceBookingList() == null) {
			setResourceBookingList(new ArrayList<>());
		}
		getResourceBookingList().add(item);
		item.setProject(this);
	}

	/**
	 * Remove the given {@link ResourceBooking} item from the {@code resourceBookingList}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeResourceBookingListItem(ResourceBooking item) {
		if (getResourceBookingList() == null) {
			return;
		}
		getResourceBookingList().remove(item);
	}

	/**
	 * Clear the {@code resourceBookingList} collection.
	 *
	 * <p>
	 * If you have to query {@link ResourceBooking} records in same transaction, make
	 * sure to call {@link javax.persistence.EntityManager#flush() } to avoid
	 * unexpected errors.
	 * </p>
	 */
	public void clearResourceBookingList() {
		if (getResourceBookingList() != null) {
			getResourceBookingList().clear();
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<User> getMembersUserSet() {
		return membersUserSet;
	}

	public void setMembersUserSet(Set<User> membersUserSet) {
		this.membersUserSet = membersUserSet;
	}

	/**
	 * Add the given {@link User} item to the {@code membersUserSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addMembersUserSetItem(User item) {
		if (getMembersUserSet() == null) {
			setMembersUserSet(new HashSet<>());
		}
		getMembersUserSet().add(item);
	}

	/**
	 * Remove the given {@link User} item from the {@code membersUserSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeMembersUserSetItem(User item) {
		if (getMembersUserSet() == null) {
			return;
		}
		getMembersUserSet().remove(item);
	}

	/**
	 * Clear the {@code membersUserSet} collection.
	 *
	 */
	public void clearMembersUserSet() {
		if (getMembersUserSet() != null) {
			getMembersUserSet().clear();
		}
	}

	public Partner getClientPartner() {
		return clientPartner;
	}

	public void setClientPartner(Partner clientPartner) {
		this.clientPartner = clientPartner;
	}

	public Partner getContactPartner() {
		return contactPartner;
	}

	public void setContactPartner(Partner contactPartner) {
		this.contactPartner = contactPartner;
	}

	public LocalDateTime getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDateTime getToDate() {
		return toDate;
	}

	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getEstimatedTimeDays() {
		return estimatedTimeDays == null ? BigDecimal.ZERO : estimatedTimeDays;
	}

	public void setEstimatedTimeDays(BigDecimal estimatedTimeDays) {
		this.estimatedTimeDays = estimatedTimeDays;
	}

	public BigDecimal getEstimatedTimeHrs() {
		return estimatedTimeHrs == null ? BigDecimal.ZERO : estimatedTimeHrs;
	}

	public void setEstimatedTimeHrs(BigDecimal estimatedTimeHrs) {
		this.estimatedTimeHrs = estimatedTimeHrs;
	}

	public BigDecimal getTimeSpent() {
		return timeSpent == null ? BigDecimal.ZERO : timeSpent;
	}

	public void setTimeSpent(BigDecimal timeSpent) {
		this.timeSpent = timeSpent;
	}

	public BigDecimal getProgress() {
		return progress == null ? BigDecimal.ZERO : progress;
	}

	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}

	public BigDecimal getSequence() {
		return sequence == null ? BigDecimal.ZERO : sequence;
	}

	public void setSequence(BigDecimal sequence) {
		this.sequence = sequence;
	}

	public Integer getOrderByState() {
		return orderByState == null ? 0 : orderByState;
	}

	public void setOrderByState(Integer orderByState) {
		this.orderByState = orderByState;
	}

	public Boolean getImputable() {
		return imputable == null ? Boolean.FALSE : imputable;
	}

	public void setImputable(Boolean imputable) {
		this.imputable = imputable;
	}

	public Set<Project> getFinishToStartTaskSet() {
		return finishToStartTaskSet;
	}

	public void setFinishToStartTaskSet(Set<Project> finishToStartTaskSet) {
		this.finishToStartTaskSet = finishToStartTaskSet;
	}

	/**
	 * Add the given {@link Project} item to the {@code finishToStartTaskSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFinishToStartTaskSetItem(Project item) {
		if (getFinishToStartTaskSet() == null) {
			setFinishToStartTaskSet(new HashSet<>());
		}
		getFinishToStartTaskSet().add(item);
	}

	/**
	 * Remove the given {@link Project} item from the {@code finishToStartTaskSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFinishToStartTaskSetItem(Project item) {
		if (getFinishToStartTaskSet() == null) {
			return;
		}
		getFinishToStartTaskSet().remove(item);
	}

	/**
	 * Clear the {@code finishToStartTaskSet} collection.
	 *
	 */
	public void clearFinishToStartTaskSet() {
		if (getFinishToStartTaskSet() != null) {
			getFinishToStartTaskSet().clear();
		}
	}

	public Set<Project> getStartToStartTaskSet() {
		return startToStartTaskSet;
	}

	public void setStartToStartTaskSet(Set<Project> startToStartTaskSet) {
		this.startToStartTaskSet = startToStartTaskSet;
	}

	/**
	 * Add the given {@link Project} item to the {@code startToStartTaskSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addStartToStartTaskSetItem(Project item) {
		if (getStartToStartTaskSet() == null) {
			setStartToStartTaskSet(new HashSet<>());
		}
		getStartToStartTaskSet().add(item);
	}

	/**
	 * Remove the given {@link Project} item from the {@code startToStartTaskSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeStartToStartTaskSetItem(Project item) {
		if (getStartToStartTaskSet() == null) {
			return;
		}
		getStartToStartTaskSet().remove(item);
	}

	/**
	 * Clear the {@code startToStartTaskSet} collection.
	 *
	 */
	public void clearStartToStartTaskSet() {
		if (getStartToStartTaskSet() != null) {
			getStartToStartTaskSet().clear();
		}
	}

	public Set<Project> getFinishToFinishTaskSet() {
		return finishToFinishTaskSet;
	}

	public void setFinishToFinishTaskSet(Set<Project> finishToFinishTaskSet) {
		this.finishToFinishTaskSet = finishToFinishTaskSet;
	}

	/**
	 * Add the given {@link Project} item to the {@code finishToFinishTaskSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addFinishToFinishTaskSetItem(Project item) {
		if (getFinishToFinishTaskSet() == null) {
			setFinishToFinishTaskSet(new HashSet<>());
		}
		getFinishToFinishTaskSet().add(item);
	}

	/**
	 * Remove the given {@link Project} item from the {@code finishToFinishTaskSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeFinishToFinishTaskSetItem(Project item) {
		if (getFinishToFinishTaskSet() == null) {
			return;
		}
		getFinishToFinishTaskSet().remove(item);
	}

	/**
	 * Clear the {@code finishToFinishTaskSet} collection.
	 *
	 */
	public void clearFinishToFinishTaskSet() {
		if (getFinishToFinishTaskSet() != null) {
			getFinishToFinishTaskSet().clear();
		}
	}

	public Set<Project> getStartToFinishTaskSet() {
		return startToFinishTaskSet;
	}

	public void setStartToFinishTaskSet(Set<Project> startToFinishTaskSet) {
		this.startToFinishTaskSet = startToFinishTaskSet;
	}

	/**
	 * Add the given {@link Project} item to the {@code startToFinishTaskSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addStartToFinishTaskSetItem(Project item) {
		if (getStartToFinishTaskSet() == null) {
			setStartToFinishTaskSet(new HashSet<>());
		}
		getStartToFinishTaskSet().add(item);
	}

	/**
	 * Remove the given {@link Project} item from the {@code startToFinishTaskSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeStartToFinishTaskSetItem(Project item) {
		if (getStartToFinishTaskSet() == null) {
			return;
		}
		getStartToFinishTaskSet().remove(item);
	}

	/**
	 * Clear the {@code startToFinishTaskSet} collection.
	 *
	 */
	public void clearStartToFinishTaskSet() {
		if (getStartToFinishTaskSet() != null) {
			getStartToFinishTaskSet().clear();
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Product> getProductSet() {
		return productSet;
	}

	public void setProductSet(Set<Product> productSet) {
		this.productSet = productSet;
	}

	/**
	 * Add the given {@link Product} item to the {@code productSet}.
	 *
	 * @param item
	 *            the item to add
	 */
	public void addProductSetItem(Product item) {
		if (getProductSet() == null) {
			setProductSet(new HashSet<>());
		}
		getProductSet().add(item);
	}

	/**
	 * Remove the given {@link Product} item from the {@code productSet}.
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeProductSetItem(Product item) {
		if (getProductSet() == null) {
			return;
		}
		getProductSet().remove(item);
	}

	/**
	 * Clear the {@code productSet} collection.
	 *
	 */
	public void clearProductSet() {
		if (getProductSet() != null) {
			getProductSet().clear();
		}
	}

	public Boolean getExcludePlanning() {
		return excludePlanning == null ? Boolean.FALSE : excludePlanning;
	}

	public void setExcludePlanning(Boolean excludePlanning) {
		this.excludePlanning = excludePlanning;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Boolean getIsProject() {
		return isProject == null ? Boolean.FALSE : isProject;
	}

	public void setIsProject(Boolean isProject) {
		this.isProject = isProject;
	}

	public Boolean getIsShowPhasesElements() {
		return isShowPhasesElements == null ? Boolean.FALSE : isShowPhasesElements;
	}

	public void setIsShowPhasesElements(Boolean isShowPhasesElements) {
		this.isShowPhasesElements = isShowPhasesElements;
	}

	public Integer getProjectTypeSelect() {
		return projectTypeSelect == null ? 0 : projectTypeSelect;
	}

	public void setProjectTypeSelect(Integer projectTypeSelect) {
		this.projectTypeSelect = projectTypeSelect;
	}

	public GenProjTypePerOrderLine getGenProjTypePerOrderLine() {
		return genProjTypePerOrderLine;
	}

	public void setGenProjTypePerOrderLine(GenProjTypePerOrderLine genProjTypePerOrderLine) {
		this.genProjTypePerOrderLine = genProjTypePerOrderLine;
	}

	public List<TimesheetLine> getTimesheetLineList() {
		return timesheetLineList;
	}

	public void setTimesheetLineList(List<TimesheetLine> timesheetLineList) {
		this.timesheetLineList = timesheetLineList;
	}

	/**
	 * Add the given {@link TimesheetLine} item to the {@code timesheetLineList}.
	 *
	 * <p>
	 * It sets {@code item.project = this} to ensure the proper relationship.
	 * </p>
	 *
	 * @param item
	 *            the item to add
	 */
	public void addTimesheetLineListItem(TimesheetLine item) {
		if (getTimesheetLineList() == null) {
			setTimesheetLineList(new ArrayList<>());
		}
		getTimesheetLineList().add(item);
		item.setProject(this);
	}

	/**
	 * Remove the given {@link TimesheetLine} item from the {@code timesheetLineList}.
	 *
	 * <p>
	 * It sets {@code item.project = null} to break the relationship.
	 * </p>
	 *
 	 * @param item
	 *            the item to remove
	 */
	public void removeTimesheetLineListItem(TimesheetLine item) {
		if (getTimesheetLineList() == null) {
			return;
		}
		getTimesheetLineList().remove(item);
		item.setProject(null);
	}

	/**
	 * Clear the {@code timesheetLineList} collection.
	 *
	 * <p>
	 * It sets {@code item.project = null} to break the relationship.
	 * </p>
	 */
	public void clearTimesheetLineList() {
		if (getTimesheetLineList() != null) {
			for (TimesheetLine item : getTimesheetLineList()) {
        item.setProject(null);
      }
			getTimesheetLineList().clear();
		}
	}

	public BigDecimal getTotalPlannedHrs() {
		return totalPlannedHrs == null ? BigDecimal.ZERO : totalPlannedHrs;
	}

	public void setTotalPlannedHrs(BigDecimal totalPlannedHrs) {
		this.totalPlannedHrs = totalPlannedHrs;
	}

	public BigDecimal getTotalRealHrs() {
		return totalRealHrs == null ? BigDecimal.ZERO : totalRealHrs;
	}

	public void setTotalRealHrs(BigDecimal totalRealHrs) {
		this.totalRealHrs = totalRealHrs;
	}

	public Boolean getExcludeTimesheetEditor() {
		return excludeTimesheetEditor == null ? Boolean.FALSE : excludeTimesheetEditor;
	}

	public void setExcludeTimesheetEditor(Boolean excludeTimesheetEditor) {
		this.excludeTimesheetEditor = excludeTimesheetEditor;
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
		if (!(obj instanceof Project)) return false;

		final Project other = (Project) obj;
		if (this.getId() != null || other.getId() != null) {
			return Objects.equals(this.getId(), other.getId());
		}

		if (!Objects.equals(getCode(), other.getCode())) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(1355342585, this.getCode());
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", getId())
			.add("name", getName())
			.add("statusSelect", getStatusSelect())
			.add("synchronize", getSynchronize())
			.add("extendsMembersFromParent", getExtendsMembersFromParent())
			.add("code", getCode())
			.add("fromDate", getFromDate())
			.add("toDate", getToDate())
			.add("dueDate", getDueDate())
			.add("estimatedTimeDays", getEstimatedTimeDays())
			.add("estimatedTimeHrs", getEstimatedTimeHrs())
			.add("timeSpent", getTimeSpent())
			.omitNullValues()
			.toString();
	}
}
