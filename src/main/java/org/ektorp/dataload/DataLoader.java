package org.ektorp.dataload;

import java.io.Reader;

public interface DataLoader {

  void loadInitialData(Reader in);

  /**
   * Is called when all DataLoaders in the system has loaded it´s data.
   */
  void allDataLoaded();

  String[] getDataLocations();
}
