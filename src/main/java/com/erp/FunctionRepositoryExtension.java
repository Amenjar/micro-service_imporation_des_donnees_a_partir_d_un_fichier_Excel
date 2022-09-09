package com.erp;

import com.axelor.apps.base.db.Function;
import com.axelor.apps.base.db.repo.FunctionRepository;
import com.axelor.db.Query;

public class FunctionRepositoryExtension extends FunctionRepository
{
    @Override
    public Function findByName(String name) {
        return Query.of(Function.class)
                .filter("TRIM(unaccent(lower(self.name))) = TRIM(unaccent(lower(:name)))")
                .bind("name", name)
                .fetchOne();
    }
}
