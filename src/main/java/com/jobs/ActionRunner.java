package com.jobs;

import com.axelor.app.AppSettings;
import com.axelor.db.tenants.CurrentTenant;
import com.axelor.db.tenants.TenantResolver;
import com.axelor.inject.Beans;
import com.axelor.meta.ActionExecutor;
import com.axelor.rpc.ActionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ActionRunner implements Job
{

    private final ActionExecutor actionExecutor = Beans.get(ActionExecutor.class);

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Override
    public void run()
    {
        try
        {
            logger.info("ActionRunner is running.");
            AppSettings appSettings = AppSettings.get();
            List<String> tenants = getTenants(appSettings);
            for (String tenant : tenants)
            {
                executeActionForTenant(tenant);
            }
        }
        catch (Exception e)
        {
            logger.error("Failure to execute actions.", e);
        }
    }

    private void executeActionForTenant(String tenant)
    {
        CurrentTenant currentTenant = new CurrentTenant();
        currentTenant.setCurrentTenant(tenant);
        try
        {
            String action = "action-update-status-training-medical,action-update-general-status-on-grid";
            executeAction(action);
            logger.error("Current tenant is: " + tenant);
            System.out.println("Current tenant is: " + TenantResolver.currentTenantIdentifier());
        }
        finally
        {
            currentTenant.removeCurrentTenant();
        }
    }

    public synchronized void executeAction(String action)
    {
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setAction(action);
        actionExecutor.execute(actionRequest);
    }

    private List<String> getTenants(AppSettings appSettings)
    {
        String tenantRegex = "^db\\.[a-zA-Z0-9]+\\.name$";
        Pattern tenantPattern = Pattern.compile(tenantRegex);
        return appSettings.getProperties().entrySet().stream().filter(entry -> tenantPattern.matcher((String) entry.getKey()).find()).map(entry -> ((String) entry.getValue()).replaceAll("\\s+", "")).collect(Collectors.toList());
    }
}
