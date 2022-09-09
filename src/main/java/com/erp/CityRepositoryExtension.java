package com.erp;

import com.axelor.apps.base.db.City;
import com.axelor.apps.base.db.repo.CityRepository;
import com.axelor.db.Query;

public class CityRepositoryExtension extends CityRepository
{

    @Override
    public City findByName(String name)
    {
        return Query.of(City.class).filter("TRIM(unaccent(lower(self.name))) = TRIM(unaccent(lower(:name)))").bind("name", name).fetchOne();
    }
}
