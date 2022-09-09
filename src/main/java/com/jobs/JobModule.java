package com.jobs;

import com.axelor.app.AxelorModule;

public class JobModule extends AxelorModule {

  @Override
  protected void configure() {
    super.configure();
    bind(LoginObserver.class);
  }
}
