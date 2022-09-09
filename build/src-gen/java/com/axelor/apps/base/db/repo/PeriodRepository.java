package com.axelor.apps.base.db.repo;

import com.axelor.apps.base.db.Period;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class PeriodRepository extends JpaRepository<Period> {

	public PeriodRepository() {
		super(Period.class);
	}

	public Period findByCode(String code) {
		return Query.of(Period.class)
				.filter("self.code = :code")
				.bind("code", code)
				.fetchOne();
	}

	public Period findByName(String name) {
		return Query.of(Period.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	// STATUS SELECT
	public static final int STATUS_OPENED = 1;
	public static final int STATUS_CLOSED = 2;
	public static final int STATUS_ADJUSTING = 3;
}

