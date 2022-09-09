package com.jobs;

import com.axelor.event.Observes;
import com.axelor.events.PostLogin;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.google.inject.servlet.RequestScoped;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class LoginObserver {

  @Inject private HttpServletRequest request;

  // Observes successful login.
  void onLoginSuccess(@Observes @Named(PostLogin.SUCCESS) PostLogin event) {
    final String userCode = event.getUser().getCode();
    final String userAgent = request.getHeader("User-Agent");
    System.out.printf("User: %s, User-Agent: %s%n", userCode, userAgent);
  }
}
