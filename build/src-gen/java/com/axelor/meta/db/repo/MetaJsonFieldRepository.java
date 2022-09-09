package com.axelor.meta.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.meta.db.MetaJsonField;

public class MetaJsonFieldRepository extends JpaRepository<MetaJsonField> {

	public MetaJsonFieldRepository() {
		super(MetaJsonField.class);
	}

	public MetaJsonField findByName(String name) {
		return Query.of(MetaJsonField.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

