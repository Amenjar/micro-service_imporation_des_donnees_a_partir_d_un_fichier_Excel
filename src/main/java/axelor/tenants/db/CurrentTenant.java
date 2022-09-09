package com.axelor.db.tenants;

public class CurrentTenant
{
    public void setCurrentTenant(String tenant){
        TenantResolver.CURRENT_TENANT.set(tenant);
        TenantResolver.CURRENT_HOST.set("biomediqa:8081");
    }

    public void removeCurrentTenant() {
        TenantResolver.CURRENT_HOST.remove();
        TenantResolver.CURRENT_TENANT.remove();
    }


}
