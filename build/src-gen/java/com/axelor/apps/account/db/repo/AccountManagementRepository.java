package com.axelor.apps.account.db.repo;

import com.axelor.apps.account.db.AccountManagement;
import com.axelor.db.JpaRepository;

public class AccountManagementRepository extends JpaRepository<AccountManagement> {

	public AccountManagementRepository() {
		super(AccountManagement.class);
	}

}

