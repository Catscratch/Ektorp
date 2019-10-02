package org.ektorp.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining show functions embedded in repositories.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ShowFunction {

  /**
   * The name of the show function.
   */
  String name();

  /**
   * Inline show function.
   */
  String function() default "";

  /**
   * Show functions are best stored in a separate files.
   * <p>
   * By specifying the file parameter a function can be loaded from the classpath. The path is
   * relative to the class annotated by this annotation.
   * <p>
   * If the file my_show_func.json is in the same directory as the repository this parameter should
   * be set to "my_show_func.js".
   */
  String file() default "";
}
