package org.ektorp.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.ektorp.util.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamingJsonSerializer implements JsonSerializer {

  private final Logger LOG = LoggerFactory.getLogger(StreamingJsonSerializer.class);
  private final ObjectMapper objectMapper;
  private final BulkDocumentWriter bulkDocWriter;

  private static ExecutorService singletonExecutorService;
  private final ExecutorService executorService;

  public StreamingJsonSerializer(ObjectMapper om) {
    this(om, getSingletonExecutorService());
  }

  public StreamingJsonSerializer(ObjectMapper om, ExecutorService es) {
    objectMapper = om;
    executorService = es;
    bulkDocWriter = new BulkDocumentWriter(om);
  }

  private static synchronized ExecutorService getSingletonExecutorService() {
    if (singletonExecutorService == null) {
      singletonExecutorService = Executors.newCachedThreadPool(new ThreadFactory() {

        private final AtomicInteger threadCount = new AtomicInteger();

        public Thread newThread(Runnable r) {
          Thread t = new Thread(r,
              String.format("ektorp-doc-writer-thread-%s", threadCount.incrementAndGet()));
          t.setDaemon(true);
          return t;
        }

      });
    }
    return singletonExecutorService;
  }

  /* (non-Javadoc)
   * @see org.ektorp.impl.JsonSerializer#asInputStream(java.util.Collection, boolean)
   */
  public BulkOperation createBulkOperation(final Collection<?> objects,
      final boolean allOrNothing) {
    try (final PipedOutputStream out = new PipedOutputStream();
        PipedInputStream in = new PipedInputStream(out)) {
      Future<?> writeTask = executorService.submit(() -> {
        try {
          bulkDocWriter.write(objects, allOrNothing, out);
        } catch (Exception e) {
          LOG.error("Caught exception while writing bulk document:", e);
        }
      });

      return new BulkOperation(writeTask, in);
    } catch (IOException e) {
      throw Exceptions.propagate(e);
    }
  }

  /* (non-Javadoc)
   * @see org.ektorp.impl.JsonSerializer#toJson(java.lang.Object)
   */
  public String toJson(Object o) {
    try {
      String json = objectMapper.writeValueAsString(o);
      LOG.debug(json);
      return json;
    } catch (Exception e) {
      throw Exceptions.propagate(e);
    }
  }

}
