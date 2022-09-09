package com.axelor.meta.db.repo;

import com.axelor.db.JpaRepository;
import com.axelor.db.Query;
import com.axelor.meta.db.MetaJsonModel;

public abstract class AbstractMetaJsonModelRepository extends JpaRepository<MetaJsonModel> {

	public AbstractMetaJsonModelRepository() {
		super(MetaJsonModel.class);
	}

	public MetaJsonModel findByName(String name) {
		return Query.of(MetaJsonModel.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

}

