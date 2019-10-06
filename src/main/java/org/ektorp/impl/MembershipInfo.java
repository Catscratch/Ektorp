package org.ektorp.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MembershipInfo {

  @JsonProperty("all_nodes")
  private List<String> allNodes;

  @JsonProperty("cluster_nodes")
  private List<String> clusterNodes;

  public List<String> getAllNodes() {
    return allNodes;
  }

  public void setAllNodes(List<String> allNodes) {
    this.allNodes = allNodes;
  }

  public List<String> getClusterNodes() {
    return clusterNodes;
  }

  public void setClusterNodes(List<String> clusterNodes) {
    this.clusterNodes = clusterNodes;
  }
}
