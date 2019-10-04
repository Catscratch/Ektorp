package org.ektorp.http;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.conn.ClientConnectionManager;

public class IdleConnectionMonitor {

  private final static long DEFAULT_IDLE_CHECK_INTERVAL = 30;

  private final static ScheduledExecutorService executorService = Executors
      .newScheduledThreadPool(1, new ThreadFactory() {

        private final AtomicInteger threadCount = new AtomicInteger(0);

        public Thread newThread(Runnable r) {
          Thread t = new Thread(r);
          t.setDaemon(true);
          t.setName(String
              .format("ektorp-idle-connection-monitor-thread-%s", threadCount.incrementAndGet()));
          return t;
        }
      });

  public static void monitor(ClientConnectionManager cm) {
    CleanupTask cleanupTask = new CleanupTask(cm);
    ScheduledFuture<?> cleanupFuture = executorService
        .scheduleWithFixedDelay(cleanupTask, DEFAULT_IDLE_CHECK_INTERVAL,
            DEFAULT_IDLE_CHECK_INTERVAL, TimeUnit.SECONDS);
    cleanupTask.setFuture(cleanupFuture);
  }

  public static void shutdown() {
    executorService.shutdown();
  }

  private static class CleanupTask implements Runnable {

    private final WeakReference<ClientConnectionManager> cm;
    private ScheduledFuture<?> thisFuture;

    CleanupTask(ClientConnectionManager cm) {
      this.cm = new WeakReference<ClientConnectionManager>(cm);
    }

    public void setFuture(ScheduledFuture<?> future) {
      thisFuture = future;
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
    public void run() {
      if (cm.get() != null) {
        cm.get().closeExpiredConnections();
      } else if (thisFuture != null) {
        thisFuture.cancel(false);
      }
    }

  }

}
