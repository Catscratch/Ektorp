package org.ektorp.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining update handler functions embedded in repositories.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateHandler {

  /**
   * The name of the update handler
   */
  String name();

  /**
   * Inline update handler function.
   */
  String function() default "";

  /**
   * Update handler functions are best stored in a separate files.
   * <p>
   * By specifying the file parameter a function can be loaded from the classpath. The path is
   * relative to the class annotated by this annotation.
   * <p>
   * If the file my_handler.js is in the same directory as the repository this parameter should be
   * set to "my_handler.js".
   */
  String file() default "";
}
