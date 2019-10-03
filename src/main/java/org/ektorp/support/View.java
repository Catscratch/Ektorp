package org.ektorp.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining views embedded in repositories.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface View {

  /**
   * The name of the view
   */
  String name();

  /**
   * Map function or path to function.
   * <p>
   * This value may be a string of code to use for the function. Alternatively, the string may
   * specify a file to load for the function by starting the string with <i>classpath:</i>. The rest
   * of the string then represents a relative path to the function.
   * </p>
   */
  String map() default "";

  /**
   * Reduce function or path to function.
   * <p>
   * This value may be a string of code to use for the function. Alternatively, the string may
   * specify a file to load for the function by starting the string with <i>classpath:</i>. The rest
   * of the string then represents a relative path to the function.
   * </p>
   */
  String reduce() default "";

  /**
   * Non-trivial views are best stored in a separate files.
   * <p>
   * By specifying the file parameter a view definition can be loaded from the classpath. The path
   * is relative to the class annotated by this annotation.
   * <p>
   * If the file complicated_view.json is in the same directory as the repository this parameter
   * should be set to "complicated_view.json".
   * <p>
   * The file must be a valid json document:
   * <p>
   * { "map": "function(doc) { much javascript here }", // the reduce function is optional "reduce":
   * "function(keys, values) { ... }" }
   */
  String file() default "";

}
