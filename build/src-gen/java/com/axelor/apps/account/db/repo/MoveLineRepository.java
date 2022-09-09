package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.MoveLine;
import com.axelor.apps.account.db.ReconcileGroup;
import com.axelor.db.JpaRepository;
import com.axelor.db.Query;

public class MoveLineRepository extends JpaRepository<MoveLine> {

	public MoveLineRepository() {
		super(MoveLine.class);
	}

	public MoveLine findByName(String name) {
		return Query.of(MoveLine.class)
				.filter("self.name = :name")
				.bind("name", name)
				.fetchOne();
	}

	public Query<MoveLine> findByReconcileGroup(ReconcileGroup reconcileGroup) {
		return Query.of(MoveLine.class)
				.filter("self.reconcileGroup = :reconcileGroup")
				.bind("reconcileGroup", reconcileGroup);
	}

	// REIMBURSEMENT STATUS
	public static final int REIMBURSEMENT_STATUS_NULL = 0;
	public static final int REIMBURSEMENT_STATUS_REIMBURSING = 1;
	public static final int REIMBURSEMENT_STATUS_REIMBURSED = 2;

	// IRRECOVERABLE STATUS SELECT
	public static final int IRRECOVERABLE_STATUS_NOT_IRRECOUVRABLE = 0;
	public static final int IRRECOVERABLE_STATUS_TO_PASS_IN_IRRECOUVRABLE = 1;
	public static final int IRRECOVERABLE_STATUS_PASSED_IN_IRRECOUVRABLE = 2;
}

