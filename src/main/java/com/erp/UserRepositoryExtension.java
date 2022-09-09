package com.erp;

import com.axelor.auth.db.User;
import com.axelor.auth.db.repo.UserRepository;
import com.axelor.db.Query;

public class UserRepositoryExtension extends UserRepository
{

    @Override
    public User findByName(String name)
    {
        return Query.of(User.class).filter("TRIM(unaccent(lower(self.name))) = TRIM(unaccent(lower(:name)))").bind("name", name).fetchOne();
    }
}
