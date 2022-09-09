package com.axelor.apps.project.db.repo;

import com.axelor.apps.project.db.Project;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class ProjectRepository extends JpaRepository<Project> {

	public ProjectRepository() {
		super(Project.class);
	}

	public Project findByCode(String code) {
		return Query.of(Project.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Query<Project> findAllByParentProject(Project parentProject) {
		return Query.of(Project.class)
				.filter("self.parentProject = :parentProject")
				.bind("parentProject", parentProject);
	}

	public Project findByName(String name) {
		return Query.of(Project.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public static final Integer TYPE_PROJECT = 1;
	public static final Integer TYPE_PHASE = 2;

	public static final Integer STATE_NEW = 1;
	public static final Integer STATE_IN_PROGRESS = 2;
	public static final Integer STATE_FINISHED = 3;
	public static final Integer STATE_CANCELED = 4;
}

