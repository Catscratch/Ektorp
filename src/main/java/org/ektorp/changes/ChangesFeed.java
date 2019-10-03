package org.ektorp.changes;

import java.util.concurrent.TimeUnit;

/**
 * ChangesFeed listens to the _changes feed in a CouchDB database. Feeds are created by calling the
 * method  changesFeed(ChangesCommand cmd) in CouchDbConnector.
 * <p>
 * An active feed buffers incoming changes in a unbounded queue that will grow until
 * OutOfMemoryException if not polled.
 */
public interface ChangesFeed {

  /**
   * Retrieves and removes the head of this changes feed, waiting if necessary until an element
   * becomes available.
   *
   * @throws InterruptedException when this changes feed is closed or otherwise is interrupted
   */
  DocumentChange next() throws InterruptedException;

  /**
   * Retrieves and removes the head of this changes feed, do not wait until an element becomes
   * available. returns null if empty
   *
   * @throws InterruptedException when this changes feed is closed or otherwise is interrupted
   */
  DocumentChange poll() throws InterruptedException;

  /**
   * Retrieves and  removes the head of this changes feed, waiting up to the specified wait time if
   * necessary for an element to become available.
   *
   * @throws InterruptedException when this changes feed is closed or otherwise is interrupted
   */
  DocumentChange next(long timeout, TimeUnit unit) throws InterruptedException;

  /**
   * Will close this feed and interrupt any threads waiting on next()
   */
  void cancel();

  /**
   * @return true if this feed is active.
   */
  boolean isAlive();

  /**
   * @return the size of this feed's unhandled internal queue.
   */
  int queueSize();

}
