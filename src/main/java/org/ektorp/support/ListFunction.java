package org.ektorp.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining list functions embedded in repositories.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListFunction {

  /**
   * The name of the list function
   */
  String name();

  /**
   * Inline list function.
   */
  String function() default "";

  /**
   * List functions are best stored in a separate files.
   * <p>
   * By specifying the file parameter a function can be loaded from the classpath. The path is
   * relative to the class annotated by this annotation.
   * <p>
   * If the file my_list_func.json is in the same directory as the repository this parameter should
   * be set to "my_list_func.js".
   */
  String file() default "";
}
