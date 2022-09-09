package com.axelor.meta.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.meta.db.MetaSelect;

public class MetaSelectRepository extends JpaRepository<MetaSelect> {

	public MetaSelectRepository() {
		super(MetaSelect.class);
	}

	public MetaSelect findByID(String xmlId) {
		return Query.of(MetaSelect.class)
				.filter("self.xmlId = :xmlId")
				.bind("xmlId", xmlId)
				.cacheable()
				.fetchOne();
	}

	public MetaSelect findByName(String name) {
		return Query.of(MetaSelect.class)
				.filter("self.name = :name")
				.bind("name", name)
				.order("-priority")
				.cacheable()
				.fetchOne();
	}

	public Query<MetaSelect> findByModule(String module) {
		return Query.of(MetaSelect.class)
				.filter("self.module = :module")
				.bind("module", module)
				.cacheable();
	}

}

