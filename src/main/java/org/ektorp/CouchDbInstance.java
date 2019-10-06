package org.ektorp;

import java.util.Collection;
import java.util.List;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.MembershipInfo;

public interface CouchDbInstance {

  /**
   * @return the names of all databases residing in this instance.
   */
  List<String> getAllDatabases();

  /**
   * @return true if the database exists.
   */
  boolean checkIfDbExists(DbPath db);

  boolean checkIfDbExists(String path);

  void createDatabase(DbPath path);

  void createDatabase(String path);

  boolean createDatabaseIfNotExists(DbPath path);

  boolean createDatabaseIfNotExists(String path);

  void deleteDatabase(String path);

  CouchDbConnector createConnector(String path, boolean createIfNotExists);

  /**
   * Returns the Couch _replicator database
   *
   * @return CouchDbConnector a connector to the replicator database
   */
  CouchDbConnector getReplicatorConnector();

  /**
   * Convenience method for accessing the underlying HttpClient. Preferably used wrapped in a
   * org.ektorp.http.RestTemplate.
   */
  HttpClient getConnection();

  ReplicationStatus replicate(ReplicationCommand cmd);

  /**
   * Get the full configuration of this instance
   *
   * @param c the type to return the configuration in (Map, JsonNode, POJO)
   * @param nodeName the name of the node to query for the configuration, if it is null
   *                 the _local node will be queried
   */
  <T> T getConfiguration(final Class<T> c, String nodeName);

  /**
   * Get the configuration of this instance within the specified section
   *
   * @param c the type to return the configuration in (Map, JsonNode, POJO)
   * @param nodeName the name of the node to query for the configuration, if it is null
   *                 the _local node will be queried
   */
  <T> T getConfiguration(final Class<T> c, String nodeName, String section);

  /**
   * Get the configuration of this instance for this specific section and key
   *
   * @param c the type to return the configuration in (Map, JsonNode, POJO)
   * @param nodeName the name of the node to query for the configuration, if it is null
   *                 the _local node will be queried
   */
  <T> T getConfiguration(final Class<T> c, String nodeName, String section, String key);

  /**
   * Convenience method to get specific configuration item
   *
   * @return the configuration value for the specified key in the specified section
   */
  String getConfiguration(String nodeName, String section, String key);

  /**
   * Update the configuration key in the specified section with the specified value
   *
   * @param nodeName the name of the node to query for the configuration, if it is null
   *                 the _local node will be queried
   * @param value the value to set (all config values are Strings in CouchDB)
   * @return the previous value for this key
   */
  String setConfiguration(String nodeName, String section, String key, String value);

  /**
   * Delete the configuration key in the specified section
   *
   * @param nodeName the name of the node to query for the configuration, if it is null
   *                 the _local node will be queried
   *
   * @return the previous value for this key
   */
  String deleteConfiguration(String nodeName, String section, String key);

  /**
   * @return all active tasks
   */
  Collection<ActiveTask> getActiveTasks();

  /**
   * Displays the nodes that are part of the cluster as cluster_nodes. The field all_nodes displays
   * all nodes this node knows about, including the ones that are part of the cluster.
   *
   * @return
   */
  MembershipInfo describeCluster();
}
