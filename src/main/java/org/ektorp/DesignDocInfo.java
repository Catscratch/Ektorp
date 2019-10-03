package org.ektorp;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ektorp.support.OpenCouchDbDocument;

public class DesignDocInfo extends OpenCouchDbDocument {

  private static final long serialVersionUID = 4030630616850588285L;

  public static class ViewIndex extends OpenCouchDbDocument {

    private static final long serialVersionUID = 1164231233089979199L;

    @JsonProperty("compact_running")
    private boolean compactRunning;
    @JsonProperty("updater_running")
    private boolean updaterRunning;
    @JsonProperty
    private String language;
    @JsonProperty("purge_seq")
    private long purgeSeq;
    @JsonProperty("waiting_commit")
    private boolean waitingCommit;
    @JsonProperty("waiting_clients")
    private int waitingClients;
    @JsonProperty
    private String signature;
    @JsonProperty("update_seq")
    private long updateSeq;
    @JsonProperty("disk_size")
    private long diskSize;

    /**
     * Indicates whether a compaction routine is currently running on the view
     */
    public boolean isCompactRunning() {
      return compactRunning;
    }

    /**
     * Indicates if the view is currently being updated.
     */
    public boolean isUpdaterRunning() {
      return updaterRunning;
    }

    /**
     * Language for the defined views.
     */
    public String getLanguage() {
      return language;
    }

    /**
     * The purge sequence that has been processed.
     */
    public long getPurgeSeq() {
      return purgeSeq;
    }

    /**
     * Indicates if there are outstanding commits to the underlying database that need to
     * processed.
     */
    public boolean isWaitingCommit() {
      return waitingCommit;
    }

    /**
     * Number of clients waiting on views from this design document.
     */
    public int getWaitingClients() {
      return waitingClients;
    }

    /**
     * MD5 signature of the views for the design document
     */
    public String getSignature() {
      return signature;
    }

    /**
     * The update sequence of the corresponding database that has been indexed.
     */
    public long getUpdateSeq() {
      return updateSeq;
    }

    /**
     * Size in bytes of the view as stored on disk.
     */
    public long getDiskSize() {
      return diskSize;
    }
  }

  @JsonProperty
  private String name;

  @JsonProperty("view_index")
  private ViewIndex viewIndex;

  public String getName() {
    return name;
  }

  public ViewIndex getViewIndex() {
    return viewIndex;
  }
}
