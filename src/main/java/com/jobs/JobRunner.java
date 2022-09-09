package com.jobs;

import com.axelor.app.AxelorModule;
import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobRunner extends AxelorModule {

  private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private boolean isJobRunning = false;

  public JobRunner() {
    //initJob();
     ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
     threadPool.schedule(new Run(), 70000, TimeUnit.MILLISECONDS);
     logger.info("building a JobRunner instance.");

  }

    public static class Run implements Runnable{

        @Override
        public void run()
        {
            JobRunner j = new JobRunner();
            j.initJob();
        }
  }

  public void initJob() {
    List<Job> jobList = new ArrayList<>();
    jobList.add(new ClientContractJob());
    jobList.add(new ActionRunner());
    run(jobList);
  }

  public void run(List<Job> jobs) {
    if (isJobRunning) {
      logger.info("Job is already running, and can't rerun.");
      return;
    }
    isJobRunning = true;
    logger.info("Contract job is running.");
    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
    long timeToMidnight = getMillisToMidnight();
    long oneDayDelay = 24 * 60 * 60 * 1000;
    jobs.forEach(
        job -> threadPool.scheduleAtFixedRate(job::run, 1000, 60000, TimeUnit.MILLISECONDS));
    logger.info("Contract job has been fired. The first run will be after {} ms.", timeToMidnight);
  }

  private long getMillisToMidnight() {
    LocalDateTime localDateTime = LocalDateTime.now();
    LocalDateTime nextMidnight =
        LocalDateTime.of(localDateTime.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
    return localDateTime.until(nextMidnight, ChronoUnit.MILLIS);
  }
}
