package com.jobs;

import javax.annotation.PostConstruct;

// A tricky Java code to predict the output
// based on order of
// execution of constructors, static
// and initialization blocks
class MyTest {
  static {
    MyTest myTest = new MyTest();
    System.out.println(myTest.getSum());
  }

  private static boolean initialized = false;

  private static int sum;

  private static void initialize() {
    if (!initialized) {
      System.out.println("Not yet initialized");
      for (int i = 0; i < 100; i++) sum += i;
      initialized = true;
    }
  }

  @PostConstruct
  public static int getSum() {
    initialize();
    return sum;
  }
}

public class GFG {
  public static void main(String[] args) {
    System.out.println("MyTest.getSum()");
  }
}
