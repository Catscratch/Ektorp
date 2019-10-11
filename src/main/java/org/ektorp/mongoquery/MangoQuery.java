package org.ektorp.mongoquery;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class MangoQuery {

  public enum Stale {
    OK("ok"),
    FALSE("false");

    private String jsonName;

    Stale(String jsonName) {
      this.jsonName = jsonName;
    }

    public String getJsonName() {
      return jsonName;
    }
  }

  /** object describing criteria used to select documents. */
  private Expression selector;

  /** Maximum number of results returned. Default is 25. Optional */
  private Integer limit;

  /** Skip the first ‘n’ results, where ‘n’ is the value specified. Optional */
  private Integer skip;

  /** contains a list of field name and direction pairs */
  private List<Sort> sort;

  /**
   * specifying which fields of each object should be returned. If it is omitted, the entire
   * object is returned.
  */
  private List<String> fields;

  /** Instruct a query to use a specific index. */
  @JsonProperty("use_index")
  private String useIndex;

  /**
   * Read quorum needed for the result. This defaults to 1, in which case the document found in
   * the index is returned. If set to a higher value, each document is read from at least that
   * many replicas before it is returned in the results. This is likely to take more time than
   * using only the document stored locally with the index.
   */
  @JsonProperty("r")
  private Integer readQuorum;

  /**
   * A string that enables you to specify which page of results you require. Used for paging
   * through result sets. Every query returns an opaque string under the bookmark key that can
   * then be passed back in a query to get the next page of results. If any part of the selector
   * query changes between requests, the results are undefined.
   */
  private String bookmark;

  /** Whether to update the index prior to returning the result. Default is true. */
  private Boolean update;

  /** Whether or not the view results should be returned from a “stable” set of shards */
  private Boolean stable;

  /** Combination of update=false and stable=true options. (Default: false) */
  private Stale stale;

  /** Include execution statistics in the query response. (Default: false) */
  @JsonProperty("execution_stats")
  private Boolean executionStats;

  public Expression getSelector() {
    return selector;
  }

  public void setSelector(Expression selector) {
    this.selector = selector;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public List<Sort> getSort() {
    return sort;
  }

  public void setSort(List<Sort> sort) {
    this.sort = sort;
  }

  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

  public String getUseIndex() {
    return useIndex;
  }

  public void setUseIndex(String useIndex) {
    this.useIndex = useIndex;
  }

  public Integer getReadQuorum() {
    return readQuorum;
  }

  public void setReadQuorum(Integer readQuorum) {
    this.readQuorum = readQuorum;
  }

  public String getBookmark() {
    return bookmark;
  }

  public void setBookmark(String bookmark) {
    this.bookmark = bookmark;
  }

  public Boolean getUpdate() {
    return update;
  }

  public void setUpdate(Boolean update) {
    this.update = update;
  }

  public Boolean getStable() {
    return stable;
  }

  public void setStable(Boolean stable) {
    this.stable = stable;
  }

  public Stale getStale() {
    return stale;
  }

  public void setStale(Stale stale) {
    this.stale = stale;
  }

  public Boolean getExecutionStats() {
    return executionStats;
  }

  public void setExecutionStats(Boolean executionStats) {
    this.executionStats = executionStats;
  }
}
