package org.ektorp.http;


public interface ResponseCallback<T> {

  /**
   * Called when http response code is &lt; 300
   */
  T success(HttpResponse hr) throws Exception;

  T error(HttpResponse hr);
}
